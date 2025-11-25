package org.example.simple;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        // Лучше сначала посмотреть комменты в SocketClient
        // В сокетном, да и практически в любом сетевом соединении кто-то первый инициирует создание соединение.
        // У нас первым соединение инциирует SocketClient.
        // Класс SocketServer в своб очередь просто ждёт, когда к нему подключаться.
        // Объект ниже под названием ServerSocket как раз занимает один из портов и ждёт, когда к нему кто-то подключится.
        ServerSocket serverSocket = new ServerSocket(1234);
        // Вызов метода accept() остановит программу до тех пор, пока к нам кто-нибудь не подключиться.
        // Когда это случиться - нам вернётся объект сокета, через который мы будем коммуницировать с подключившимся.
        Socket socket = serverSocket.accept();
        // Достаём input стрим, чтобы прочитать, что нам отправили и заворачиваем в Reader, чтобы было удобнее работать с символами
        InputStream inputStream = socket.getInputStream();
        Reader reader = new InputStreamReader(inputStream);
        // Своеобразный буфер для чтения того, что нам приходит
        char[] line = new char[8];
        // Читаем в буфер всё, что нам пришло, пока не прочитаем до конца. Каждый раз выводим то, что пришло
        while (reader.read(line) != -1) {
            System.out.println(new String(line));
        }
        // Не забываем всё закрыть
        reader.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}
