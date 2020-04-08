package org.example;

import org.example.entity.User;

/**
 * @author songtao
 * @create 2020-04-2020/4/7-21:19
 */
@RpcServer(value = IHelloService.class,version = "v1.0")
public class HelloServiceImpl implements  IHelloService {


    @Override
    public String sayHello(String content) {
        System.out.println("v1.0 request in sayhello:"+content);
        return "v1.0 say hello:"+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("v1.0 request save user:"+user);
        return "v1.0 SUCCESS";
    }
}
