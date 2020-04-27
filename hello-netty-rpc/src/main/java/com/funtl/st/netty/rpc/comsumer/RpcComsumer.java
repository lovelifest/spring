package com.funtl.st.netty.rpc.comsumer;

import com.funtl.st.netty.rpc.api.IRpcHelloService;
import com.funtl.st.netty.rpc.api.IRpcService;
import com.funtl.st.netty.rpc.comsumer.proxy.RpcProxy;
import com.funtl.st.netty.rpc.provider.RpcHelloServiceImpl;
import com.funtl.st.netty.rpc.provider.RpcServiceImpl;

/**
 * @author songtao
 * @create 2020-04-2020/4/11-22:15
 */
public class RpcComsumer {
    public static void main(String[] args) {
        IRpcHelloService iRpcHelloService = new RpcProxy().create(IRpcHelloService.class);
        System.out.println(iRpcHelloService.hello("tom"));


        IRpcService service = new RpcProxy().create(IRpcService.class);
        System.out.println(service.add(8, 2));
        System.out.println(service.sub(8, 2));
        System.out.println(service.mult(8, 2));
        System.out.println(service.div(8, 2));
    }
}
