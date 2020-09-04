package com.stanley.wang.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.stanley.wang.model.HealthInfo;
import com.stanley.wang.model.RuntimeInfo;
import com.stanley.wang.test.AbstractTest;

public class HealthControllerTest extends AbstractTest {

	@Test
	public void getHealthInfo_Health_Test_Success() throws Exception 
	{
	   String uri = "/healthz";
	   
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
			   					.accept(MediaType.APPLICATION_JSON))
			   					.andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   
	   String content = mvcResult.getResponse().getContentAsString();
	   
	   HealthInfo healthInfo = super.mapFromJson(content, HealthInfo.class);
	  
	   
	   assertTrue((healthInfo != null) && (healthInfo.getStatus().compareTo("OK") == 0));
	}
	
	
	@Test
	public void getHealthInfo_Info_Test_Success() throws Exception 
	{
	   String uri = "/info";
	   
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
			   					.accept(MediaType.APPLICATION_JSON))
			   					.andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   
	   String content = mvcResult.getResponse().getContentAsString();
	   
	   RuntimeInfo runtime = super.mapFromJson(content, RuntimeInfo.class);
	  
	   
	   assertTrue((runtime != null) && (runtime.getHealthInfo().getStatus().compareTo("OK") == 0));
	}
}
