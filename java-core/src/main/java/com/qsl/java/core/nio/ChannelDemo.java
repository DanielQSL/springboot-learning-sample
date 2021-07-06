package com.qsl.java.core.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author qianshuailong
 * @date 2021/7/1
 */
public class ChannelDemo {

    public static void main(String[] args) throws Exception {
        // 构造一个传统的文件输出流
        FileOutputStream out = new FileOutputStream("D:\\temp\\hello.txt");
        // 通过文件输出流获取到对应的FileChannel，以NIO的方式来写文件
        FileChannel channel = out.getChannel();

        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
        channel.write(buffer);

        System.out.println(buffer.position());
        System.out.println(channel.position());

        buffer.rewind();

        channel.position(5);
        channel.write(buffer);

        channel.close();
        out.close();
    }

}
