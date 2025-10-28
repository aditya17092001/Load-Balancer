package com.LB;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HealthChecker implements Runnable {
	
	private final List<Server> servers;
	
	public HealthChecker(List<Server> servers) {
		this.servers = servers;
	}

	@Override
	public void run() {
		for(Server server: servers) {
			try {
				HttpURLConnection conn = (HttpURLConnection) new URL(server.getUrl()).openConnection();
				conn.setConnectTimeout(1000);
				conn.connect();
				server.setHealthy(conn.getResponseCode() == 200);
				System.out.println(server.getUrl()+" server is Healthy");
			} catch(Exception e) {
				server.setHealthy(false);
				System.out.println(server.getUrl()+" server is Unhealthy");
			}
		}
		
	}
	
}
