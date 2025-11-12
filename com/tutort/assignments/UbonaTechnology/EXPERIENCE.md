# 🧠 Java Developer System Design & Core CS Knowledge Base

*A single point of truth for interview prep & deep understanding.*

---

## 🧩 1. Databases & Query Optimization

### 🧠 Topic: MySQL Optimization

**Explanation:**
When handling millions of requests, SQL optimization is critical to ensure scalability and low latency.

#### 🔹 Subtopics:

* **Indexes:**

    * B-tree and hash indexes
    * Covering indexes (include columns used in SELECT)
    * Composite indexes (multi-column)
* **Query Execution Plan:**

    * Use `EXPLAIN` keyword
    * Identify full table scans, temporary tables, and filesorts
* **Denormalization:**

    * When joins become too costly, use pre-joined or summarized data
* **Caching Layers:**

    * Redis / Memcached for repeated reads
* **Connection Pooling:**

    * Maintain a fixed number of DB connections (HikariCP, DBCP)
* **Batching:**

    * Use batch inserts/updates instead of per-row operations
* **Pagination Optimization:**

    * Use `LIMIT ... OFFSET` carefully
    * For large datasets → `WHERE id > last_seen_id`

---

## ⚙️ 2. Concurrency & Multithreading

### 🧠 Topic: Thread Pool

**Explanation:**
A thread pool is a group of pre-initialized threads that execute tasks from a queue, improving performance and
controlling concurrency.

#### 🔹 Subtopics:

* **Need for Thread Pools:**

    * Thread creation/destruction overhead
    * Resource control and stability
* **Executor Framework:**

    * `Executors.newFixedThreadPool()`
    * `newCachedThreadPool()`, `newScheduledThreadPool()`
* **ThreadPoolExecutor Parameters:**

    * `corePoolSize`, `maxPoolSize`, `queueCapacity`, `keepAliveTime`
* **Task Queue:**

    * `ArrayBlockingQueue` (bounded)
    * `LinkedBlockingQueue` (unbounded)
    * `SynchronousQueue`
* **Rejection Policies:**

    * `AbortPolicy`, `CallerRunsPolicy`, `DiscardPolicy`, `DiscardOldestPolicy`
* **Tuning Guidelines:**

    * CPU-bound → `#threads ≈ #cores + 1`
    * IO-bound → `#threads ≈ #cores * (1 + wait_time / compute_time)`

#### 🔸 Example:

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
        10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(20)
);
```

---

### 🧠 Topic: Polling vs WebSocket

#### 🔹 Polling

* Client periodically requests updates via HTTP.
* Simple but inefficient (creates many redundant requests).
* Good for low-frequency updates.

#### 🔹 WebSocket

* Persistent TCP connection for real-time, bi-directional data.
* Efficient for chat, trading, gaming, etc.
* Reduces latency and network overhead.

#### 🔹 Long Polling

* Server holds the request open until data changes.
* Bridges the gap between polling and real-time.

#### ⚖️ Comparison:

| Feature    | Polling                | WebSocket                 |
|------------|------------------------|---------------------------|
| Connection | New HTTP per request   | One persistent connection |
| Direction  | One-way                | Two-way                   |
| Efficiency | Low                    | High                      |
| Use Case   | Simple periodic checks | Real-time updates         |

---

## ⚙️ 3. Distributed Systems & Messaging

### 🧠 Topic: Apache Kafka

**Explanation:**
Kafka is a distributed event streaming platform for building real-time data pipelines and event-driven architectures.

#### 🔹 Core Concepts

* **Producer / Consumer** → Send and receive messages.
* **Topic** → Logical channel for messages.
* **Partition** → Unit of parallelism; ordered log.
* **Broker** → Kafka server node.
* **Offset** → Position of a message in a partition.
* **Consumer Group** → Set of consumers sharing load.

#### 🔹 Key Features

* High throughput (sequential disk I/O)
* Replication and fault tolerance
* Retention and replay capability
* Exactly-once semantics
* Integration with Spark, Flink, Debezium, etc.

#### 🔹 Use Cases

* Microservice communication
* Event sourcing
* Log aggregation
* Real-time analytics

#### ⚖️ Kafka vs RabbitMQ

| Feature   | Kafka           | RabbitMQ       |
|-----------|-----------------|----------------|
| Retention | Time-based      | Until consumed |
| Ordering  | Partition-level | Queue-level    |
| Model     | Pull            | Push           |
| Replay    | Yes             | No             |
| Use Case  | Streams         | Tasks          |

---

## 🧮 4. Algorithms & Data Structures

### 🧠 Topic: Rotting Oranges (Grid BFS)

**Problem:**
Each minute, fresh oranges adjacent to rotten ones become rotten. Find min time for all to rot.

#### 🔹 Approach:

* Use **BFS (Breadth-First Search)**.
* Track rotten oranges and time.
* Process layer-by-layer (minute-by-minute).
* Add diagonal moves if required.

#### 🔸 Pseudocode:

```java
Queue<Cell> q = new LinkedList<>();
while(!q.

isEmpty()){
        for(
int i = 0;
i<size;i++){
        // rot adjacent oranges
        }
minutes++;
        }
```

#### 🔹 Subtopics:

* BFS / DFS
* Queue-based level traversal
* Matrix boundary checks
* Time complexity: O(n*m)

---

## ☁️ 5. Microservices & Scalability

### 🧠 Topic: Handling Millions of Requests

#### 🔹 Subtopics:

* **Horizontal Scaling:** Add more instances behind a load balancer.
* **Database Optimization:** Use indexing, read replicas, caching.
* **Caching Layers:** Redis, CDN, application-level caches.
* **Asynchronous Processing:** Kafka, RabbitMQ, background workers.
* **Connection Pooling:** Limit database connections per service.
* **Rate Limiting & Circuit Breakers:** Protect from traffic spikes.
* **Metrics & Observability:** Prometheus, Grafana, ELK.

---

## ⚙️ 6. Networking Fundamentals

### 🧠 Topic: HTTP vs WebSocket vs REST vs gRPC

| Protocol  | Type               | Direction       | Use Case                                 |
|-----------|--------------------|-----------------|------------------------------------------|
| HTTP/REST | Request/Response   | Client → Server | CRUD APIs                                |
| WebSocket | Persistent         | Bi-directional  | Real-time chat, games                    |
| gRPC      | Binary over HTTP/2 | Bi-directional  | Microservices, performance-critical APIs |

---

## 🧠 7. Computer Science Fundamentals (Base Layer)

### 🔹 Operating System

* Process vs Thread
* Context Switching
* Deadlocks (4 conditions)
* Thread Synchronization (Locks, Semaphores)
* Memory Management (Heap vs Stack)

### 🔹 Networking

* TCP vs UDP
* DNS resolution
* HTTP lifecycle
* Latency vs Throughput
* Load Balancers (Round Robin, Least Connections)

### 🔹 Data Structures

* Arrays, LinkedLists, HashMaps
* Trees, Graphs
* Queues, Stacks, PriorityQueues

### 🔹 Algorithms

* Sorting (QuickSort, MergeSort)
* Searching (Binary Search)
* Graph (BFS, DFS, Dijkstra)
* Dynamic Programming (Knapsack, LIS)

### 🔹 Java Internals

* JVM Memory Model (Heap, PermGen, GC)
* ClassLoader hierarchy
* Synchronized blocks vs Locks
* `volatile` keyword & happens-before relation

---

## 🧩 8. System Design Topics (Advanced)

* **Load Balancing & Scaling**
* **Caching Strategies (LRU, LFU)**
* **API Gateway / Reverse Proxy**
* **Database Sharding**
* **CQRS & Event Sourcing**
* **Distributed Transactions / Sagas**
* **CAP Theorem**
* **Idempotency**
* **Consistency Models (Strong, Eventual)**

---

## 📚 9. Reference & Study Tips

* Read: *Java Concurrency in Practice*
* Kafka: *Kafka: The Definitive Guide (O’Reilly)*
* System Design: *Grokking the System Design Interview*
* Practice algorithms: *LeetCode, InterviewBit, GeeksforGeeks*

---

### 🧾 Final Note

> ⚙️ “Build mental models, not memorized answers.”
> Every concept here connects to another — like threads connecting to concurrency, which connects to scaling, which
> connects to distributed systems.

---