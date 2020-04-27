package com.funtl.st.demo.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author songtao
 * @create 2020-04-2020/4/20-22:28
 */
public class TestServerInitiallizer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个netty 提供的httpServerCodec codec =>[coder->decoder]
        //HttpServerCodec
        //1.HttpServerCodec 是netty 提供的处理http的编码解码器
        pipeline.addLast("myHttpServerCodec", new HttpServerCodec());
        //2.增加一个自定义的handler
        pipeline.addLast("myTestHttpServerHandler", new TestHttpServerHandler());
        System.out.println("成功~~~~");
    }
}
