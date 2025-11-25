package org.example.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioStringReverser {

    private static final MessageChannelReader messageChannelReader = new MessageChannelReader();
    private static final MessageChannelWriter messageChannelWriter = new MessageChannelWriter();

    public static void main(String[] args) throws IOException {
        // Почти всё то же самое, что в SocketStringReverser, только делается в одном потоке с помощью Selector
        // Чем селектор лучше многопоточности? Иногда создаются такие условия, когда выделять по треду на каждое соединение -
        // дорого и опасно. Например, когда у нас очень нагруженный сервер, а работа с каждым соединением занимает длительное время.
        // В таком случае может накопиться много тредов, которые будут сильно грузить процессор
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(1234));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        // Чтобы добавить канал в селектор, нужно вызвать у канала метод register, куда передадим селектор и какие
        // события селектор должен отслеживать. Тут селектор будет отслеживать подключение новый соединений через ServerSocketChannel
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // Метод select обработает каждый канал, где произошло какое-то событие, с помощью переданного консьюмера
            selector.select(key -> handleSelection(key, selector));
        }
    }

    private static void handleSelection(SelectionKey selectionKey, Selector selector) {
        if (selectionKey.isAcceptable()) {
            // если какой-то канал готов принять новое соединение, то принимаем его и добавляем в селектор
            ServerSocketChannel currentChannel = (ServerSocketChannel) selectionKey.channel();
            registerNewChannel(selector, currentChannel);
        }
        if (selectionKey.isReadable()) {
            // если какой-то канал готов что-то прочитать, то читаем сообщение и отправляем ответ
            SocketChannel currentChannel = (SocketChannel) selectionKey.channel();
            processMessage(currentChannel);
        }
    }

    private static void registerNewChannel(Selector selector, ServerSocketChannel serverSocketChannel) {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            System.out.println("Error in accepting connection");
        }
    }

    private static void processMessage(SocketChannel socketChannel) {
        try {
            ReverserMessage message = messageChannelReader.readMessageFromChannel(socketChannel);
            String reversedString = reverseString(message.getStringData());
            ReverserMessage response = new ReverserMessage(reversedString);
            messageChannelWriter.writeMessage(response, socketChannel);
        } catch (IOException e) {
            System.out.println("Error in reading message");
            try {
                socketChannel.close();
            } catch (IOException ignored) {}
        }
    }

    private static String reverseString(String s) {
        char[] chars = s.toCharArray();
        char[] result = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = chars[chars.length - i - 1];
        }
        return new String(result);
    }
}
