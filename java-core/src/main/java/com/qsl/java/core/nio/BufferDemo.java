package com.qsl.java.core.nio;

import java.nio.ByteBuffer;

/**
 * NIO中的缓冲区
 *
 * @author qianshuailong
 * @date 2021/7/1
 */
public class BufferDemo {

    public static void main(String[] args) {
        byte[] data = new byte[]{11, 127, 3};
        ByteBuffer buffer = ByteBuffer.wrap(data);
        System.out.println(buffer.capacity());

        System.out.println("===========================");

        ByteBuffer direct = ByteBuffer.allocateDirect(100);
        System.out.println("position=" + direct.position());
        System.out.println("capacity=" + direct.capacity());
        System.out.println("limit=" + direct.limit());

        direct.put(data);
        System.out.println("position=" + direct.position());

//        buffer.position(0);
        byte[] dst = new byte[3];
        direct.get(dst);

        System.out.print("dst=[");
        for(int i = 0; i < dst.length; i++) {
            System.out.print(i);
            if(i < dst.length - 1) {
                System.out.print(",");
            }
        }
        System.out.print("]");


    }
}
