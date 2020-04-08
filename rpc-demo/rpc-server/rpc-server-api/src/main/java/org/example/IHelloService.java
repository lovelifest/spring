package org.example;

import org.example.entity.User;

/**
 * @author songtao
 * @create 2020-04-2020/4/7-21:15
 */
public interface IHelloService {
    String sayHello(String content);

    String saveUser(User user );

}
