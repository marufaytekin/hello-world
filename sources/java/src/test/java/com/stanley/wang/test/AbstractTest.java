package com.stanley.wang.test;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stanley.wang.HelloRestAPIApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloRestAPIApplication.class)
@AutoConfigureMockMvc
public abstract class AbstractTest 
{
   @Autowired
   private WebApplicationContext webApplicationContext;

   @Autowired
   protected MockMvc mvc;
   
   protected void init()
   {
	   this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
   }
   
   protected String mapToJson(Object obj) throws JsonProcessingException 
   {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(obj);
   }
   
   protected <T> T mapFromJson(String json, Class<T> clazz) 
		   throws JsonParseException, JsonMappingException, IOException 
   {
      
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, clazz);
   }
}