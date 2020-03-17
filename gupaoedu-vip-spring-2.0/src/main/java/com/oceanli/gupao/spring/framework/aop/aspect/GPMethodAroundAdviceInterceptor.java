package com.oceanli.gupao.spring.framework.aop.aspect;

import com.oceanli.gupao.spring.framework.aop.GPProceedingJoinPoint;
import com.oceanli.gupao.spring.framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

public class GPMethodAroundAdviceInterceptor extends GPAbstractMethodAspectJAdvice {

    private GPProceedingJoinPoint gpProceedingJoinPoint;

    public GPMethodAroundAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(GPMethodInvocation mi) throws Throwable {

        Object result = null;
        Class<?>[] parameterTypes = super.getAspectMethod().getParameterTypes();
        if (parameterTypes == null || parameterTypes.length == 0) {
            result = super.getAspectMethod().invoke(super.getAspectTarget());
        } else {
            gpProceedingJoinPoint = new GPProceedingJoinPoint();
            gpProceedingJoinPoint.setMethodInvocation(mi);
            Object[] args = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i ++) {
                if(parameterTypes[i] == GPProceedingJoinPoint.class) {
                    args[i] = gpProceedingJoinPoint;
                }
            }
            result = super.getAspectMethod().invoke(super.getAspectTarget(), args);
        }
        return result;
    }
}
