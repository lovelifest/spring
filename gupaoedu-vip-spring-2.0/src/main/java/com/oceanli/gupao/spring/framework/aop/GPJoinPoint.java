package com.oceanli.gupao.spring.framework.aop;

import java.lang.reflect.Method;

public interface GPJoinPoint {

    Method getMethod();
    Object[] getArguments();
    Object getThis();
}
