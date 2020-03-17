package com.oceanli.gupao.spring.framework.aop;


import com.oceanli.gupao.spring.framework.aop.intercept.GPMethodInvocation;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class GPCglibAopProxy implements GPAopProxy, MethodInterceptor {

    private GPAdvisedSupport config;

    public GPCglibAopProxy(GPAdvisedSupport config) {
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
        //创建Enhancer对象
        Enhancer enhancer = new Enhancer();
        // 设置要代理的目标类，以扩展功能
        enhancer.setSuperclass(this.config.getTarget().getClass());
        // 设置单一回调对象，在回调中拦截对目标方法的调用
        enhancer.setCallback(this);
        // 设置类加载器
        enhancer.setClassLoader(classLoader);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        GPMethodInvocation invocation = null;
        List<Object> chain = this.config.getInterceptorsAndDynamicInterceptionAdvice(method, this.config.getTargetClass());
        if (chain == null || chain.isEmpty()) {

            return method.invoke(this.config.getTarget(), args);
        } else {
            invocation = new GPMethodInvocation(o, this.config.getTarget(), method, args, this.config.getTargetClass(), chain);
        }
        return invocation.proceed();
    }
}
