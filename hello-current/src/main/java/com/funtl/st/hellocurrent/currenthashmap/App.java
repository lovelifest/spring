package com.funtl.st.hellocurrent.currenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author songtao
 * @create 2020-04-2020/4/2-22:42
 */
public class App {
    private static ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            map.put(i,i);
        }

        map.size();

    }
}
