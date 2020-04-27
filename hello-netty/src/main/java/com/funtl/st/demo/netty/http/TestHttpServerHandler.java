package com.funtl.st.demo.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author songtao
 * @create 2020-04-2020/4/20-22:27
 */

/**
 * 1.SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 的子类
 * 2。HttpObject 表示客户端和服务端互相通讯的数据被封装成HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    //channelRead0 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断 msg 是不是 HttpRequest
        if (msg instanceof HttpRequest) {
            System.out.println("pipeline hashcode"+ctx.pipeline().hashCode());
            System.out.println("TestHttpServerHandler hash="+this.hashCode());
            System.out.println("msg 类型=" + msg.getClass());
            System.out.println(" 客户端地址 " + ctx.channel().remoteAddress());

            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请 求 了 /favicon.ico,不做响应");
                return;
            }

            //回复信息给浏览器【http协议】
            ByteBuf content = Unpooled.copiedBuffer("hello,我是服务端", CharsetUtil.UTF_16);

            //构造一个http的响应，即httpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            //将构建好的response返回
            ctx.writeAndFlush(response);
        }
    }
}
