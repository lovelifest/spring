package com.funtl.st.hellodistributed.socket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author songtao
 * @create 2020-04-2020/4/6-21:47
 */
public class XSerializable implements  Iserializer {
    XStream xStream = new XStream(new DomDriver());
    @Override
    public <T> byte[] serialize(T obj) {

        return xStream.toXML(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return (T)xStream.fromXML(new String(data));
    }
}
