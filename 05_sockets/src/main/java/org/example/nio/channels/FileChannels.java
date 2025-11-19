package org.example.nio.channels;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;

public class FileChannels {
    public static void main(String[] args) throws URISyntaxException, IOException {
        URL fileUrl = FileChannels.class.getResource("/war-and-peace.txt");
        Path filePath = Path.of(fileUrl.toURI());
        System.out.println(filePath.toAbsolutePath());
        ReadableByteChannel channel = FileChannel.open(filePath);
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        while (channel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.limit()];
            byteBuffer.get(bytes);
            System.out.write(bytes);
            byteBuffer.clear();
        }
        channel.close();
    }
}
