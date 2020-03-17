package com.oceanli.gupao.spring.demo.aspect;

import com.oceanli.gupao.spring.framework.aop.GPJoinPoint;
import com.oceanli.gupao.spring.framework.aop.GPProceedingJoinPoint;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class LogAspect {

    //在调用一个方法之前，执行 before 方法
    public void before(GPJoinPoint joinPoint){
    //这个方法中的逻辑，是由我们自己写的
        log.info("Invoker Before Method!!!" +
                "\nTargetObject:" + joinPoint.getThis() +
                "\nArgs:" + Arrays.toString(joinPoint.getArguments()));
    }
    //在调用一个方法之后，执行 after 方法
    public void after(GPJoinPoint joinPoint){
        log.info("Invoker After Method!!!" +
                "\nTargetObject:" + joinPoint.getThis() +
                "\nArgs:" + Arrays.toString(joinPoint.getArguments()));
    }

    public void afterThrowing(GPJoinPoint joinPoint, Throwable ex){
        log.info("出现异常" +
                "\nTargetObject:" + joinPoint.getThis() +
                "\nArgs:" + Arrays.toString(joinPoint.getArguments()) +
                "\nThrows:" + ex.getMessage());
    }

    public Object around(GPProceedingJoinPoint gpProceedingJoinPoint) throws Throwable{
        log.info("环绕通知前置 " +
                "\nTargetObject:" + gpProceedingJoinPoint.getThis() +
                "\nArgs:" + Arrays.toString(gpProceedingJoinPoint.getArguments()));
        Object proceed = gpProceedingJoinPoint.proceed();
        log.info("环绕通知后置 " + "\nresult:" + proceed);
        return proceed;
    }

}
