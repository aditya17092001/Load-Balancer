package com.LB;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.LB.Algorithms.IPHash;
import com.LB.Algorithms.LeastConnection;
import com.LB.Algorithms.RoundRobin;

public class Main {
	public static void main(String args[]) {
		List<Server> servers = List.of(
				new Server("http://localhost:8001"),
				new Server("http://localhost:8002"),
	            new Server("http://localhost:8003")
		);
		
		LoadBalancingStrategy leastStrategy = new LeastConnection();
		LoadBalancingStrategy roundRobinStrategy = new RoundRobin();
		LoadBalancingStrategy ipHashStrategy = new IPHash();
		LoadBalancer lb = new LoadBalancer(servers, ipHashStrategy, 20);
		
		Thread healthThread = new Thread(new HealthChecker(servers));
        healthThread.setDaemon(true);
        healthThread.start();
        
        for (int i = 0; i < 1000; i++) {
            String clientIp = "192.168.1." + i;
            lb.forwardRequestAsync(clientIp, "GET /api/data");
        }

        lb.shutdown();

        System.out.println("All requests processed.");
	}
}
