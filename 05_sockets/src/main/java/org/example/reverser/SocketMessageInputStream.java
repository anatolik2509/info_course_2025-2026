package org.example.reverser;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

// Удобный класс-декоратор, который к input стриму добавляет метод по чтению SocketMessage.
// Заодно в нём и определено, как это сообщение правильно читать
public class SocketMessageInputStream extends InputStream {
    private final InputStream inputStream;

    public SocketMessageInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    public SocketMessage readSocketMessage() throws IOException {
        // Сначала читаем длину сообщения
        byte[] lengthBytes = new byte[2];
        if (inputStream.read(lengthBytes) == -1) {
            // Если внезапно поток закрылся, то кидаем EOFException. Как раз это исключение означает, что поток закрыт,
            // а мы мытаемся из него что-то прочитать.
            throw new EOFException();
        }
        int messageLength = lengthBytes[0] + (lengthBytes[1] << 8);
        byte[] dataBytes = new byte[messageLength];
        // Пытаемся прочитать столько байтов, сколько указано в длине
        int readedBytes = inputStream.read(dataBytes);
        // Если прочитано меньше байтов, чем должно быть, то сообщение по каким-то причинам целиком не дошло и соединение завершилось.
        // Кидаем исключение
        if (readedBytes < messageLength) {
            throw new EOFException("Readed %d bytes insted of %d".formatted(readedBytes, messageLength));
        }
        return new SocketMessage(lengthBytes, dataBytes);
    }
}
