package com.LB.Algorithms;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.LB.LoadBalancingStrategy;
import com.LB.Server;

public class RoundRobin implements LoadBalancingStrategy {
	
	private AtomicInteger index = new AtomicInteger(0);

	@Override
	public Server selectServer(List<Server> servers, String clientIP) {
		int i = Math.abs(index.getAndIncrement() % servers.size());
        return servers.get(i);
	}
}
