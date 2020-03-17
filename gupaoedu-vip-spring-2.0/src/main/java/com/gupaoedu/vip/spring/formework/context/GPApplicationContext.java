package com.gupaoedu.vip.spring.formework.context;

import com.gupaoedu.vip.spring.formework.beans.config.GPBeanDefinition;
import com.gupaoedu.vip.spring.formework.beans.support.GPBeanDefinitionReader;
import com.gupaoedu.vip.spring.formework.beans.support.GPDefaultListableBeanFactory;
import com.gupaoedu.vip.spring.formework.core.GPBeanFactory;

import java.util.List;

/**
 * 按之前源码分析的套路，IOC、DI、MVC、AOP
 */
public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {

    private String[] configLocations;

    private GPBeanDefinitionReader reader;

    public GPApplicationContext(String ... configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    public void refresh() {
        //1.定位配置文件：通过配置文件位置获取资源加载器
        reader = new GPBeanDefinitionReader(configLocations);

        //2.加载配置文件：通过资源加载器读取配置文件内容（配置信息），封装成BeanDefinitions
        List<GPBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //3.注册（配置信息）：把BeanDefinitions(配置信息)，存入伪IOC容器（Map）
        doRegisterBeanDefinition(beanDefinitions);

        //4.初始化：非懒加载的bean初始化
        doAutowired();
    }

    private void doAutowired() {
    }

    private void doRegisterBeanDefinition(List<GPBeanDefinition> beanDefinitions) {
    }

    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
