package org.example;

import org.example.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class AppClient
{
    public static void main( String[] args )
    {
        /*RpcProxyClient rpcProxyClient=new RpcProxyClient();

        IHelloService iHelloService=rpcProxyClient.clientProxy
                (IHelloService.class,"localhost",8080);

        String result=iHelloService.sayHello("Mic");
        System.out.println(result);*/

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient rpcProxyClient = context.getBean(RpcProxyClient.class);
        IHelloService iHelloService=rpcProxyClient.clientProxy
                (IHelloService.class,"localhost",8080);

        String result=iHelloService.sayHello("Mic");
        System.out.println(result);
    }
}
