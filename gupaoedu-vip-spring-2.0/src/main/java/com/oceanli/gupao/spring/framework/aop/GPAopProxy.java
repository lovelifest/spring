package com.oceanli.gupao.spring.framework.aop;

public interface GPAopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
