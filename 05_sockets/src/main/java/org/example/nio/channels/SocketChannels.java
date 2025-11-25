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
        // SocketChannel - аналог сокета из nio. В основе мало чем отличается по принципу работы
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(1234));
        SocketChannel socketChannel = serverSocketChannel.accept();

        // Блокирующий или неблокирующий режим.
        // Когда мы читаем/пишем через input/output стрим в io - программа останавливается, пока операция не будет выполнена.
        // В случае чтения будет ждать пока в сокете не появятся данные. При записи будет писать, пока всё не запишет.
        // В nio для сетевых каналов добавили возможность включить неблокирующий режим.
        // В неблокирующем режиме прочитается/запишется сколько успеет.
        // Может вообще ничего не прочитать, если ничего нет. В блокирующем режиме - будет ждать, пока данные в канале появятся.
        // Это полезно для сетевых операций, когда операции чтения записи могут растянуться из-за сетевых задержкек и
        // обработок на другой стороне. Вместо того, чтобы ждать, можно пойти обработать какие-нибудь другие каналы.
        // Сделать неблокируемым чтение/запись для файловых каналов нельзя.
        // Можете попробовать поставить тут false и посмотреть разницу в выводимом "Total iterations"
        socketChannel.configureBlocking(true);
        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
        int iter = 0;
        while (true) {
            iter++;
            // Читаем в буфер
            int readed = socketChannel.read(byteBuffer);
            if (readed == -1) {
                // Значит сетевой канал закрылся, выходим из цикла
                break;
            }
            if (readed == 0) {
                // Значит ничего не прочитали, начинаем сначала
                continue;
            }
            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.limit()];
            byteBuffer.get(bytes);
            System.out.println("Message: " + new String(bytes, StandardCharsets.UTF_8));
            byteBuffer.clear();
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
            // У буферов есть полезный метод, чтобы сразу завернуть в буфер готовые данные
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
