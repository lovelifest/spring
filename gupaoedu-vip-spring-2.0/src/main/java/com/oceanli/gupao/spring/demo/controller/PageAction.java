package com.oceanli.gupao.spring.demo.controller;

import com.oceanli.gupao.spring.demo.service.IQueryService;
import com.oceanli.gupao.spring.framework.annotation.GPAutowired;
import com.oceanli.gupao.spring.framework.annotation.GPController;
import com.oceanli.gupao.spring.framework.annotation.GPRequestMapping;
import com.oceanli.gupao.spring.framework.annotation.GPRequestParam;
import com.oceanli.gupao.spring.framework.webmvc.servlet.GPModelAndView;

import java.util.HashMap;
import java.util.Map;

@GPController
@GPRequestMapping("/")
public class PageAction {

    @GPAutowired
    IQueryService queryService;
    @GPRequestMapping("/first.html")
    public GPModelAndView query(@GPRequestParam("teacher") String teacher){
        String result = queryService.query(teacher);
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("teacher", teacher);
        model.put("data", result);
        model.put("token", "123456");
        return new GPModelAndView("first.html",model);
    }
}
