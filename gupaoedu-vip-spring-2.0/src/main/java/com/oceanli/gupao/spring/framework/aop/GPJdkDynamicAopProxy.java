package com.oceanli.gupao.spring.framework.aop;

import com.oceanli.gupao.spring.framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class GPJdkDynamicAopProxy implements GPAopProxy, InvocationHandler {

    private GPAdvisedSupport config;

    public GPJdkDynamicAopProxy(GPAdvisedSupport config) {
        this.config = config;
    }

    public GPAdvisedSupport getConfig() {
        return config;
    }

    public void setConfig(GPAdvisedSupport config) {
        this.config = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.config.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader, this.config.getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        GPMethodInvocation invocation = null;
        List<Object> chain = this.config.getInterceptorsAndDynamicInterceptionAdvice(method, this.config.getTargetClass());
        if (chain == null || chain.isEmpty()) {

            return method.invoke(this.config.getTarget(), args);
        } else {
            invocation = new GPMethodInvocation(proxy, this.config.getTarget(), method, args, this.config.getTargetClass(), chain);
        }
        return invocation.proceed();
    }


}
