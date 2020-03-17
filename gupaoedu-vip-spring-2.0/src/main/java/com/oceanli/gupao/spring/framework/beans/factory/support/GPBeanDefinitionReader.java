package com.oceanli.gupao.spring.framework.beans.factory.support;

import com.oceanli.gupao.spring.framework.beans.factory.config.GPBeanDefinition;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GPBeanDefinitionReader {

    private Properties config = new Properties();

    private static final String SCAN_PACKAGE = "scanPackage";

    private List<String> registryBeanClasses = new ArrayList<String>();

    public GPBeanDefinitionReader(String... locations) {

        //读取配置文件，转换为文件流 放入properties中
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream(locations[0].replace("classpath:", "")
                        .replace("classpath*:", ""));
        try {
            config.load(inputStream);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //扫描包，获取包下所有类
        doScanner(config.getProperty(SCAN_PACKAGE));

    }

    private void doScanner(String scanPackage) {
        //扫描包，获取包下面所有的class文件，将其注入到registryBeanClasses
        URL url = this.getClass().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classFile = new File(url.getFile());
        for (File f: classFile.listFiles()) {
            if  (f.isDirectory()) {
                doScanner(scanPackage + "." + f.getName());
            }
            if (f.getName().endsWith(".class")) {
                String className = scanPackage + "." + f.getName().replaceAll("\\.class", "");
                registryBeanClasses.add(className);
            }
        }
    }

    public List<GPBeanDefinition> doLoadBeanDefinitions() {
        //将registryBeanClasses的class转换为GPBeanDefinition
        List<GPBeanDefinition> result = new ArrayList<>();
        if (registryBeanClasses.isEmpty()) {
            return result;
        }
        for (String className : registryBeanClasses) {
            try{
                Class<?> clazz = Class.forName(className);
                if (clazz.isInterface()) {
                    continue;
                }
                String simpleName = clazz.getSimpleName();
                GPBeanDefinition gpBeanDefinition = doCreateGPBeanDefinition(toLowerFirstCase(simpleName), clazz.getName());
                result.add(gpBeanDefinition);
                //将类的所有接口也加入到GPBeanDefinition列表中
                Class<?>[] interfaces = clazz.getInterfaces();
                if (interfaces != null) {
                    for (Class<?> i : interfaces) {
                        result.add(doCreateGPBeanDefinition(i.getName(), clazz.getName()));
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private GPBeanDefinition doCreateGPBeanDefinition(String factoryBeanName, String beanClassName) {
        //创建GPBeanDefinition
        GPBeanDefinition gpBeanDefinition = new GPBeanDefinition();
        gpBeanDefinition.setFactoryBeanName(factoryBeanName);
        gpBeanDefinition.setBeanClassName(beanClassName);
        gpBeanDefinition.setSingleton(true);
        return gpBeanDefinition;
    }

    public Properties getConfig() {
        return config;
    }

    public void setConfig(Properties config) {
        this.config = config;
    }
}
