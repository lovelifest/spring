package com.funtl.st.hellodistributed.socket;

import com.alibaba.fastjson.JSON;

/**
 * @author songtao
 * @create 2020-04-2020/4/6-22:03
 */
public class JsonSerializer implements Iserializer {
    @Override
    public <T> byte[] serialize(T obj) {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return (T) JSON.parseObject(new String(data),clazz);
    }
}
