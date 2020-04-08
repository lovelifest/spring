package org.example.proxy;

/**
 * @author songtao
 * @create 2020-04-2020/4/8-0:20
 */
public class ProxyDemo {
    public static void main(String[] args) {
        IDog iDog = new GunDog();
        IDog proxy = (IDog)MyProxyFactory.getProxy(iDog);
        proxy.run("汪汪汪");
    }
}
