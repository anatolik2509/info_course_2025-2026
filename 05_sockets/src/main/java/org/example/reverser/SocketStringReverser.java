package org.example.reverser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

// Сервер, который через сокет принимает строку, разворачивает её и отправляет назад. Очень полезно
public class SocketStringReverser {

    public static void main(String[] args) throws IOException {
        // Чтобы сервер мог одновременно обрабатывать несколько соединений - создаём тред-пул.
        // В нём живут несколько потоков (тут мы задали, что их 10), которые асинхронно будут выполнять задания,
        // которые мы туда закинем
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingDeque<>();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.DAYS, taskQueue);
        ServerSocket serverSocket = new ServerSocket(1234);
        while (true) {
            // Принимаем соеднинение и делегируем работу с ним какому-нибудь треду.
            // Пока другой тред занимается работой с соединением - ждём новых соединений.
            Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(() -> handleConnection(socket));
        }
    }

    private static void handleConnection(Socket socket) {
        try {
            // Стоит почитать, что написано в комментариях к этим двум классам
            SocketMessageOutputStream outputStream = new SocketMessageOutputStream(socket.getOutputStream());
            SocketMessageInputStream inputStream = new SocketMessageInputStream(socket.getInputStream());
            while (true) {
                // Принимаем сообщение со строкой, разворачиваем и отправляем назад.
                // И так, пока не выбросится исключение в процессе
                SocketMessage request = inputStream.readSocketMessage();
                String reversed = reverseString(request.getStringData());
                outputStream.writeSocketMessage(new SocketMessage(reversed));
            }
        } catch (IOException e) {
            // Если возникло исключение ввода/вывода (EOFException тоже к ним относится, а он означает, что соединение закрыто),
            // то считаем, что соединение закрыто. Дополнительно на всякий закрываем сокет
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
