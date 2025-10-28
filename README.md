# ⚖️ Java Load Balancer

A multithreaded **Load Balancer** implementation in Java that distributes incoming client requests across multiple backend servers using different load-balancing strategies.  
It also includes a background **Health Checker** that continuously monitors server health.

---

## 🚀 Features

- ✅ **Supports Multiple Algorithms**
  - Round Robin
  - Least Connections
  - IP Hash
- 🔄 **Concurrent Request Handling** using a thread pool
- ❤️ **Health Checking** of servers via background thread
- ⚙️ **Thread-Safe Server Management** using `AtomicInteger`
- 📊 **Extensible Design** – Easily add new balancing strategies

---

## 🧱 System Design Overview

### 🏗️ High-Level Design (HLD)

**Components:**
1. **Client** – Sends simulated requests to the load balancer.
2. **Load Balancer** – Routes requests to backend servers based on selected strategy.
3. **Servers** – Backend servers that process incoming requests.
4. **Health Checker** – Periodically checks server health.

**Flow:**
1. Client sends request to Load Balancer.
2. Load Balancer chooses a healthy server based on the strategy.
3. Request is forwarded asynchronously using a thread pool.
4. Server handles the request and updates its connection count.
5. HealthChecker runs in the background to mark servers as healthy/unhealthy.

---

### ⚙️ Low-Level Design (LLD)

#### Core Classes
| Class | Responsibility |
|-------|----------------|
| `Server` | Represents a backend server with active connection tracking and health status. |
| `LoadBalancer` | Manages servers, applies strategy, and forwards requests asynchronously. |
| `LoadBalancingStrategy` | Interface for implementing different routing strategies. |
| `RoundRobin`, `LeastConnection`, `IPHash` | Different implementations of `LoadBalancingStrategy`. |
| `HealthChecker` | Periodically pings all servers and updates their health status. |
| `Main` | Initializes the system, starts threads, and simulates client requests. |

---

## 🧩 Project Structure

```bash
📦 com.LB
 ┣ 📂 Algorithms
 ┃ ┣ 📜 IPHash.java
 ┃ ┣ 📜 LeastConnection.java
 ┃ ┗ 📜 RoundRobin.java
 ┣ 📜 HealthChecker.java
 ┣ 📜 LoadBalancer.java
 ┣ 📜 LoadBalancingStrategy.java
 ┣ 📜 Server.java
 ┗ 📜 Main.java
```
---

## ⚙️ How It Works

1. The `Main` class initializes three backend servers:  
   `http://localhost:8001`, `http://localhost:8002`, and `http://localhost:8003`.
2. You can switch between load-balancing algorithms by changing this line in `Main.java`:

```
LoadBalancingStrategy strategy = new RoundRobin();
```

3. The LoadBalancer forwards incoming requests to servers using a fixed-size thread pool.
4. The HealthChecker runs continuously in the background to verify server health.

---

## 🧠 Example Output
```
[pool-1-thread-3] Client 192.168.1.2 -> http://localhost:8001
Server Status: http://localhost:8001, ActiveConnections=3, Healthy=true
........................................
http://localhost:8002 server is Healthy
http://localhost:8003 server is Unhealthy
All requests processed.
```
---
## 🧰 Prerequisites
Java 17+

Any IDE (IntelliJ / Eclipse / VS Code) or command-line setup

Optionally, mock backend servers running on ports 8001–8003

To start mock servers quickly (for demo), run:

```
python3 -m http.server 8001
python3 -m http.server 8002
python3 -m http.server 8003
```

---

## ▶️ Run the Program

Copy code
javac com/LB/*.java com/LB/Algorithms/*.java
java com.LB.Main
🛑 Stopping Servers
If you want to stop mock servers manually:

Windows:
To kill the ports
```
netstat -ano | findstr :8001
taskkill /PID <pid> /F
```
---

## 🧩 Future Enhancements
Add weighted round robin algorithm

Support HTTPS backend servers

Introduce rate limiting and retry logic

Expose a REST API for dynamic server registration
