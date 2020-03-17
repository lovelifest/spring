package com.gupaoedu.vip.spring.formework.beans.support;

import com.gupaoedu.vip.spring.formework.beans.config.GPBeanDefinition;
import com.sun.xml.internal.ws.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Bean的配置信息加载器
 */
public class GPBeanDefinitionReader {

    private Properties config = new Properties();

    private final static String SCAN_PACKAGE = "scanPackage";

    private List<String> registerBeanClasses = new ArrayList<>();

    public GPBeanDefinitionReader(String... locations) {
        //加载配置信息为properties
        doLoadConfiguration(locations[0]);
        //根据配置信息（扫描路径），获取类名
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    /**
     * 扫描class文件，获取文件名字
     * @param scanPackage 类的扫描路径
     */
    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource(scanPackage.replaceAll("\\.", "/"));
        assert url != null;
        File classPath = new File(url.getFile());
        Arrays.stream(Objects.requireNonNull(classPath.listFiles()))
                .forEach(file -> {
            if(file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            }else {
                if(file.getName().endsWith(".class")) {
                    String className = (scanPackage + "." + file.getName()).replace(".class", "");
                    this.registerBeanClasses.add(className);
                }
            }
        });

    }

    /**
     * 加载配置信息
     */
    private void doLoadConfiguration(String location1) {
        String location = location1.replace("classpath","");
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(location);
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 加载bean的配置信息,方便IOC容器初始化Bean
     * @return bean的配置信息
     */
    public List<GPBeanDefinition> loadBeanDefinitions() {
        List<GPBeanDefinition> definitions = new ArrayList<>();
        for(String beanName : registerBeanClasses) {
            GPBeanDefinition definition = doCreateBeanDefinition(beanName);
            if(definition == null) {continue;}
            definitions.add(definition);
        }
        return definitions;
    }

    /**
     * 创建bean的配置信息
     */
    private GPBeanDefinition doCreateBeanDefinition(String beanName) {
        try {
            Class<?> beanClass = Class.forName(beanName);
            if(beanClass.isInterface()) {return null;}
            GPBeanDefinition definition = new GPBeanDefinition();
            definition.setBeanClassName(beanName);
            definition.setFactoryBeanName(StringUtils.decapitalize(beanClass.getSimpleName()));

            return definition;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
