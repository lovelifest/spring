package org.example.proxy;

import java.lang.reflect.Proxy;

/**
 * @author songtao
 * @create 2020-04-2020/4/8-0:18
 */
public class MyProxyFactory {

    public static Object getProxy(Object target) {
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new MyInvocationHandle(target));
        return proxy;
    }

}
