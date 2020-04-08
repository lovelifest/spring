package org.example;

import org.example.entity.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author songtao
 * @create 2020-04-2020/4/7-22:20
 */
public class RemoteInvoatcationHander implements InvocationHandler {
    private String host;
    private int port;

    public RemoteInvoatcationHander(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //请求会进入到这里
        System.out.println("come in");
        //请求数据的包装
        RpcRequest rpcRequest=new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setVersion("v2.0");
        //远程通信
        RpcNetTransport netTransport=new RpcNetTransport(host,port);
        Object result=netTransport.send(rpcRequest);

        return result;
    }
}
