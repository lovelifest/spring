package org.example;

import org.example.entity.RpcRequest;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author songtao
 * @create 2020-04-2020/4/7-21:44
 */
public class ProcessorHandler implements  Runnable {
    private Socket socket;
    Map<String,Object> handlerMap;

    public ProcessorHandler(Socket socket, Map<String,Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            //输入流中应该有哪些信息
            //请求哪个类、方法名称、参数

                RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
                Object reslut = invoke(rpcRequest);
                 objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                 objectOutputStream.writeObject(reslut);
                 objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(objectOutputStream != null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest rpcRequest){
        String className = rpcRequest.getClassName();
        //增加版本号
        String version = rpcRequest.getVersion();
        if(!StringUtils.isEmpty(version)){
            className+="-"+version;
        }

        Object service = handlerMap.get(className);
        if(service == null){
            throw new RuntimeException("service not found:"+className);
        }


        Object[] args = rpcRequest.getParameters();
        Class<?>[] types = new Class[args.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = args[i].getClass();
        }

        try {
            Class<?> clazz = Class.forName(rpcRequest.getClassName());
            Method method = clazz.getMethod(rpcRequest.getMethodName(),types);
            Object result =  method.invoke(service,args);
            return result;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }



}
