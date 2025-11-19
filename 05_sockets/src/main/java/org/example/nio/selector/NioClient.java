package org.example.nio.selector;

import org.example.reverser.SocketMessage;
import org.example.reverser.SocketMessageInputStream;
import org.example.reverser.SocketMessageOutputStream;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NioClient {
    public static void main(String[] args) throws IOException {
        MessageChannelReader messageChannelReader = new MessageChannelReader();
        MessageChannelWriter messageChannelWriter = new MessageChannelWriter();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), 1234));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.nextLine();
            messageChannelWriter.writeMessage(new ReverserMessage(s), socketChannel);
            ReverserMessage response = messageChannelReader.readMessageFromChannel(socketChannel);
            System.out.println(response.getStringData());
        }
    }
}
