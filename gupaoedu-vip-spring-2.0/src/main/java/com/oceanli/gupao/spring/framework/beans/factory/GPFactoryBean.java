package com.oceanli.gupao.spring.framework.beans.factory;

public interface GPFactoryBean<T> {

    T getObject();

    default boolean isSingleton() {
        return true;
    }
}
