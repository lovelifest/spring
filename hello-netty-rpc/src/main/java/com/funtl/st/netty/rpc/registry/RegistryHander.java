package com.funtl.st.netty.rpc.registry;

import com.funtl.st.netty.rpc.protocol.InvokerProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author songtao
 * @create 2020-04-2020/4/11-21:35
 */
public class RegistryHander extends ChannelInboundHandlerAdapter {

    private List<String> classNames = new ArrayList<String>();
    private Map<String ,Object> registryMap = new ConcurrentHashMap<String, Object>();





    public RegistryHander() {
        //1、根据要给包名将所有符合的class扫描出来，放到一个容器中
        //如果是分布式的话读取配置文件
        ScannerClass("com.funtl.st.netty.rpc.provider");
        //2、给每一个class取一个唯一名字，作为服务名，保存到一个容器中
        doRegistry();
    }

    private void doRegistry() {
        if(classNames.isEmpty()){
            return;
        }

        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> anInterface = clazz.getInterfaces()[0];
                String serverName = anInterface.getName();
                //dubbo存储的是网络路径，从配置文件中读取
                //在调用的时候再去解析，这里用反射获取
                registryMap.put(serverName,clazz.newInstance());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

    }

    private void ScannerClass(String pageName) {
        URL url = this.getClass().getClassLoader().getResource(pageName.replaceAll("\\.","/"));
        File classFile = new File(url.getFile());
        for (File file : classFile.listFiles()) {
            if(file.isDirectory()){
                ScannerClass(pageName+"."+file.getName());
            }else {
                classNames.add(pageName + "." +file.getName().replace(".class",""));
            }
        }
    }

    //有客户端连接上的时候回调
    //3、当有客户端连接过来之后，获取协议内容InvokerProtocol的对象
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object reslut = new Object();
        InvokerProtocol request = (InvokerProtocol) msg;
        //4、要去注册好的容器中找到符合条件的服务
        if(registryMap.containsKey(request.getClassName())){
            Object service = registryMap.get(request.getClassName());
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParams());
             reslut = method.invoke(service, request.getValues());
        }
        //5、通过远程调用Provider得到返回结果，并回复给客户端
        ctx.write(reslut);
        ctx.flush();
        ctx.close();
    }

    //连接异常的时候回调
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }
}
