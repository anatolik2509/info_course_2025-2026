package org.example.reverser;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 1234);
        Scanner scanner = new Scanner(System.in);
        SocketMessageOutputStream outputStream = new SocketMessageOutputStream(socket.getOutputStream());
        SocketMessageInputStream inputStream = new SocketMessageInputStream(socket.getInputStream());
        while (true) {
            String s = scanner.nextLine();
            outputStream.writeSocketMessage(new SocketMessage(s));
            SocketMessage response = inputStream.readSocketMessage();
            System.out.println(response.getStringData());
        }
    }
}
