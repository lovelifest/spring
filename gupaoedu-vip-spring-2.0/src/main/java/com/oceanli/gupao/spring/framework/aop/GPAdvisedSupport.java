package com.oceanli.gupao.spring.framework.aop;

import com.oceanli.gupao.spring.framework.aop.aspect.GPMethodAfterAdviceInterceptor;
import com.oceanli.gupao.spring.framework.aop.aspect.GPMethodAfterThrowingAdviceInterceptor;
import com.oceanli.gupao.spring.framework.aop.aspect.GPMethodAroundAdviceInterceptor;
import com.oceanli.gupao.spring.framework.aop.aspect.GPMethodBeforeAdviceInterceptor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GPAdvisedSupport {

    private Class<?> targetClass;
    private Object target;
    private Pattern pointCutClassPattern;
    private GPAopConfig config;
    private transient Map<Method, List<Object>> methodCache = new HashMap<Method, List<Object>>();

    public GPAdvisedSupport(GPAopConfig config) {
        this.config = config;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Pattern getPointCutClassPattern() {
        return pointCutClassPattern;
    }

    public void setPointCutClassPattern(Pattern pointCutClassPattern) {
        this.pointCutClassPattern = pointCutClassPattern;
    }

    public GPAopConfig getConfig() {
        return config;
    }

    public void setConfig(GPAopConfig config) {
        this.config = config;
    }

    public Map<Method, List<Object>> getMethodCache() {
        return methodCache;
    }

    public void setMethodCache(Map<Method, List<Object>> methodCache) {
        this.methodCache = methodCache;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception {

        List<Object> cached = this.methodCache.get(method);
        // 缓存未命中，则进行下一步处理
        if (cached == null) {
            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            cached = this.methodCache.get(m);
            this.methodCache.put(m, cached);
        }
        return cached;

    }

    public boolean pointCutMatch() {

        return pointCutClassPattern.matcher(targetClass.toString()).matches();
    }

    private void parse() {

        try {

            //pointcut表达式
            String pointCut = config.getPointCut().replaceAll("\\.", "\\\\.")
                    .replaceAll("\\\\.\\*",".*")
                    .replaceAll("\\(","\\\\(")
                    .replaceAll("\\)","\\\\)");
            Pattern pattern = Pattern.compile(pointCut);

            String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(")-4);
            pointCutClassPattern = Pattern.compile("class " + pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ") +1));

            methodCache = new HashMap<Method, List<Object>>();

            String aspectClass = config.getAspectClass();
            Class<?> aspectTarget = Class.forName(aspectClass);
            //切面类的方法Map，key为方法名称, value为方法
            Map<String, Method> aspectMethodMap = new HashMap<>();
            Method[] methods = aspectTarget.getMethods();
            for (Method m : methods) {
                aspectMethodMap.put(m.getName(), m);
            }

            for (Method method : this.targetClass.getMethods()) {
                String methodName = method.toString();
                if (methodName.contains("throws")) {
                    methodName = methodName.substring(0, methodName.lastIndexOf("throws")).trim();
                }
                Matcher matcher = pattern.matcher(methodName);
                if (matcher.matches()) {
                    //能满足切面规则的类，添加的 AOP 配置中
                    List<Object> advices = new LinkedList<Object>();
                    if (config.getAspectBefore()!= null && !"".equals(config.getAspectBefore().trim())) {
                        advices.add(new GPMethodBeforeAdviceInterceptor(aspectMethodMap.get(config.getAspectBefore()), aspectTarget.newInstance()));
                    }
                    if (config.getAspectAfter()!= null && !"".equals(config.getAspectAfter().trim())) {
                        advices.add(new GPMethodAfterAdviceInterceptor(aspectMethodMap.get(config.getAspectAfter()), aspectTarget.newInstance()));
                    }
                    if (config.getAspectAfterThrow()!= null && !"".equals(config.getAspectAfterThrow().trim())) {

                        GPMethodAfterThrowingAdviceInterceptor throwingAdviceInterceptor =
                                new GPMethodAfterThrowingAdviceInterceptor(aspectMethodMap.get(config.getAspectAfterThrow()), aspectTarget.newInstance());
                        throwingAdviceInterceptor.setThrowingName(config.getAspectAfterThrowingName());
                        advices.add(throwingAdviceInterceptor);
                    }
                    if (config.getAspectAround() != null && !"".equals(config.getAspectAround())) {
                        advices.add(new GPMethodAroundAdviceInterceptor(aspectMethodMap.get(config.getAspectAround()), aspectTarget.newInstance()));
                    }
                    methodCache.put(method, advices);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
