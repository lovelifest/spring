package com.funtl.st.hello.redis;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author songtao
 * @create 2020-05-2020/5/11-23:34
 */
public class JedisTest {

    @Test
    public void testJedis(){
        //1.连接jedis
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //2.操作redis
        jedis.set("name", "helloworld");
        System.out.println(jedis.get("name"));
        //3.关闭连接
        jedis.close();
    }

    @Test
    public void testList(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.lpush("list1","a","b","c");
        jedis.rpush("list1", "x");
        List<String> list1 = jedis.lrange("list1", 0, -1);
        for (String s : list1) {
            System.out.println(s);
        }
    }

}
