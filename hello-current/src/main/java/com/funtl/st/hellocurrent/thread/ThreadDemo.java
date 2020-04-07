package com.funtl.st.hellocurrent.thread;

/**
 * @author songtao
 * @create 2020-03-2020/3/29-21:41
 */
public class ThreadDemo {

    //两种代码表现形式（方法层面，还是代码块层面）
    //两种作用范围(对象锁,还是类锁)  区别是是否跨对象线程保护

    public synchronized void demo(){//对象锁

    }

    public  void  demo2(){

        synchronized (this){//this这个对象的生命周期

        }
    }

    public void  demo4(){
        synchronized (ThreadDemo.class){//jvm这个类的生命周期，比this的生命周期大

        }
    }

    public static  synchronized  void  demo3(){

    }

    public static void main(String[] args) {

    }
}
