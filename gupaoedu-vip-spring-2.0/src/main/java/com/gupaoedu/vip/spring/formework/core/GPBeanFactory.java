package com.gupaoedu.vip.spring.formework.core;

/**
 * 单例工厂的顶层设计
 */
public interface GPBeanFactory {

    /**
     * 单例模式提供全局访问点
     */
    Object getBean(String beanName);
}
