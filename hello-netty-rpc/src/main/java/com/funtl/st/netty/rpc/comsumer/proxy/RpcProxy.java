package com.funtl.st.netty.rpc.comsumer.proxy;

import com.funtl.st.netty.rpc.protocol.InvokerProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.val;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author songtao
 * @create 2020-04-2020/4/11-22:19
 */
public class RpcProxy {
    public static<T> T create(Class<?> clazz){
        MethodProxy methodProxy = new MethodProxy(clazz);
        val result = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, methodProxy);
        return (T) result;
    }

    //将本地调用通过代理的形式换成网络调用
    private static class MethodProxy implements InvocationHandler{
        private Class<?> clazz;

        public MethodProxy(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //判断是不是接口
            //不是接口
            if(Object.class.equals(method.getDeclaringClass())){
                return method.invoke(proxy,args);
            //是接口
            }else {
                return rpcInvoker(proxy,method,args);
            }

        }

        private Object rpcInvoker(Object proxy, Method method, Object[] args) throws InterruptedException {
            InvokerProtocol msg = new InvokerProtocol();
            msg.setClassName(this.clazz.getName());
            msg.setMethodName(method.getName());
            msg.setParams(method.getParameterTypes());
            msg.setValues(args);

            final RpcProxyHandler proxyHandler = new RpcProxyHandler();

            //发起网络请求
            EventLoopGroup wokerGroup = new NioEventLoopGroup();
            Bootstrap client = new Bootstrap();
            client.group(wokerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
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
                            pipeline.addLast(proxyHandler);
                        }
                    });
            ChannelFuture future = client.connect("localhost", 8080).sync();
            future.channel().writeAndFlush(msg).sync();
            future.channel().closeFuture().sync();
            wokerGroup.shutdownGracefully();
            return proxyHandler.getResponse();
        }
    }
}
