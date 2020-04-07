package com.funtl.st.hellocurrent.vilotale;

/**
 * @author songtao
 * @create 2020-03-2020/3/30-23:00
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 =  new Thread(()->{
            System.out.println("Thread 1");
        });
        Thread t2 =  new Thread(()->{
            System.out.println("Thread 2");
        });
        Thread t3 =  new Thread(()->{
            System.out.println("Thread 3");
        });

        t1.start();
        t1.join();//阻塞主线程，等到t1线程的执行结果，等待t1线程释放
        t2.start();
        t2.join();//建立一个happens-before规则
        t3.start();
        t3.join();
    }

}
