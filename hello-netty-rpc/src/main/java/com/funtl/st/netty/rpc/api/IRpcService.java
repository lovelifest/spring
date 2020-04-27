package com.funtl.st.netty.rpc.api;

/**
 * @author songtao
 * @create 2020-04-2020/4/11-20:59
 */
public interface IRpcService {
    int add(int a,int b);
    int sub(int a,int b);
    int mult(int a,int b);
    int div(int a,int b);
}
