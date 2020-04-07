package com.funtl.st.hellodistributed.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author songtao
 * @create 2020-04-2020/4/5-21:27
 */
public class ClinentSocketDemo1 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            //在当前连接上写入输入
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //通过控制台拿到数据
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            //拿到输入流
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String readline = sin.readLine();//获得控制台的输入
            while (!readline.equals("bye")){
                out.println(readline);//写到服务端
                System.out.println("Server:"+in.readLine());
                readline = sin.readLine();//重新获取
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
