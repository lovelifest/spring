package com.funtl.st.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author songtao
 * @create 2020-04-2020/4/16-21:52
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        //创建serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //获得一个selector
        Selector selector = Selector.open();
        //绑定一个端口，在服务端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel 注册到 selector 关心的事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("注册到selector上key的数量："+selector.keys().size());
        //循环等待客户端连接
        while (true){
            if(selector.select(1000) == 0){//没有事件发生
                System.out.println("服务器等待了1秒，无连接。。。。。");
                continue;
            }

            //如果返回的>0,就获取到相关的selectionKey集合
            //1、如果返回的>0，表示已经获取了关注的事件
            //2、selector.selectedKeys()返回关注事件的集合
            //通过selectionKeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历Set<SelectorKey>,使用迭代器遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                //获取到SelectionKey
                SelectionKey key = iterator.next();
                //根据key 对应的通道发生的事件做相应的处理
                if(key.isAcceptable()){//如果是OP_ACCEPT，表示是有新的客户端连接
                    //该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);

                    System.out.println("客户端连接成功 生成了一个socketChannel "+socketChannel.hashCode());
                    //将当前的socketChannel 注册到selector上,关注事件为OP_READ,同时给socketChannel关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("注册到selector上key的数量："+selector.keys().size());
                }

                if(key.isReadable()){//发生OP_READ
                    //通过key 反向获取到对应的channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer)key.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端："+new String(buffer.array()));
                }

                //手动从集合中移除当前的selectionKey，防止重复操作
                iterator.remove();

            }
        }
    }
}
