package com.funtl.st.hellocurrent.reentrantlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author songtao
 * @create 2020-03-2020/3/30-23:28
 */
public class RwLockDemo {
    public static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    static Map<String,String> map = new HashMap<>();
    static Lock read = lock.readLock();
    static Lock write = lock.writeLock();

    public static final Object get(String key){
        System.out.println("begin read data:"+key);
        read.lock();

        String s;
        try {
            return s = map.get(key);
        } finally {
            read.unlock();
        }
    }

    public static  void  write(String key,String value){
        write.lock();
        try {
            map.put(key,value);
        } finally {
            write.unlock();
        }
    }

    public static void main(String[] args) {
        lock.readLock();

        lock.writeLock();
        //读→读式可以共享的
        //读→写  互斥
        //写→到写  互斥
        //适合写少读多的场景
    }
}
