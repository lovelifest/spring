package com.funtl.st.hellocurrent.controller;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author songtao
 * @create 2020-03-2020/3/28-22:11
 */
public class SaveProcessor extends Thread implements  IRequestProcessor {
    LinkedBlockingDeque<Request> requests = new LinkedBlockingDeque<>();
    private IRequestProcessor nextProcessor;
    private volatile Boolean isFinish=false;


    public SaveProcessor(IRequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public SaveProcessor() {
    }

    private  void  shutdown(){//对外提供关闭的方法
        this.isFinish = true;
    }

    @Override
    public void process(Request request) {
        //TODO 根据实际需求去做一些操作
        requests.add(request);
    }

    @Override
    public void run() {
        while (!isFinish){
            try {
                Request request = requests.take();
                //处理业务
                System.out.println(" SaveProcessor: "+request);
                //交给下一个责任链
                if(nextProcessor != null){
                    nextProcessor.process(request);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
