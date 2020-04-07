package com.funtl.st.hellocurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author songtao
 * @create 2020-04-2020/4/1-21:25
 */
public class ConditionTest {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(new ConditionWait(lock,condition)).start();
        new Thread(new ConditionNotify(lock,condition)).start();

    }


}
