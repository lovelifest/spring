package org.example;

import java.lang.reflect.Proxy;

/**
 * @author songtao
 * @create 2020-04-2020/4/7-22:15
 */
public class RpcProxyClient {
    public <T> T clientProxy(final Class<T> interfaceCls,final String host,final int port){
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class<?>[]{interfaceCls}, new RemoteInvoatcationHander(host,port));
    }

}
