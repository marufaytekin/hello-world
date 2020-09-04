package com.stanley.wang.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.stanley.wang.test.AbstractTest;

public class HelloControllerTest extends AbstractTest
{
	@Test
	public void getAccount_Test_Success() throws Exception 
	{
	   String uri = "/";
	   
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
			   					.accept(MediaType.TEXT_PLAIN))
			   					.andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   
	   String content = mvcResult.getResponse().getContentAsString();
	   
	   assertFalse(content.isEmpty());
	   assertTrue((content != null) && (content.compareTo("Hello!") == 0));
	}
}
