package com.funtl.st.hellocurrent.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author songtao
 * @create 2020-03-2020/3/30-23:28
 */
public class ReenTrantLockDemo2 {
    public static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();//获得一个锁
        //.......
        lock.unlock();//释放锁
    }
}
