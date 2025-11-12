package org.example.reverser;

import java.nio.charset.StandardCharsets;

public class SocketMessage {
    private final byte[] messageLength;
    private final byte[] messageData;

    public SocketMessage(String data) {
        this.messageLength = new byte[2];
        int dataLength = data.length();
        messageLength[0] = (byte) dataLength;
        messageLength[1] = (byte) (dataLength >> 8);
        messageData = data.getBytes(StandardCharsets.UTF_8);
    }

    public SocketMessage(byte[] messageLength, byte[] messageData) {
        this.messageLength = messageLength;
        this.messageData = messageData;
    }

    public String getStringData() {
        return new String(messageData, StandardCharsets.UTF_8);
    }

    public byte[] getMessageLength() {
        return messageLength;
    }

    public byte[] getMessageData() {
        return messageData;
    }
}
