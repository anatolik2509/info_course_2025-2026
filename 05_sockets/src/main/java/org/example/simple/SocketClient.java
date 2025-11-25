package org.example.simple;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        // Это сокет. Своеобразный "канал" для коммуникации двух разных процессов - если говорить в общем.
        // В нашем контексте обычно сокет подразумевает сетевое tcp соединение, поэтому в конструкторе мы указываем сетевой адрес и порт.
        Socket socket = new Socket(InetAddress.getLocalHost(), 1234);
        // У сокета можно получить input и output стримы, чтобы читать и писать в сокет соответственно.
        // В этом примере мы использем сокет только для записи, поэтому мы берём output стрим
        // Всё, что мы запишем в output появится на "другой стороне" сокета в input стриме
        OutputStream outputStream = socket.getOutputStream();
        // Создаём Writer т.к. input/output стримы позволяют работать чисто с байтами,
        // а Writer-ы и Reader-ы помогают нам работать с символами, самостоятельно превращая их в нужные байты для стримов
        Writer writer = new OutputStreamWriter(outputStream);
        // Пишем сообщение
        writer.write("Hello!");
        // Не забываем всё закрыть
        writer.close();
        outputStream.close();
        socket.close();
    }
}
