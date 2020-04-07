package com.funtl.st.hellocurrent.blockingqueue;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author songtao
 * @create 2020-04-2020/4/4-21:18
 */
public class AtomicDemo {
    private static  int count ;
    static AtomicInteger atomicInteger = new AtomicInteger(0);

    static void incr(){
            atomicInteger.incrementAndGet();
            count++;//当出现线程切换时候，可能不能够保证原子性

        System.out.println("count:"+ count);
        System.out.println("automicIngeger:"+ atomicInteger);
        try {
            Thread.sleep(1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(AtomicDemo::incr).start();
        }
    }



}
