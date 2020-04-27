package com.funtl.st.netty.rpc.registry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author songtao
 * @create 2020-04-2020/4/11-21:08
 */
public class RpcRegistry {
    private int port;

    public RpcRegistry(int port) {
        this.port = port;
    }

    public void  start() throws InterruptedException {
        //ServerSocket/ServerSocketChannel
        //基于NIO来实现的
        //Selector 主线程，Work线程

        //初始化主线程池，selector
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //子线程池初始化，具体对应客户端的处理逻辑
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();
        server.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    //在netty中，把所有的业务处理归总到一个队列中
                    //这个队列包含了各种各样的处理逻辑，对这些处理逻辑在netty中有一个封装
                    //封装成一个对象，无锁化串行对列
                    //pipline
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //就是对我们的处理逻辑的封装
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //这两个是还原出InvokerProtocol对象，完整的接受数据
                        //对自定义协议的内容要进行编、解码
                        //解码
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                        //自定义编码
                        pipeline.addLast(new LengthFieldPrepender(4));
                        //反序列化
                        //实参处理
                        pipeline.addLast("encoder",new ObjectEncoder());
                        pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        //前面的编解码是对数据的解析
                        //最后一步，执行属于自己的逻辑
                        //1、注册，给每一个对象取一个名字，对外提供的服务名
                        //2、服务的位置要做一个登记
                        pipeline.addLast(new RegistryHander());

                    }
                }).option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true);
        ChannelFuture future = server.bind(this.port).sync();
        System.out.println("RPC Registry start listen at:"+this.port);
        future.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        new RpcRegistry(8080).start();
    }

}
