package com.funtl.st.hellocurrent.condition;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author songtao
 * @create 2020-04-2020/4/2-22:27
 */
public class ImportDataDemo extends  Thread {
    private CyclicBarrier cyclicBarrier;
    private String path;

    public ImportDataDemo(CyclicBarrier cyclicBarrier, String path) {
        this.cyclicBarrier = cyclicBarrier;
        this.path = path;
    }

    @Override
    public void run() {
        System.out.println("开始导入:"+path+"数据");
        //TODO
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }


    }
}
