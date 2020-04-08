package org.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author songtao
 * @create 2020-04-2020/4/8-0:17
 */
public class MyInvocationHandle implements InvocationHandler {
    private Object target;

    public MyInvocationHandle(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        DogUtils.method1();
        method.invoke(target, args);
        DogUtils.method2();
        return null;
    }
}
