package com.oceanli.gupao.spring.demo.service.impl;


import com.oceanli.gupao.spring.demo.service.IDemoService;
import com.oceanli.gupao.spring.framework.annotation.GPService;

/**
 * 核心业务逻辑
 */
@GPService
public class DemoService implements IDemoService {

	@Override
	public String get(String name) {
		return "My name is " + name;
	}

}
