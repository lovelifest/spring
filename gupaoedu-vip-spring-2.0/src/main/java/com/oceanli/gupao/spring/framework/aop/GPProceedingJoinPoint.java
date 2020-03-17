package com.oceanli.gupao.spring.framework.aop;

import com.oceanli.gupao.spring.framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

public class GPProceedingJoinPoint implements GPJoinPoint{

    private GPMethodInvocation methodInvocation;

    public Object proceed() throws Throwable {
        return this.methodInvocation.proceed();
    }

    public GPMethodInvocation getMethodInvocation() {
        return methodInvocation;
    }

    public void setMethodInvocation(GPMethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Method getMethod() {
        return methodInvocation.getMethod();
    }

    @Override
    public Object[] getArguments() {
        return methodInvocation.getArguments();
    }

    @Override
    public Object getThis() {
        return methodInvocation.getThis();
    }
}
