package com.funtl.st.hellocurrent.controller;

import java.util.concurrent.TimeUnit;

/**
 * @author songtao
 * @create 2020-03-2020/3/29-0:33
 */
public class ThreadReSetDemo {
    private static  int i;

    public static void main(String[] args) {
        Thread thread =  new Thread(()->{
            while (true){//默认是false
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("before:"+Thread.currentThread().isInterrupted());
                    Thread.interrupted();
                    System.out.println("after:"+Thread.currentThread().isInterrupted());
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
