package com.stanley.wang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stanley.wang.model.HealthInfo;
import com.stanley.wang.model.RuntimeInfo;
import com.stanley.wang.service.HealthService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class HealthController {

	
	@Autowired
	private HealthService service;
	
	@RequestMapping(value = "/healthz", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<?> health() {
		
		ResponseEntity<?> result = null;
		
		try
		{
			HealthInfo healthInfo = service.health();
			result = new ResponseEntity<HealthInfo>(healthInfo, HttpStatus.OK);
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/info", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<?> info() {
		
		ResponseEntity<?> result = null;
		
		try
		{
			RuntimeInfo runtimeInfo = service.info();
			result = new ResponseEntity<RuntimeInfo>(runtimeInfo, HttpStatus.OK);
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
		
		return result;
	}
}
