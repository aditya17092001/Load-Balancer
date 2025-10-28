package com.LB;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LoadBalancer {
	
	private final List<Server> servers;
	private final LoadBalancingStrategy strategy;
	private final ExecutorService executor;
	
	public LoadBalancer(List<Server> servers, LoadBalancingStrategy strategy, int threadPoolSize) {
		this.servers = servers;
		this.strategy = strategy;
		this.executor = Executors.newFixedThreadPool(threadPoolSize);
	}
	
	public Server getServer(String clientIp) {
		return strategy.selectServer(servers.stream().filter(server -> server.isHealthy()).toList(), clientIp);
	}
		
	public void forwardRequestAsync(String clientIp, String request) {
		executor.submit(() -> handleRequest(clientIp, request));
	}
	
	private void handleRequest(String clientIp, String request) {
		Server server = getServer(clientIp);
		
		if(server == null) {
			System.out.println("No healthy server available");
			return ;
		}
		
		server.incrementConnections();
		System.out.printf("[%s] Client %s -> %s%n", Thread.currentThread().getName(), clientIp, server.getUrl());
        System.out.println("Server Status: " + server.getUrl() + ", ActiveConnections=" + server.getActiveConnections() + ", Healthy=" + server.isHealthy());
        System.out.println("........................................");

        try {
            Thread.sleep(500); 
        } catch (InterruptedException ignored) {
        } finally {
            server.decrementConnections();
        }
	}
	
	public void shutdown() {
        System.out.println("\nInitiating graceful shutdown of LoadBalancer...");
        executor.shutdown(); // stop accepting new tasks

        try {
            // wait for all submitted tasks to finish within timeout
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Forcing shutdown... some tasks may not have finished.");
                executor.shutdownNow(); // forcefully stop running tasks
            } else {
                System.out.println("All requests processed successfully before shutdown.");
            }
        } catch (InterruptedException e) {
            System.out.println("Shutdown interrupted, forcing immediate shutdown.");
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
	
	public void awaitCompletion() {
	    executor.shutdown();
	    try {
	        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
	            executor.shutdownNow();
	        }
	    } catch (InterruptedException e) {
	        executor.shutdownNow();
	    }
	}

}
