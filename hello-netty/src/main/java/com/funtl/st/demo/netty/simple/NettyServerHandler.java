package com.funtl.st.demo.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author songtao
 * @create 2020-04-2020/4/19-22:09
 */

/**
 * 定义一个Handler 需要继承netty规定好的某个HandlerAdapter(规范)
 * 这时我们自定义个Handler,才能称之为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取数据实际（这里我们可以读取客户端发送的消息）
    /*
    1.ChannelHandlerContext ctx:上下文对象，含有管道pipeline,通道
    2.Object msg:客户端发送的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*10);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,客户端2~", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("发生异常");
                }
            }
        });

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*10);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,客户端3~", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("发生异常");
                }
            }
        });

        //用户自定义定时任务 -》该任务是提交到 scheduleTashQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*10);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,客户端3~", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("发生异常");
                }
            }
        },5, TimeUnit.SECONDS);


        System.out.println("go on .......................................");
        /*        System.out.println("服务器读取线程"+Thread.currentThread().getName());
        System.out.println("server ctx=" + ctx);
        System.out.println("看看channel 和 pipeline的关系");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();//本质是一个双向链表
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送来消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址" + ctx.channel().remoteAddress());*/
    }

    //数据读取完毕，发送消息
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush 是read 和 flush
        //将数据写入到缓存，并刷新
        //一般来讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,客户端1~", CharsetUtil.UTF_8));
    }

    //处理异常，一般需要关闭通道


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
