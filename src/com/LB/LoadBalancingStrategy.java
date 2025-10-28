package com.LB;

import java.util.List;

public interface LoadBalancingStrategy {
	public Server selectServer(List<Server> servers, String clientIP);
}
