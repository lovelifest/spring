package com.funtl.st.hellodistributed.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author songtao
 * @create 2020-04-2020/4/6-20:41
 */
public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket socket = new ServerSocket(8080);
        Socket accept = socket.accept();

        ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
        User user = (User)objectInputStream.readObject();
        System.out.println(user.toString());

    }
}
