package com.oceanli.gupao.spring.framework.aop.aspect;

import com.oceanli.gupao.spring.framework.aop.GPJoinPoint;
import com.oceanli.gupao.spring.framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

public class GPMethodAfterThrowingAdviceInterceptor extends GPAbstractMethodAspectJAdvice {

    private String throwName;

    private GPJoinPoint gpJoinPoint;

    public GPMethodAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    void afterThrowing(Method m, Object target, Object[] args) throws Throwable {
        super.invokeAspectMethod(gpJoinPoint, null, null);
    }

    @Override
    public Object invoke(GPMethodInvocation mi) throws Throwable {

        this.gpJoinPoint = mi;
        Object proceed = null;
        try {
            proceed = mi.proceed();
        } catch (Throwable throwable) {
            invokeAspectMethod(mi, null, throwable.getCause());
            throwable.printStackTrace();
        }
        return proceed;
    }
    public void setThrowingName(String throwingName) {
        this.throwName = throwingName;
    }
}
