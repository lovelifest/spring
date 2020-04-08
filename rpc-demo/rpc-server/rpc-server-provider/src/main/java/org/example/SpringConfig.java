package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author songtao
 * @create 2020-04-2020/4/8-21:23
 */
@Configuration
@ComponentScan(basePackages = "org.example")
public class SpringConfig {

    @Bean(name = "stRpcServer")
    public StRpcServer stRpcServer(){
        return  new StRpcServer(8080);
    }


}
