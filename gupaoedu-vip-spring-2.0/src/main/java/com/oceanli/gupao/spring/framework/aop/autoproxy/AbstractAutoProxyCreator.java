package com.oceanli.gupao.spring.framework.aop.autoproxy;

import com.oceanli.gupao.spring.framework.aop.GPAdvisedSupport;
import com.oceanli.gupao.spring.framework.aop.GPAopConfig;
import com.oceanli.gupao.spring.framework.aop.GPCglibAopProxy;
import com.oceanli.gupao.spring.framework.aop.GPJdkDynamicAopProxy;
import com.oceanli.gupao.spring.framework.beans.factory.support.GPBeanDefinitionReader;

public class AbstractAutoProxyCreator {

    private GPBeanDefinitionReader reader;
    private GPAdvisedSupport config;

    public Object wrapIfNecessary(Object bean, String beanName, GPAdvisedSupport config) {

        this.config = config;
        if (this.config.pointCutMatch()) {
            Object proxy = createProxy(bean.getClass(), beanName);
            return proxy;
        }
        return bean;
    }

    protected Object createProxy(Class<?> beanClass, String beanName) {

        Class<?>[] interfaces = beanClass.getInterfaces();
        if (interfaces.length > 0 ) {
            return new GPJdkDynamicAopProxy(this.config).getProxy();
        }
        return new GPCglibAopProxy(this.config).getProxy();
    }


}
