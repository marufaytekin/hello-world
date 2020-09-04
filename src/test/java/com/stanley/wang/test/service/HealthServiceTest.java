package com.stanley.wang.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stanley.wang.model.HealthInfo;
import com.stanley.wang.model.RuntimeInfo;
import com.stanley.wang.service.HealthService;
import com.stanley.wang.test.AbstractTest;


public class HealthServiceTest extends AbstractTest {

		@Autowired
		private HealthService service;
		
		
		@Test
		public void healthService_Health_Test_Success() throws Exception {
			
			HealthInfo health = service.health();
			
			Assert.assertTrue((health != null) && 
					(health.getStatus().compareTo("OK") == 0));
		}
		
		@Test
		public void healthService_Info_Test_Success() throws Exception {
			
			RuntimeInfo r = service.info();
			
			Assert.assertTrue((r != null) && 
					(r.getHealthInfo().getStatus().compareTo("OK") == 0));
		}
	}
