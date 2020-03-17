package com.oceanli.gupao.spring.framework.context.support;

import com.oceanli.gupao.spring.framework.beans.factory.config.GPBeanDefinition;
import com.oceanli.gupao.spring.framework.beans.factory.support.GPBeanDefinitionReader;
import com.oceanli.gupao.spring.framework.beans.factory.support.GPDefaultListableBeanFactory;

import java.util.List;
import java.util.Map;

public abstract class GPAbstractRefreshableApplicationContext extends GPAbstractApplicationContext {

    private String[] configLocations;

    private GPDefaultListableBeanFactory beanFactory;

    public GPBeanDefinitionReader reader;

    @Override
    protected void refreshBeanFactory() throws Exception{


        GPDefaultListableBeanFactory beanFactory = createBeanFactory();
        //1、定位，定位配置文件
        this.reader = new GPBeanDefinitionReader(configLocations);
        beanFactory.reader = reader;
        //2、加载配置文件，扫描相关的类，把它们转换成GPBeanDefinition
        List<GPBeanDefinition> gpBeanDefinitions = reader.doLoadBeanDefinitions();
        //3、注册，把配置信息GPBeanDefinition放到容器里面(伪 IOC 容器)
        doRegisterGPBeanDefinition(gpBeanDefinitions, beanFactory);
        //4、把不是延时加载的类，提前初始化
        doAutowired(beanFactory);
        this.beanFactory = beanFactory;


    }

    protected void doAutowired(GPDefaultListableBeanFactory beanFactory) {
        for (Map.Entry<String, GPBeanDefinition> beanDefinitionEntry :
                beanFactory.beanDefinitionMap.entrySet()) {
            String factoryBeanName = beanDefinitionEntry.getKey();
            GPBeanDefinition gpBeanDefinition = beanDefinitionEntry.getValue();
            if (!gpBeanDefinition.isLazyInit()) {
                beanFactory.getBean(factoryBeanName);
            }
        }
    }

    protected void doRegisterGPBeanDefinition(List<GPBeanDefinition> gpBeanDefinitions, GPDefaultListableBeanFactory beanFactory) throws Exception {

        for (GPBeanDefinition gpBeanDefinition : gpBeanDefinitions) {

            String factoryBeanName = gpBeanDefinition.getFactoryBeanName();
            if (beanFactory.beanDefinitionMap.containsKey(factoryBeanName)) {
                throw new Exception("The " + factoryBeanName + " is exist!!!");
            }
            beanFactory.beanDefinitionMap.put(factoryBeanName, gpBeanDefinition);
        }
    }

    public void setConfigLocations(String... locations) {
        if (locations != null) {
            this.configLocations = new String[locations.length];
            for (int i = 0; i < locations.length; i++) {
                this.configLocations[i] = locations[i].trim();
            }
        } else {
            this.configLocations = null;
        }
    }

    @Override
    public void refresh() throws Exception {
        refreshBeanFactory();

    }

    protected GPDefaultListableBeanFactory createBeanFactory(){

        return new GPDefaultListableBeanFactory(this.reader);
    }

    public GPDefaultListableBeanFactory getBeanFactory() {

        return this.beanFactory;
    }

}
