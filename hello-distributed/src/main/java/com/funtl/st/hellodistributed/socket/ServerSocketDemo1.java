package com.funtl.st.hellodistributed.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author songtao
 * @create 2020-04-2020/4/5-21:22
 */
public class ServerSocketDemo1 {
    public static void main(String[] args) {
        try {
            //服务端监听一个端口号,ip默认情况下是本机的地址
            //ip:port
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket socket = serverSocket.accept();
            //拿到输入流
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //输出流
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            //通过控制台拿到数据
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Clinet:"+in.readLine());//获得输入流的信息
            String line = sin.readLine();//获得控制台输入的数据
            while (!line.equals("bye")){
                printWriter.println(line);//写回到客户端
                printWriter.flush();
                System.out.println("Clinet:"+in.readLine());//读取客户端传过来的数据
                line = sin.readLine();//重新读取控制台的数据
            }

            System.out.println(in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
