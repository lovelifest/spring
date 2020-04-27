package com.funtl.st.netty.rpc.comsumer.proxy;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author songtao
 * @create 2020-04-2020/4/11-22:46
 */
public class RpcProxyHandler extends ChannelInboundHandlerAdapter {
    private  Object response;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       response = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client is exception");
    }

    public Object getResponse() {
        return  response;
    }
}
