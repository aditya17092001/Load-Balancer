package com.LB;

public class Server {

	private String url;
	private boolean isHealthy;
	private int activeConnections;
	
	public Server(String url) {
		this.url = url;
		this.isHealthy = true;
		this.activeConnections = 0;
	}
	
	public String getUrl() {
		return url;
	}
	
	public boolean isHealthy() {
		return isHealthy;
	}
	
	public int getActiveConnections() {
		return activeConnections;
	}
	
	public synchronized void incrementConnections() {
		activeConnections++;
	}
	
	public synchronized void decrementConnections() {
		activeConnections--;
	}
	
	public void setHealthy(boolean healthy) {
		this.isHealthy = healthy;
	}
}
