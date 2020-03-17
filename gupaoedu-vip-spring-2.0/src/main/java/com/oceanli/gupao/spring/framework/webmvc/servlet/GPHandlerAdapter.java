package com.oceanli.gupao.spring.framework.webmvc.servlet;

import com.oceanli.gupao.spring.framework.annotation.GPRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GPHandlerAdapter {


    public GPModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) {

        //页面传的参数列表
        Map<String, String[]> parameterMap = req.getParameterMap();

        GPHandlerMapping handlerMapping = (GPHandlerMapping)handler;

        Method method = handlerMapping.getMethod();

        //每一个方法有一个参数列表，那么这里保存的是形参列表
        Map<String,Integer> paramMapping = new HashMap<String, Integer>();

        //取出方法中的形参列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        //实参数组
        Object[] paramValues = new Object[parameterTypes.length];

        //方法的形参上的注解二维数组，因为有多个参数，每个参数都可能有注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] pa = parameterAnnotations[i];
            for (int j = 0; j < pa.length; j++) {
                if (pa[j] instanceof GPRequestParam) {
                    GPRequestParam requestParam = (GPRequestParam) pa[j];
                    //形参上配置的GPRequestParam注解的参数，查询它的参数位置
                    paramMapping.put(requestParam.value(), i);
                }
            }
        }
        for (int i=0; i<parameterTypes.length; i++) {
            Class<?> parameterTypeClass = parameterTypes[i];
            //查询形参上的HttpServletRequest、HttpServletResponse的位置
            if (parameterTypeClass == HttpServletRequest.class
            || parameterTypeClass == HttpServletResponse.class) {
                paramMapping.put(parameterTypeClass.getName(), i);
            }
        }
        for(Map.Entry<String, Integer> entry : paramMapping.entrySet()) {
            String paramName = entry.getKey();
            Integer paramIndex = entry.getValue();
            if (paramName == HttpServletRequest.class.getName()) {
                paramValues[paramIndex] = req;
                continue;
            }
            if (paramName == HttpServletResponse.class.getName()) {
                paramValues[paramIndex] = resp;
                continue;
            }
            String[] paramValue = parameterMap.get(paramName);
            if (paramValue != null) {
                String value = Arrays.toString(paramValue).replaceAll("\\[|\\]", "").replaceAll("\\s", "");
                //因为页面上传过来的值都是 String 类型的，而在方法中定义的类型是千变万化的
                //要针对我们传过来的参数进行类型转换
                paramValues[paramIndex] = caseStringValue(value, parameterTypes[paramIndex]);
            }
        }
        //从 handler 中取出 controller、method，然后利用反射机制进行调用
        try {

            Object result =
                    handlerMapping.getMethod().invoke(handlerMapping.getController(),paramValues);
            if(result == null){ return null; }
            boolean isModelAndView = handlerMapping.getMethod().getReturnType() ==
                    GPModelAndView.class;
            if(isModelAndView){
                return (GPModelAndView)result;
            }else{
                return null;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object caseStringValue(String value, Class<?> clazz) {

        if(clazz == String.class){
            return value;
        }else if(clazz == Integer.class){
            return Integer.valueOf(value);
        }else if(clazz == int.class){
            return Integer.valueOf(value).intValue();
        }else {
            return null;
        }
    }
}
