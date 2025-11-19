package org.example.nio.channels;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class SocketChannels {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(1234));
        SocketChannel socketChannel = serverSocketChannel.accept();

        socketChannel.configureBlocking(true);
        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
        int iter = 0;
        while (true) {
            iter++;
            int readed = socketChannel.read(byteBuffer);
            if (readed == -1) {
                break;
            }
            if (readed == 0) {
                continue;
            }
            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.limit()];
            byteBuffer.get(0, bytes);
            System.out.println("Message: " + new String(bytes, StandardCharsets.UTF_8));
        }
        System.out.println("Total iterations: " + iter);
        socketChannel.close();
        serverSocketChannel.close();
    }

    private static class SocketClient {
        public static void main(String[] args) throws IOException, InterruptedException {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(InetAddress.getLocalHost(), 1234));
            Thread.sleep(100);
            String message = "Hello!";
            ByteBuffer byteMessage = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
            socketChannel.write(byteMessage);
            Thread.sleep(100);
            message = "Its me!";
            byteMessage = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
            socketChannel.write(byteMessage);
            socketChannel.close();
        }
    }
}
