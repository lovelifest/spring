package com.oceanli.gupao.spring.framework.aop.aspect;

import com.oceanli.gupao.spring.framework.aop.GPAdvise;
import com.oceanli.gupao.spring.framework.aop.GPJoinPoint;
import com.oceanli.gupao.spring.framework.aop.intercept.GPMethodInterceptor;
import com.oceanli.gupao.spring.framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

public abstract class GPAbstractMethodAspectJAdvice implements GPAdvise, GPMethodInterceptor {

    private Method aspectMethod;
    private Object aspectTarget;

    public GPAbstractMethodAspectJAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    public Method getAspectMethod() {
        return aspectMethod;
    }

    public void setAspectMethod(Method aspectMethod) {
        this.aspectMethod = aspectMethod;
    }

    public Object getAspectTarget() {
        return aspectTarget;
    }

    public void setAspectTarget(Object aspectTarget) {
        this.aspectTarget = aspectTarget;
    }

    protected void invokeAspectMethod(GPJoinPoint gpJoinPoint, Object returnValue, Throwable ex) throws Throwable{

        Class<?>[] parameterTypes = this.aspectMethod.getParameterTypes();
        if (parameterTypes == null || parameterTypes.length == 0) {
            this.aspectMethod.invoke(aspectTarget);
        } else {
            Object[] args = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i ++) {
                if(parameterTypes[i] == GPJoinPoint.class) {
                    args[i] = gpJoinPoint;
                }else if(parameterTypes[i] == Throwable.class){
                    args[i] = ex;
                }else if(parameterTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }
            this.aspectMethod.invoke(this.aspectTarget, args);
        }
    }
}
