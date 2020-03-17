package com.gupaoedu.vip.spring.formework.context.support;

/**
 * IOC容器实现的顶层设计
 */
public abstract class GPAbstractApplicationContext {

    /**
     * 需要子类重写，重启容器的入口
     */
    public void refresh() {}
}
