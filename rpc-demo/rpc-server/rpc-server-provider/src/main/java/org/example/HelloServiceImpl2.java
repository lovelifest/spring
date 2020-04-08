package org.example;

import org.example.entity.User;

/**
 * @author songtao
 * @create 2020-04-2020/4/7-21:19
 */
@RpcServer(value = IHelloService.class,version = "v2.0")
public class HelloServiceImpl2 implements  IHelloService {


    @Override
    public String sayHello(String content) {
        System.out.println("v2.0 request in sayhello:"+content);
        return "v2.0 say hello:"+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("v2.0 request save user:"+user);
        return "v2.0 SUCCESS";
    }
}
