package com.funtl.st.hellocurrent.condition;

import java.util.concurrent.CyclicBarrier;

/**
 * @author songtao
 * @create 2020-04-2020/4/2-22:23
 */
public class CycliBarrierDemo extends Thread {
    //循环屏障
    //可以使用一组线程达到一个同步点之前阻塞
    @Override
    public void run() {
        System.out.println("开始分析数据");
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new CycliBarrierDemo());
        new  Thread(new ImportDataDemo(cyclicBarrier,"path1")).start();
        new  Thread(new ImportDataDemo(cyclicBarrier,"path2")).start();
        new  Thread(new ImportDataDemo(cyclicBarrier,"path3")).start();
    }
}
