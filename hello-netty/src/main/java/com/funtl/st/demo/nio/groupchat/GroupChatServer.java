package com.funtl.st.demo.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author songtao
 * @create 2020-04-2020/4/18-21:08
 */
public class GroupChatServer {
    ServerSocketChannel listenChannel;
    Selector selector;

    public GroupChatServer() {
        try {
            listenChannel = ServerSocketChannel.open();
            selector = Selector.open();
            listenChannel.configureBlocking(false);
            listenChannel.socket().bind(new InetSocketAddress(6667));
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
                 while (true) {
                    int count = selector.select();
                    if (count > 0) {
                        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            if (key.isAcceptable()) {
                                SocketChannel socketChannel = listenChannel.accept();
                                socketChannel.configureBlocking(false);
                                socketChannel.register(selector, SelectionKey.OP_READ);
                                System.out.println(socketChannel.getRemoteAddress() + " 上线 ");
                            }

                            if (key.isReadable()) {
                                readData(key);
                            }

                            iterator.remove();
                        }
                    } else {
                        System.out.println("等待客户端连接.....");
                 }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = channel.read(byteBuffer);
            if (count > 0) {
                String string = new String(byteBuffer.array());
                System.out.println("来自客户端消息：" + string);
                //向其他客户端发送消息，除了自己
                sendMsgToAtherClient(string, channel);
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了。。。 ");
                key.cancel();
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendMsgToAtherClient(String string, SocketChannel self) {
        for (SelectionKey key : selector.keys()) {
            SelectableChannel channel1 = key.channel();
            if (channel1 instanceof SocketChannel && channel1 != self) {
                SocketChannel socketChannel = (SocketChannel) channel1;
                ByteBuffer byteBuffer = ByteBuffer.wrap(string.getBytes());
                try {
                    socketChannel.write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
