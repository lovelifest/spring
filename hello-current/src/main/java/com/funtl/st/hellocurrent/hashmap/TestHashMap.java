package com.funtl.st.hellocurrent.hashmap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author songtao
 * @create 2020-05-2020/5/12-21:46
 */
public class TestHashMap {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("1","2");
        String put = map.put("1", "3");
        System.out.println(put);
        System.out.println(map.get("1"));
    }
}
