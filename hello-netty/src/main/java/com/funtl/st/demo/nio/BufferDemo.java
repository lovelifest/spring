package com.funtl.st.demo.nio;

import java.nio.IntBuffer;

/**
 * @author songtao
 * @create 2020-04-2020/4/15-22:16
 */
public class BufferDemo {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(5);
        //存储数据
        buffer.put(1);
        buffer.put(2);
        buffer.put(3);
        buffer.put(4);

        //读写切换
        buffer.flip();

        //读取数据
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }

}
