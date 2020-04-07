package com.funtl.st.hellocurrent.controller;

import java.util.concurrent.TimeUnit;

/**
 * @author songtao
 * @create 2020-03-2020/3/29-0:33
 */
public class ThreadExceptionDemo {
    private static  int i;

    public static void main(String[] args) {
        Thread thread =  new Thread(()->{
            while (true){//默认是false
                if(Thread.currentThread().isInterrupted()){
                    try {
                        TimeUnit.SECONDS.sleep(1);//中断一个处于阻塞状态的线程， join/wait/queue.take...都会抛出异常，会复位_interrupted state为false
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(1);
            thread.interrupt();//把isinterrupted设置为true
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
