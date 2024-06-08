package org.example.utils;

import org.example.Dto.CommandRequest;
import org.example.Dto.Response;

import java.io.*;
import java.nio.ByteBuffer;

public class Serializer {
    public static ByteBuffer serializeObject(Response response) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(response);
        oos.close();
        ByteBuffer buffer = ByteBuffer.allocate(baos.size());
        buffer.put(baos.toByteArray());
        return buffer;
    }

    public static CommandRequest deserializeObject(ByteBuffer buffer) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (CommandRequest) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
