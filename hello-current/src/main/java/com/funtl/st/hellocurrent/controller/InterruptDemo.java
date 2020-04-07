package com.funtl.st.hellocurrent.controller;

import java.util.concurrent.TimeUnit;

/**
 * @author songtao
 * @create 2020-03-2020/3/29-0:33
 */
public class InterruptDemo {
    private static  int i;

    public static void main(String[] args) {
       Thread thread =  new Thread(()->{
            while (!Thread.currentThread().isInterrupted()){//默认是false
                i++;
            }
            System.out.println("i:"+i);

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
