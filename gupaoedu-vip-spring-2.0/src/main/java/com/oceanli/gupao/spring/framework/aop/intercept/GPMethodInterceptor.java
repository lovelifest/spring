package com.oceanli.gupao.spring.framework.aop.intercept;

public interface GPMethodInterceptor {

    Object invoke(GPMethodInvocation invocation) throws Throwable;
}
