package com.funtl.st.hellocurrent.condition;

import java.util.concurrent.CountDownLatch;

/**
 * @author songtao
 * @create 2020-04-2020/4/2-21:48
 */
public class CountDownLatchDemo extends Thread {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    @Override
    public void run() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO
        System.out.println("threadName:" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new CountDownLatchDemo().start();
        }
        countDownLatch.countDown();
    }

    /*public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(()->{
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName());
        }).start();
        new Thread(()->{
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName());
        }).start();
        new Thread(()->{
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName());
        }).start();
            countDownLatch.await();//阻塞，到countDownLatch为0的时候释放
    }*/

}
