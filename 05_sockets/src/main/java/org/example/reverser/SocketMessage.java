package org.example.reverser;

import java.nio.charset.StandardCharsets;

// Сокеты подразумевают общение на байтах, которые сами по себе могут означать вообще что угодно.
// Поэтому обе стороны коммуникации должны общаться как-то согласованно,
// чтобы смысл каждого байта был понятен и клиенту, и серверу.
// Получается свой "протокол" для общения. Идеально, когда для этого определены конкретные java классы,
// которые формат общения фиксируют программно.
// Этот класс как раз отвечает за формат сообщения, которое будет передаваться через сокеты
public class SocketMessage {

    // Константа, отражающая, сколько байтов занимает информация о длине сообщения.
    public static final int MESSAGE_LENGTH_SIZE_IN_BYTES = 2;

    // Байты, отвечающие за длину сообещния. Чтобы каждая сторона понимала,
    // когда одно сообщение закончилось и началось новое
    private final byte[] messageLength;
    // Байты для информации, которую сообщение передаёт. В сообщении к серверу это будет слово, которое надо развернуть,
    // а в сообщении от сервера развёрнутое слово.
    private final byte[] messageData;

    public SocketMessage(String data) {
        this.messageLength = new byte[MESSAGE_LENGTH_SIZE_IN_BYTES];
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
