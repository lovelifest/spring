package com.oceanli.gupao.spring.framework.aop.aspect;

import com.oceanli.gupao.spring.framework.aop.GPJoinPoint;
import com.oceanli.gupao.spring.framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

public class GPMethodBeforeAdviceInterceptor extends GPAbstractMethodAspectJAdvice {

    private GPJoinPoint gpJoinPoint;

    public GPMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(GPMethodInvocation mi) throws Throwable {

        this.gpJoinPoint = mi;
        super.invokeAspectMethod(this.gpJoinPoint, null, null);
        return mi.proceed();
    }
}
