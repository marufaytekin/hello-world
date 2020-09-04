package com.stanley.wang.model;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthInfo {
	
	private String 		status;
	private String		version;
	private String 		uptime;
	
	public ArrayList<String> getIPAddresses() {
		
		ArrayList<String> ips = new ArrayList<String>();
		
		try 
		{
			InetAddress[]  addresses = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
			
			
			Stream.of(addresses).filter(s->s.isLoopbackAddress() == false)
								.filter(s->s.isMulticastAddress() == false)
								.filter(s->s.isLinkLocalAddress() == false)						
								.forEach(address->ips.add(address.getHostAddress()));
		}
		catch(Exception e) {}
		
		return ips;
	}
	
	public String getHostname() {
		
		String hostname="localhost";
		
		try {hostname = InetAddress.getLocalHost().getHostName();}
		catch(Exception e) {}
		return hostname;
	}
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public HealthInfo withStatus(String status) {
		this.status = status;
		return this;
	}
	
	public HealthInfo withVersion(String version) {
		this.version = version;
		return this;
	}
	
	public HealthInfo withSince(String uptime) {
		this.uptime = "up since " + uptime;
		return this;
	}
}
