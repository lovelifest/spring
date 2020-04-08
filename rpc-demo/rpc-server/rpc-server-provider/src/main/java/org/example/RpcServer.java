package org.example;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author songtao
 * @create 2020-04-2020/4/8-21:17
 */
@Target(ElementType.TYPE)//类或者接口
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcServer {
    Class<?> value();
    String version() default "";

}
