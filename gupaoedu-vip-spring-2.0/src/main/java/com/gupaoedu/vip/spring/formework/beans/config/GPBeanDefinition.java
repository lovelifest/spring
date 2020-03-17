package com.gupaoedu.vip.spring.formework.beans.config;

import lombok.Data;

/**
 * bean的注册信息（xml、注解）
 */
@Data
public class GPBeanDefinition {

    private String beanClassName;

    private boolean lazyInit = false;

    private String factoryBeanName;
}
