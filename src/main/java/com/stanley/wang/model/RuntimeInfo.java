package com.stanley.wang.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class RuntimeInfo {

	private HealthInfo healthInfo;
	
	private BuildProperties buildproperties;
	
	private Map envMap = new HashMap<String, String>();
	
	private Map sysMap = new HashMap<String, String>();
	
	public HealthInfo getHealthInfo() {
		return this.healthInfo;
	}
	
	public BuildProperties getBuildInfo() {
		return this.buildproperties;
	}
	
	public Map getEnvironment() {
		return this.envMap;
	}
	
	public Map getSystemProperties() {
		return this.sysMap;
	}
	
	public RuntimeInfo withHealthInfo(HealthInfo healthInfo) {
		this.healthInfo = healthInfo;
		return this;
	}
	
	public RuntimeInfo withBuildProperties(BuildProperties buildProperties) {
		this.buildproperties = buildProperties;
		return this;
	}
	
	public RuntimeInfo withEnvironment(Map envMap) {
		this.envMap = envMap;
		return this;
	}
	
	public RuntimeInfo withSystemProperties(Properties properties) {
		this.sysMap = properties;
		return this;
	}
}
