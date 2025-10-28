package com.LB;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadBalancer {
	
	private final List<Server> servers;
	private final LoadBalancingStrategy strategy;
	
	public LoadBalancer(List<Server> servers, LoadBalancingStrategy strategy) {
		this.servers = servers;
		this.strategy = strategy;
	}
	
	public Server getServer(String clientIp) {
		return strategy.selectServer(servers.stream().filter(server -> server.isHealthy()).toList(), clientIp);
	}
		
	public void forwardRequest(String clientIp, String request) {
		Server server = getServer(clientIp);
		if(server == null) {
			System.out.println("No healthy server available");
			return ;
		}
		
		server.incrementConnections();
		System.out.println("Forwarding request to server: "+server.getUrl());
		System.out.println("Status of the server: "+server.getUrl()+", "+server.getActiveConnections()+", "+server.isHealthy());
		System.out.println(".................................");
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
	    executor.submit(() -> {
	        try {
	            Thread.sleep(5000);
	            server.decrementConnections();
	            System.out.println("Decremented connections for server: " + server.getUrl());
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    });

	    executor.shutdown();
		
//		server.decrementConnections();
		
		
	}
}
