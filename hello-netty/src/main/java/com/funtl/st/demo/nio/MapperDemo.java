package com.funtl.st.demo.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author songtao
 * @create 2020-04-2020/4/15-23:14
 */
public class MapperDemo {
    public static void main(String[] args) throws IOException {
        /**
         * 1.MappedByteBuffer 可以直接让文件直接在内存（堆歪内存）中修改，操作系统不需要拷贝一次
         */

        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数1→读写模式
         * 参数2→可以直接修改的起始位置
         * 参数3→是映射内存的大小，将1.txt的多少个字节隐射到内存，可以修该的范围是0-5
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0,(byte)'H');
        mappedByteBuffer.put(3,(byte)'9');
        randomAccessFile.close();

    }
}
