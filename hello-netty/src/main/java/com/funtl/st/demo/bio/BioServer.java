package com.funtl.st.demo.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author songtao
 * @create 2020-04-2020/4/15-21:57
 */
public class BioServer {

    public static void main(String[] args) throws IOException {

        //线程池机制
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(18888);

        System.out.println("服务器启动了.....");

        while (true){
            System.out.println("线程信息id = "+Thread.currentThread().getId()+"         名字 = "+Thread.currentThread().getName());

            //监听，等待客户端连接
            System.out.println("等待连接.....");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            //就创建一个线程，与之通讯（单独写一个方法）
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //可以和客户端通讯
                    handler(socket);
                }
            });
        }


    }

    private static void handler(Socket socket) {

        try {
            System.out.println("线程信息id = "+Thread.currentThread().getId()+"名字 = "+Thread.currentThread().getName());
            byte[] bytes = new byte[3];
            //通过socket获取流
            InputStream is = socket.getInputStream();

            //循环的读取客户端发送过来的数据
            while (true){
                System.out.println("线程信息id = "+Thread.currentThread().getId()+"名字 = "+Thread.currentThread().getName());
                System.out.println("read......");
                int read = is.read(bytes);
                if(read !=-1){
                    System.out.println(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
