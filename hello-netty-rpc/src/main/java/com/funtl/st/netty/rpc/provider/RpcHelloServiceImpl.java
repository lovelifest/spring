package com.funtl.st.netty.rpc.provider;

import com.funtl.st.netty.rpc.api.IRpcHelloService;

/**
 * @author songtao
 * @create 2020-04-2020/4/11-21:05
 */
public class RpcHelloServiceImpl implements IRpcHelloService {
    public String hello(String name) {
        return "你好："+name;
    }
}
