package com.LB.Algorithms;

import java.util.List;

import com.LB.LoadBalancingStrategy;
import com.LB.Server;

public class IPHash implements LoadBalancingStrategy {

	@Override
	public Server selectServer(List<Server> servers, String clientIP) {
		int hash = Math.abs(clientIP.hashCode());
		return servers.get(hash % servers.size());
	}

}
