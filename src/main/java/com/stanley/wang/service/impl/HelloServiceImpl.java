package com.stanley.wang.service.impl;

import org.springframework.stereotype.Service;

import com.stanley.wang.model.HelloWorld;
import com.stanley.wang.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public HelloWorld hello() {
		// TODO Auto-generated method stub
		return new HelloWorld().withMessage("Hello!");
	}
	
}
