package com.funtl.st.netty.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author songtao
 * @create 2020-04-2020/4/11-21:01
 */
@Data
public class InvokerProtocol implements Serializable {
    private String className;//服务名
    private String methodName;//方法名，具体的逻辑
    private Class<?> [] params;//形参列表
    private Object[] values;//实参列表
}
