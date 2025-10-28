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
				new Server("http://localhost:8082"),
	            new Server("http://localhost:8083")
		);
		
		LoadBalancingStrategy leastStrategy = new LeastConnection();
		LoadBalancingStrategy roundRobinStrategy = new RoundRobin();
		LoadBalancingStrategy ipHashStrategy = new IPHash();
		LoadBalancer lb = new LoadBalancer(servers, roundRobinStrategy);
		
//		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        scheduler.scheduleAtFixedRate(
//            new HealthChecker(servers),
//            0,                // initial delay
//            10,               // check every 10 seconds
//            TimeUnit.SECONDS
//        );
		
        for (int i = 0; i < 5; i++) {
            String clientIp = "192.168.0." + i;
            lb.forwardRequest(clientIp, "GET /api");
        }
	}
}
