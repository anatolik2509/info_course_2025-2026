package org.example.nio.channels;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;

public class FileChannels {
    // Демонстрация работы каналов и буферов
    public static void main(String[] args) throws URISyntaxException, IOException {
        // Находим текстовый файл, который будем читать
        URL fileUrl = FileChannels.class.getResource("/war-and-peace.txt");
        Path filePath = Path.of(fileUrl.toURI());
        System.out.println(filePath.toAbsolutePath());
        // Создаём канал. Channel - это абстракция из nio.
        // Разработчики nio решили сделать свою большую иерархию классов и интерфейсов, отличающуюсю от input и output стримов
        ReadableByteChannel channel = FileChannel.open(filePath);
        // Каналы при чтении и записи используют буферы. Буферы - довольно полезная абстракция для работы с байтами (и не только).
        // Можете думать о них, как об обычных массивах. У буферов есть 3 важных int поля:
        // cap - capacity - сколько буфер вообще может в себя вместить. Тут у нас это - 128
        // lim - limit - сколько сейчас в буфере полезных данных. Полезно так как буфер не всегда заполняется полностью.
        //      Не может быть больше cap.
        // pos - position - указатель на ячейку, в которую мы запишем данные или прочитаем данные.
        //      После вызова метода get (прочитать ячейку/и) или put (записать ячейку/и) позиция сдвинется.
        //      Pos не может быть больше lim, иначе выбросится исключение.
        // Выделяем буфер на 128 байт
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        // В цикле читаем данные в буфер
        while (channel.read(byteBuffer) != -1) {
            // После чтения pos будет не равен нулю, а нам бы хотелось начать читать данне с начала буфера.
            // Метод flip сделает lim = pos, а pos равным нулю.
            byteBuffer.flip();
            // Создаём массив размером с количство полезных данных в буфере
            byte[] bytes = new byte[byteBuffer.limit()];
            // Читаем данные из буфера в массив
            byteBuffer.get(bytes);
            System.out.write(bytes);
            // Очищаем буфер, чтобы туда можно было заново писать
            byteBuffer.clear();
        }
        // Не забываем всё закрыть
        channel.close();
    }
}
