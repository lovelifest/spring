package com.oceanli.gupao.spring.framework.aop.aspect;

import com.oceanli.gupao.spring.framework.aop.GPJoinPoint;
import com.oceanli.gupao.spring.framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

public class GPMethodAfterAdviceInterceptor extends GPAbstractMethodAspectJAdvice {

    private GPJoinPoint gpJoinPoint;

    public GPMethodAfterAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(GPMethodInvocation mi) throws Throwable {

        this.gpJoinPoint = mi;
        Object proceed = mi.proceed();
        super.invokeAspectMethod(this.gpJoinPoint, proceed, null);
        return proceed;
    }
}
