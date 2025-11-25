package org.example.reverser;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 1234);
        Scanner scanner = new Scanner(System.in);
        // Стоит почитать, что написано в комментариях к этим двум классам
        SocketMessageOutputStream outputStream = new SocketMessageOutputStream(socket.getOutputStream());
        SocketMessageInputStream inputStream = new SocketMessageInputStream(socket.getInputStream());
        while (true) {
            String s = scanner.nextLine();
            // Заворачиваем то, что нам написали в консоли, в сообщение и отправляем
            outputStream.writeSocketMessage(new SocketMessage(s));
            // Читаем ответ. Как видим, взаимодействие через сокеты двухстороннее, мы можем как отправлять, так и читать отправленное.
            // Хотя здесь у нас модель запрос-ответ, это вполне можно делать параллельно, то есть одновременно и писать, и читать.
            // Можем читать, когда захотим, и писать, когда захотим.
            // Поэтому сокеты часто используются как раз когда модель запрос-ответ неэффективна.
            // Например, чат. Клиент может постоянно спрашивать у сервера "есть новые сообщения?",
            // а может один раз подключиться и сервер сам отправит клиенту новые сообщения
            SocketMessage response = inputStream.readSocketMessage();
            System.out.println(response.getStringData());
        }
        // Тут я ничего не закрываю, потому что сделал по-проще. В данной реализации программа завершится только
        // если её убить. В идеале нужно предусмотреть какую-нибудь команду, по которому приложение закончит работу
    }
}
