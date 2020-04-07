package com.funtl.st.hellodistributed.socket;

/**
 * @author songtao
 * @create 2020-04-2020/4/6-21:07
 */
public interface Iserializer {

    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] data,Class<T> clazz);
}
