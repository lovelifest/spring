package com.funtl.st.hellocurrent.reentrantlock;

/**
 * @author songtao
 * @create 2020-03-2020/3/30-23:28
 */
public class ReenTrantLockDemo {

    public synchronized void method1(){//获取对象锁
        System.out.println("method1");
        method2();
    }

    private void method2() {
        synchronized (this){//不再次获取对象锁，而是增加重入次数
            System.out.println("method2");
        }

    }


    public static void main(String[] args) {
        ReenTrantLockDemo reenTrantLockDemo = new ReenTrantLockDemo();
        reenTrantLockDemo.method1();
    }
}
