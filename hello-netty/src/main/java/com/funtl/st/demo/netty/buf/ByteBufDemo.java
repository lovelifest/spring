package com.funtl.st.demo.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author songtao
 * @create 2020-04-2020/4/21-21:47
 */
public class ByteBufDemo {
    public static void main(String[] args) {

        //创建一个对象
        //对象包含一个数组arr,是一个byte[10]
        //在netty的buffer中，不需要使用flip进行读写反转
        //底层维护了readerindex 和 writerindex
        //通过readerindex 和 writerindex 将buffer分成三个区域
        //0-readerindex 已经读取的区域
        //readindex 到 writeindex ,可读区域
        //writeindex 到 capcity , 可写的区域

        ByteBuf buffer = Unpooled.buffer(10);
        System.out.println(" buffer 的容量 ：" + buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.writeByte(i);
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.getByte(i);
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.readByte();
        }

    }
}
