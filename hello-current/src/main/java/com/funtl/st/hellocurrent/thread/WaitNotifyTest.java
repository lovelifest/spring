package com.funtl.st.hellocurrent.thread;

/**
 * @author songtao
 * @create 2020-03-2020/3/29-23:08
 */
public class WaitNotifyTest {
    public static void main(String[] args) {
        Object lock = new Object();
        Thread threadA = new Thread(new ThreadA(lock));
        Thread threadB = new Thread(new ThreadB(lock));
        threadA.start();
        threadB.start();

    }

}
