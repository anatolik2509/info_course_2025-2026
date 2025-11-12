package org.example.simple;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        Reader reader = new InputStreamReader(inputStream);
        char[] line = new char[8];
        while (reader.read(line) != -1) {
            System.out.println(new String(line));
        }
        reader.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}
