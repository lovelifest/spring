package com.funtl.st.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author songtao
 * @create 2020-04-2020/4/15-23:29
 */
public class ScatteringAndGatheringDemo {
    public static void main(String[] args) throws IOException {

        /**
         * Scattering:将数据写入到buffer时,可以采用buffer数组，依次写入【分散】
         * Gathering：从buffer读取数据时，可以采用buffer数组，依次读 【聚合】
         */

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        //绑定端口到socket并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建一个buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接（telent）
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;//假定从客户端接受8个字节
        //循环读取
        while (true){
            int byteRead = 0;
            while (byteRead < messageLength){
                long read = socketChannel.read(byteBuffers);//读取到的个数
                byteRead += read;//累计读了多少字节
                System.out.println("byteRead= " + byteRead);
                //使用流打印，看看当前的这个buffer 的position 和 limit
                Arrays.asList(byteBuffers).stream().map(byteBuffer ->
                        "position="+byteBuffer.position()+",limit="+byteBuffer.limit()
                ).forEach(System.out::println);
            }

            //将所有的buffer反转
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());

            //将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite <messageLength){
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }

            //将所有的buffer复位
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());

            System.out.println("byteRead:"+byteRead+" byteWrite:"+ byteWrite+" messageLength:"+messageLength );

        }


    }
}
