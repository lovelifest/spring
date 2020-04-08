package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author songtao
 * @create 2020-04-2020/4/8-21:59
 */
@Configuration
public class SpringConfig {
    @Bean("rpcProxyClient")
    public RpcProxyClient proxyClient(){
        return new RpcProxyClient();
    }
}
