package org.example.nio.selector;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

// Аналог SocketMessageInputStream (только не декоратор)
public class MessageChannelReader {
    public ReverserMessage readMessageFromChannel(ByteChannel byteChannel) throws IOException {
        // Выделяем буфер под длину сообщения
        ByteBuffer lengthByteBuffer = ByteBuffer.allocate(2);
        // Так как канал неблокирующий - читаем данные, пока не вычитаем нужное колчиество. Иначе есть вероятность,
        // что вычитается только часть данных
        while (lengthByteBuffer.position() != lengthByteBuffer.limit()) {
            int readed = byteChannel.read(lengthByteBuffer);
            if (readed == -1) {
                throw new EOFException();
            }
        }
        lengthByteBuffer.flip();
        int messageLength = lengthByteBuffer.get() + (lengthByteBuffer.get() << 8);
        lengthByteBuffer.flip();
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
        lengthByteBuffer.get(lengthBytes);
        dataByteBuffer.get(dataBytes);
        return new ReverserMessage(lengthBytes, dataBytes);
    }
}
