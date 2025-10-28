package com.LB.Algorithms;

import java.util.List;

import com.LB.LoadBalancingStrategy;
import com.LB.Server;

public class LeastConnection implements LoadBalancingStrategy {

	@Override
	public Server selectServer(List<Server> servers, String clientIP) {
		return servers.stream()
				.filter(server -> server.isHealthy())
				.min((s1, s2) -> Integer.compare(s1.getActiveConnections(), s2.getActiveConnections()))
				.orElse(null);
	}

}
