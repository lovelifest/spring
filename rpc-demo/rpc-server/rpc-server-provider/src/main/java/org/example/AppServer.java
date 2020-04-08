package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */

public class AppServer
{
    public static void main( String[] args )
    {
        /*IHelloService helloService=new HelloServiceImpl();

       RpcProxyServer proxyServer=new RpcProxyServer();
       proxyServer.publisher(helloService,8080);*/

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        context.start();
    }
}
