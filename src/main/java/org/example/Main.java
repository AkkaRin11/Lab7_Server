package org.example;

import org.example.connection.TCPServer;
import org.example.repository.DB;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        DB db = DB.getInstance();
        TCPServer tcpServer = new TCPServer();
        try {
            db.createConnection();
            tcpServer.openConnection();
            tcpServer.run();
        } catch (IOException | SQLException sqlException) {
            System.out.println("Сервер или БД непредвиденно завершили свою работу");
        }

    }
}