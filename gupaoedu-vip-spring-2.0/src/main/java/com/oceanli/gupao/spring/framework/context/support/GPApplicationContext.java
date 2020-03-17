package com.oceanli.gupao.spring.framework.context.support;

import com.oceanli.gupao.spring.demo.controller.DemoAction;
import com.oceanli.gupao.spring.framework.beans.factory.GPBeanFactory;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public class GPApplicationContext extends GPAbstractRefreshableApplicationContext implements GPBeanFactory {

    public GPApplicationContext(String... configLocations) throws Exception{

        setConfigLocations(configLocations);
        refresh();

    }

    @Override
    public Object getBean(String name) {

        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(Class<?> beanClass) throws Exception {
        return getBean(beanClass.getName());
    }

    public String[] getBeanDefinitionNames() {
        Set<String> keySet = getBeanFactory().beanDefinitionMap.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }

    public Properties getConfig() {
        return super.reader.getConfig();
    }

    public static void main(String[] args) throws Exception {
        GPApplicationContext context = new GPApplicationContext("classpath:application.properties");
        DemoAction demoAction1 = (DemoAction) context.getBean("demoAction");
        DemoAction demoAction2 = (DemoAction) context.getBean("demoAction");
        System.out.println(context.getBeanFactory());
    }

}
