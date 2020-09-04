package com.stanley.wang.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stanley.wang.model.HelloWorld;
import com.stanley.wang.service.HelloService;
import com.stanley.wang.test.AbstractTest;


public class HelloServiceTest extends AbstractTest {

	@Autowired
	private HelloService service;
	
	
	@Test
	public void helloService_Test_Success() throws Exception {
		
		HelloWorld hello = service.hello();
		
		Assert.assertTrue((hello != null) && 
				(hello.getMessage() != null) && 
				(hello.getMessage().compareTo("Hello!") == 0));
	}
}
