package org.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author songtao
 * @create 2020-04-2020/4/8-21:22
 */
public class StRpcServer implements ApplicationContextAware, InitializingBean {

    ExecutorService executorService= Executors.newCachedThreadPool();
    private int port;
    private Map<String,Object> handlerMap = new HashMap<>();

    public StRpcServer(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(port);
            while(true){//不断接受请求
                Socket socket=serverSocket.accept();//BIO
                //每一个socket 交给一个processorHandler来处理
                executorService.execute(new ProcessorHandler(socket,handlerMap));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcServer.class);
        if(!serviceBeanMap.isEmpty()){
            for (Object serviceBean:serviceBeanMap.values()){
                //拿到注解
                RpcServer rpcServer = serviceBean.getClass().getAnnotation(RpcServer.class);
                String servicename = rpcServer.value().getName();//拿到接口类
                String version = rpcServer.version();//拿到版本号
                if(!StringUtils.isEmpty(version)){
                    servicename+="-"+version;
                }
                handlerMap.put(servicename,serviceBean);
            }
        }
    }
}
