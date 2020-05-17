package com.funtl.st.hello.redis.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * @author songtao
 * @create 2020-05-2020/5/11-23:58
 */
public class JedisUtils {
    private static JedisPool jp = null;
    private static Integer port = null;
    private static Integer maxTotal = null;
    private static Integer maxIdle = null;
    private static String host = null;
    static {
        ResourceBundle rb = ResourceBundle.getBundle("redis");
         port = Integer.parseInt(rb.getString("redis.port"));
         maxTotal = Integer.parseInt(rb.getString("redis.maxTotal"));
         maxIdle = Integer.parseInt(rb.getString("redis.maxIdle"));
         host = rb.getString("redis.host");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port);
        jp = jedisPool;
    }



    public static void main(String[] args) {
        Jedis jedis = jp.getResource();
        String name = jedis.get("name");
        System.out.println(name);
    }
}
