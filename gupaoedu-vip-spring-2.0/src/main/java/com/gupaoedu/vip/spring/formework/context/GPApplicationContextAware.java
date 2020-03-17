package com.gupaoedu.vip.spring.formework.context;

/**
 * IOC容器注入接口，只要实现该接口，就能实现IOC容器注入该类中
 * 通过监听器去扫描所有实现该接口的类，通过setApplicationContext()，
 * 实现IOC容器注入
 */
public interface GPApplicationContextAware {

    void setApplicationContext(GPApplicationContext applicationContext);
}
