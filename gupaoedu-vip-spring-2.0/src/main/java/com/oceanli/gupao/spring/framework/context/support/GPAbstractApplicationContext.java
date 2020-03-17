package com.oceanli.gupao.spring.framework.context.support;

public abstract class GPAbstractApplicationContext {

    protected abstract void refreshBeanFactory() throws Exception ;

    //受保护，只提供给子类重写
    public void refresh() throws Exception {}
}
