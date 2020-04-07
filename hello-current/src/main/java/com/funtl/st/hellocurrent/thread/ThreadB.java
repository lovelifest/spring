package com.funtl.st.hellocurrent.thread;

/**
 * @author songtao
 * @create 2020-03-2020/3/29-23:06
 */
public class ThreadB extends Thread {
    private Object lock;

    public ThreadB(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.println("start ThreadB");
            try {
                lock.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("end ThreadB");
        }
    }
}
