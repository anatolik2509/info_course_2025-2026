package org.example.reverser;

import java.io.IOException;
import java.io.OutputStream;

// Удобный класс-декоратор, который к output стриму добавляет метод по записи SocketMessage.
// Заодно в нём и определено, как это сообщение правильно записывать
public class SocketMessageOutputStream extends OutputStream {
    private final OutputStream outputStream;

    public SocketMessageOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    public void writeSocketMessage(SocketMessage socketMessage) throws IOException {
        // Правильная запись. Просто всё отправляем в нужном порядке
        outputStream.write(socketMessage.getMessageLength());
        outputStream.write(socketMessage.getMessageData());
    }
}
