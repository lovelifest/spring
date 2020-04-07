package com.funtl.st.hellodistributed.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author songtao
 * @create 2020-04-2020/4/6-20:46
 */
public class Client {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 8080);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        User user = new User();
        user.setName("tom");
        user.setAge(18);
        objectOutputStream.writeObject(user);
    }
}
