package com.oceanli.gupao.spring.framework.beans.factory;

public interface GPBeanFactory {

    Object getBean(String name);

    Object getBean(Class<?> beanClass) throws Exception;
}
