package com.funtl.st.hellocurrent.thread;

/**
 * @author songtao
 * @create 2020-03-2020/3/29-23:06
 */
public class ThreadA extends Thread {
    private Object lock;

    public ThreadA(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.println("start ThreadA");
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end ThreadA");
        }
    }
}
