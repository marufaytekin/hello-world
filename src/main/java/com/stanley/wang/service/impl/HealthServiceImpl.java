package com.stanley.wang.service.impl;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stanley.wang.component.GlobalHealthInfo;
import com.stanley.wang.model.HealthInfo;
import com.stanley.wang.model.RuntimeInfo;
import com.stanley.wang.service.HealthService;

@Service
public class HealthServiceImpl implements HealthService {

	@Autowired
	private GlobalHealthInfo globalInfo;

	public HealthInfo health() {
		return new HealthInfo()
				.withStatus("OK")
				.withVersion(globalInfo.getBuildProperties().getVersion())
				.withSince(globalInfo.getUptime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	}

	public RuntimeInfo info() {
		// TODO Auto-generated method stub
		return new RuntimeInfo()
				.withHealthInfo(this.health())
				.withBuildProperties(globalInfo.getBuildProperties())
				.withEnvironment(System.getenv())
				.withSystemProperties(System.getProperties());
	}
}
