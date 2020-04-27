package com.funtl.st.netty.rpc.provider;

import com.funtl.st.netty.rpc.api.IRpcService;

/**
 * @author songtao
 * @create 2020-04-2020/4/11-21:05
 */
public class RpcServiceImpl implements IRpcService {
    public int add(int a, int b) {
        return a+b;
    }

    public int sub(int a, int b) {
        return a-b;
    }

    public int mult(int a, int b) {
        return a * b;
    }

    public int div(int a, int b) {
        return a / b;
    }
}
