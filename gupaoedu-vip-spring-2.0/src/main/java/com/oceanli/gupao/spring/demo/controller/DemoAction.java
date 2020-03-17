package com.oceanli.gupao.spring.demo.controller;

import com.oceanli.gupao.spring.demo.service.IDemoService;
import com.oceanli.gupao.spring.framework.annotation.GPController;
import com.oceanli.gupao.spring.framework.annotation.GPAutowired;
import com.oceanli.gupao.spring.framework.annotation.GPRequestMapping;
import com.oceanli.gupao.spring.framework.annotation.GPRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//虽然，用法一样，但是没有功能
@GPController
@GPRequestMapping("/demo")
public class DemoAction {

  	@GPAutowired
	private IDemoService demoService;

	@GPRequestMapping("/query")
	public void query(HttpServletRequest req, HttpServletResponse resp,
                      @GPRequestParam("name") String name){
//		String result = demoService.get(name);
		String result = "My name is " + name;
		try {
			resp.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GPRequestMapping("/add")
	public void add(HttpServletRequest req, HttpServletResponse resp,
                    @GPRequestParam("a") Integer a, @GPRequestParam("b") Integer b){
		try {
			resp.getWriter().write(a + "+" + b + "=" + (a + b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GPRequestMapping("/remove")
	public void remove(HttpServletRequest req, HttpServletResponse resp,
                       @GPRequestParam("id") Integer id){
	}

}
