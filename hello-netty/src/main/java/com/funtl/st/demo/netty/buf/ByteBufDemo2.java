package com.funtl.st.demo.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;


/**
 * @author songtao
 * @create 2020-04-2020/4/21-22:06
 */
public class ByteBufDemo2 {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.copiedBuffer("hello,world!", Charset.forName("utf-8"));
        if(buf.hasArray()){
            byte[] content = buf.array();
            System.out.println(new String(content, Charset.forName("utf-8")));
            System.out.println("buf="+buf);
            System.out.println(buf.arrayOffset());
            System.out.println(buf.writerIndex());
            System.out.println(buf.readerIndex());
            System.out.println(buf.capacity());

            System.out.println(buf.readByte());
            System.out.println((char) buf.readByte());
            System.out.println((char) buf.getByte(0));
            System.out.println(buf.readableBytes());
            for (int i = 0; i < buf.readableBytes(); i++) {
                System.out.println((char)buf.getByte(i));
            }

            buf.getCharSequence(0, 4, Charset.forName("utf-8"));
        }
    }
}
