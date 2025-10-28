package com.LB;

import java.util.concurrent.atomic.AtomicInteger;

public class Server {

	private String url;
	private boolean isHealthy;
	private final AtomicInteger activeConnections = new AtomicInteger(0);
	
	public Server(String url) {
		this.url = url;
		this.isHealthy = true;
	}
	
	public String getUrl() {
		return url;
	}
	
	public boolean isHealthy() {
		return isHealthy;
	}
	
	public int getActiveConnections() {
		return activeConnections.get();
	}
	
	public void incrementConnections() {
		activeConnections.incrementAndGet();
	}
	
	public void decrementConnections() {
		activeConnections.decrementAndGet();
	}
	
	public void setHealthy(boolean healthy) {
		this.isHealthy = healthy;
	}
}
