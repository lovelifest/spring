package com.funtl.st.demo.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author songtao
 * @create 2020-04-2020/4/18-22:46
 */
public class ZeroCopyClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7777));
        FileChannel channel = new FileInputStream("a.zip").getChannel();
        long l = System.currentTimeMillis();
        long transferCount = channel.transferTo(0, channel.size(), socketChannel);
        System.out.println("发送的总字节数 = "+transferCount+"耗时："+(System.currentTimeMillis()-l));
        channel.close();
    }
}
