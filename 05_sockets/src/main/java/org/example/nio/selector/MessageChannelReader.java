package org.example.nio.selector;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public class MessageChannelReader {
    public ReverserMessage readMessageFromChannel(ByteChannel byteChannel) throws IOException {
        ByteBuffer lengthByteBuffer = ByteBuffer.allocate(2);
        while (lengthByteBuffer.position() != lengthByteBuffer.limit()) {
            int readed = byteChannel.read(lengthByteBuffer);
            if (readed == -1) {
                throw new EOFException();
            }
        }
        lengthByteBuffer.flip();
        int messageLength = lengthByteBuffer.get() + (lengthByteBuffer.get() << 8);
        ByteBuffer dataByteBuffer = ByteBuffer.allocate(messageLength);
        while (dataByteBuffer.position() != dataByteBuffer.limit()) {
            int readed = byteChannel.read(dataByteBuffer);
            if (readed == -1) {
                throw new EOFException();
            }
        }
        dataByteBuffer.flip();
        byte[] lengthBytes = new byte[2];
        byte[] dataBytes = new byte[messageLength];
        lengthByteBuffer.get(0, lengthBytes);
        dataByteBuffer.get(0, dataBytes);
        return new ReverserMessage(lengthBytes, dataBytes);
    }
}
