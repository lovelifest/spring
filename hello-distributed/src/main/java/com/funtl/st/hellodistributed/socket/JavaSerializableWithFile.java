package com.funtl.st.hellodistributed.socket;

import java.io.*;

/**
 * @author songtao
 * @create 2020-04-2020/4/6-21:05
 */
public class JavaSerializableWithFile implements Iserializer {
    @Override
    public <T> byte[] serialize(T obj) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("user")));
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("user")));
            try {
                return (T)objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
