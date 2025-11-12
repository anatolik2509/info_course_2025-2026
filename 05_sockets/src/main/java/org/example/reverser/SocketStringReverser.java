package org.example.reverser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class SocketStringReverser {

    public static void main(String[] args) throws IOException {
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingDeque<>();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.DAYS, taskQueue);
        ServerSocket serverSocket = new ServerSocket(1234);
        while (true) {
            Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(() -> handleConnection(socket));
        }
    }

    private static void handleConnection(Socket socket) {
        try {
            SocketMessageOutputStream outputStream = new SocketMessageOutputStream(socket.getOutputStream());
            SocketMessageInputStream inputStream = new SocketMessageInputStream(socket.getInputStream());
            while (true) {
                SocketMessage request = inputStream.readSocketMessage();
                String reversed = reverseString(request.getStringData());
                outputStream.writeSocketMessage(new SocketMessage(reversed));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connection closed");
            try {
                socket.close();
            } catch (IOException ignored) {

            }
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
