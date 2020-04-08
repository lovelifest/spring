package org.example.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author songtao
 * @create 2020-04-2020/4/7-21:50
 */
@Data
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 7386949541169640114L;
    private String className;
    private String methodName;
    private Object[] parameters;
    private String version;
}
