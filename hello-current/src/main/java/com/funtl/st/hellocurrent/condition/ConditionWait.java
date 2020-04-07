package com.funtl.st.hellocurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author songtao
 * @create 2020-04-2020/4/1-21:21
 */
public class ConditionWait implements  Runnable {
    private Lock lock;
    private Condition condition;

    public ConditionWait(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        try {
            lock.lock();//竞争锁
            try {
                System.out.println("Condition wait start");
                condition.await();//阻塞线程
                System.out.println("Condition wait end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            lock.unlock();//释放锁
        }

    }
}
