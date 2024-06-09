package org.example.connection;

import org.example.Dto.CommandRequest;
import org.example.Dto.Response;
import org.example.Dto.StatusCode;
import org.example.controller.CommandController;
import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;
import org.example.utils.HashUtils;
import org.example.utils.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
    private static final int BUFFER_SIZE = 4096;
    private ByteBuffer buffer;

    private static final String HOST = "localhost";
    private static final int PORT = 2000;

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    private final CommandController commandController;
    private final LabWorkService labWorkService;

    private final ExecutorService readThreadPool;
    private final ExecutorService processThreadPool;

    public TCPServer() {
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
        commandController = new CommandController();
        labWorkService = new LabWorkServiceImpl();

        // Инициализируем Fixed Thread Pools
        readThreadPool = Executors.newFixedThreadPool(10);
        processThreadPool = Executors.newFixedThreadPool(10);
    }

    public void openConnection() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST, PORT);
        serverSocketChannel.bind(inetSocketAddress);
        selector = initSelector();
    }

    private Selector initSelector() throws IOException {
        Selector socketSelector = SelectorProvider.provider().openSelector();
        serverSocketChannel.register(socketSelector, SelectionKey.OP_ACCEPT);
        return socketSelector;
    }

    public void run() {
        try {
            while (true) {
                selector.selectNow();
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = takeKey(selectedKeys);
                    handleKey(key);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SelectionKey takeKey(Iterator<SelectionKey> selectionKeyIterator) {
        SelectionKey key = selectionKeyIterator.next();
        selectionKeyIterator.remove();
        return key;
    }

    private void handleKey(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                accept(key);
            } else if (key.isReadable()) {
                // Убираем ключ перед чтением
                key.interestOps(0);
                readThreadPool.execute(() -> {
                    try {
                        read(key);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } else if (key.isWritable()) {
                // Убираем ключ перед записью
                key.interestOps(0);
                write(key);
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = ssc.accept();
        socketChannel.configureBlocking(false);
        System.out.println("Подключенно: " + socketChannel.getRemoteAddress());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        buffer.clear();
        int bytesRead;
        try {
            bytesRead = socketChannel.read(buffer);
        } catch (IOException e) {
            key.cancel();
            socketChannel.close();
            return;
        }

        if (bytesRead == -1) {
            key.cancel();
            return;
        }
        this.buffer.flip();

        CommandRequest request = Serializer.deserializeObject(buffer);
        request.setUserPass(HashUtils.encryptPass(request.getUserPass()));
        processThreadPool.execute(() -> processRequest(key, request));
    }

    private void processRequest(SelectionKey key, CommandRequest request) {
        Response response;
        String commandResponse;

        if (request.getCommandName().equals("registration")) {
            if (labWorkService.registration(request.getUserName(), request.getUserPass())) {
                response = new Response(StatusCode._200_SUCCESS_, "Регистрация прошла успешно");
            } else {
                response = new Response(StatusCode._500_SERVER_ERROR, "Аккаунт с таким именем уже существует");
            }
        } else if (request.getCommandName().equals("log")) {
            if (labWorkService.log(request.getUserName(), request.getUserPass())) {
                response = new Response(StatusCode._200_SUCCESS_, "Вы успешно зашли в аккаунт");
            } else {
                response = new Response(StatusCode._500_SERVER_ERROR, "Ошибка в имени пользователя или пароле");
            }
        } else {
            int id = labWorkService.getPersonId(request.getUserName(), request.getUserPass());

            if (id != -1) {
                commandResponse = commandController.executeCommand(
                        request.getCommandName(),
                        request.getCommandObjectArgument(),
                        id,
                        request.getCommandStringArgument()
                );

                response = new Response(StatusCode._200_SUCCESS_, commandResponse);
            } else {
                response = new Response(StatusCode._500_SERVER_ERROR, "Такого юзера не существует");
            }
        }

        key.attach(response);
        key.interestOps(SelectionKey.OP_WRITE);
        selector.wakeup();
    }

    public void close() throws IOException {
        if (serverSocketChannel != null) {
            serverSocketChannel.close();
        }
        readThreadPool.shutdown();
        processThreadPool.shutdown();
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        Response response = (Response) key.attachment();

        new Thread(() -> {
            try {
                ByteBuffer writeBuffer = Serializer.serializeObject(response);
                writeBuffer.flip();
                while (writeBuffer.hasRemaining()) {
                    socketChannel.write(writeBuffer);
                }
                // Регистрация обратно в режим чтения
                socketChannel.register(selector, SelectionKey.OP_READ);
                selector.wakeup();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    socketChannel.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}
