package org.example.nio.selector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public class MessageChannelWriter {
    public void writeMessage(ReverserMessage message, ByteChannel byteChannel) throws IOException {
        byte[] lengthBytes = message.getMessageLength();
        int bufferLength = lengthBytes[0] + lengthBytes[1] << 8 + 2;
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferLength);
        byteBuffer.put(lengthBytes);
        byteBuffer.put(message.getMessageData());
        byteBuffer.flip();
        byteChannel.write(byteBuffer);
    }
}
