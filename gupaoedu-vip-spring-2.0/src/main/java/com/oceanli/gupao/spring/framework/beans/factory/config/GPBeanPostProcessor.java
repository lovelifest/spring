package com.oceanli.gupao.spring.framework.beans.factory.config;

import com.oceanli.gupao.spring.framework.aop.GPAdvisedSupport;
import com.oceanli.gupao.spring.framework.aop.autoproxy.AbstractAutoProxyCreator;
import com.oceanli.gupao.spring.framework.beans.factory.support.GPBeanDefinitionReader;

public class GPBeanPostProcessor {

    public GPBeanDefinitionReader reader;

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName, GPAdvisedSupport config) throws Exception {

        AbstractAutoProxyCreator proxyCreator = new AbstractAutoProxyCreator();
        Object proxy = proxyCreator.wrapIfNecessary(bean, beanName, config);
        return proxy;
    }
}
