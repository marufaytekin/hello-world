package com.stanley.wang.component;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GlobalHealthInfo {
	
	private static LocalDateTime uptime = LocalDateTime.now();

	@Autowired
	private BuildProperties buildProperties;
		
	public BuildProperties getBuildProperties() {
		return this.buildProperties;
	}
	
	public static LocalDateTime getUptime() {
		return uptime;
	}
}