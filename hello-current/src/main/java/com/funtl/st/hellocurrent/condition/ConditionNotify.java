package com.funtl.st.hellocurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author songtao
 * @create 2020-04-2020/4/1-21:21
 */
public class ConditionNotify implements  Runnable {
    private Lock lock;
    private Condition condition;

    public ConditionNotify(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println("Condition notify start");
            condition.signal();//唤醒阻塞的线程
            System.out.println("Condition notify end");
        }finally {
            lock.unlock();
        }

    }
}
