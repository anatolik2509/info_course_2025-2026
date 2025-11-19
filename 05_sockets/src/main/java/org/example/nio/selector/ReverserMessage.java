package org.example.nio.selector;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ReverserMessage {
    private final byte[] messageLength;
    private final byte[] messageData;

    public ReverserMessage(String data) {
        this.messageLength = new byte[2];
        int dataLength = data.length();
        messageLength[0] = (byte) dataLength;
        messageLength[1] = (byte) (dataLength >> 8);
        messageData = data.getBytes(StandardCharsets.UTF_8);
    }

    public ReverserMessage(byte[] messageLength, byte[] messageData) {
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

    public static ReverserMessage fromBuffer(ByteBuffer byteBuffer) {
        byte[] lengthBytes = new byte[2];
        byteBuffer.get(0, lengthBytes);
        int messageLength = lengthBytes[0] + lengthBytes[1] << 8;
        byte[] data = new byte[messageLength];
        byteBuffer.get(2, data);
        return new ReverserMessage(lengthBytes, data);
    }
}
