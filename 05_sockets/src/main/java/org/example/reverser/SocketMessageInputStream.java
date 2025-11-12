package org.example.reverser;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
        byte[] lengthBytes = new byte[2];
        if (inputStream.read(lengthBytes) == -1) {
            throw new EOFException();
        }
        int messageLength = lengthBytes[0] + (lengthBytes[1] << 8);
        byte[] dataBytes = new byte[messageLength];
        int readedBytes = inputStream.read(dataBytes);
        if (readedBytes < messageLength) {
            System.out.println(new String(dataBytes, StandardCharsets.UTF_8));
            throw new EOFException("Readed %d bytes insted of %d".formatted(readedBytes, messageLength));
        }
        return new SocketMessage(lengthBytes, dataBytes);
    }
}
