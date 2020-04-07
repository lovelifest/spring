package com.funtl.st.hellocurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author songtao
 * @create 2020-04-2020/4/4-20:45
 */
public class BlockingDemo {
    ArrayBlockingQueue queue = new ArrayBlockingQueue(10);

    public BlockingDemo() {
        init();
    }

    public void init(){
        new Thread(()->{
            while (true){
                try {
                    Object message = queue.take();
                    System.out.println("receive data:"+message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void  addData(String message) throws InterruptedException {
        queue.add(message);
        System.out.println("send message:"+message);
        Thread.sleep(1000);
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingDemo blockingDemo = new BlockingDemo();
        for (int i = 0; i < 1000; i++) {
            blockingDemo.addData("message"+i);
        }
    }



}
