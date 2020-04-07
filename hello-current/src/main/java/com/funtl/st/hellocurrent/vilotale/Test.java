package com.funtl.st.hellocurrent.vilotale;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @author songtao
 * @create 2020-03-2020/3/30-21:19
 */
public class Test {

    private static volatile Boolean stop = false;


    public static void main(String[] args) throws InterruptedException {
        Thread t1 =  new Thread(()->{
           long i = 0;
           while (!stop){
               i++;
           }
        });

        t1.start();
        Thread.sleep(1000);
        stop = true;
    }
}
