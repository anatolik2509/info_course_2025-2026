package org.example.reverser;

import java.io.IOException;
import java.io.OutputStream;

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
        outputStream.write(socketMessage.getMessageLength());
        outputStream.write(socketMessage.getMessageData());
    }
}
