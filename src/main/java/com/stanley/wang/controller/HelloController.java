package com.stanley.wang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stanley.wang.model.HelloWorld;
import com.stanley.wang.service.HelloService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class HelloController {
	
	@Autowired
	private HelloService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = { "text/plain"})
	public ResponseEntity<?> getHello()
	{
		ResponseEntity<?> result = null;
		
		try
		{
			HelloWorld hello = service.hello();

			// return the result
			result = new ResponseEntity<String>(hello.getMessage(), HttpStatus.OK);
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
		
		return result;
	}

}
