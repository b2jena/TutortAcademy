---

## 🧠 Complete Wiki-Style Knowledge Base (`README.md`)

````markdown
# 🧠 Java Developer Knowledge Base

*A structured, expandable wiki of core Computer Science, Java, and System Design concepts for interviews and mastery.*

---

## 📘 Table of Contents

1. [Databases & SQL Optimization](#1-databases--sql-optimization)
    - [Indexes](#indexes)
    - [Execution Plans](#execution-plans)
    - [Caching & Pooling](#caching--pooling)
    - [Pagination & Batching](#pagination--batching)

2. [Concurrency & Multithreading](#2-concurrency--multithreading)
    - [Why Thread Pools](#why-thread-pools)
    - [ThreadPoolExecutor Internals](#threadpoolexecutor-internals)
    - [Task Queues](#task-queues)
    - [Rejection Policies](#rejection-policies)
    - [Thread Tuning Formulas](#thread-tuning-formulas)
    - [Synchronized vs Lock vs Volatile](#synchronized-vs-lock-vs-volatile)

3. [Networking & Real-Time Communication](#3-networking--real-time-communication)
    - [Polling](#polling)
    - [WebSocket](#websocket)
    - [Long Polling](#long-polling)
    - [HTTP vs WebSocket vs gRPC](#http-vs-websocket-vs-grpc)

4. [Messaging & Streaming Systems](#4-messaging--streaming-systems)
    - [Kafka Core Concepts](#kafka-core-concepts)
    - [Kafka Architecture](#kafka-architecture)
    - [Kafka vs RabbitMQ](#kafka-vs-rabbitmq)
    - [Use Cases & Best Practices](#use-cases--best-practices)

5. [Algorithmic Thinking & DSA](#5-algorithmic-thinking--dsa)
    - [Rotting Oranges (BFS)](#rotting-oranges-bfs)
    - [Graph Traversal Patterns](#graph-traversal-patterns)
    - [Queue vs Stack vs PriorityQueue](#queue-vs-stack-vs-priorityqueue)

6. [Microservices & Scalability](#6-microservices--scalability)
    - [Handling High Load](#handling-high-load)
    - [Caching Strategies](#caching-strategies)
    - [Database Scaling](#database-scaling)
    - [Resilience Patterns](#resilience-patterns)
    - [Observability](#observability)

7. [Computer Science Fundamentals](#7-computer-science-fundamentals)
    - [Operating Systems](#operating-systems)
    - [Networking Basics](#networking-basics)
    - [Data Structures](#data-structures)
    - [Algorithms](#algorithms)
    - [Java Internals](#java-internals)

8. [System Design (Advanced)](#8-system-design-advanced)
    - [Load Balancing](#load-balancing)
    - [Caching Layers](#caching-layers)
    - [Sharding & Replication](#sharding--replication)
    - [CQRS & Event Sourcing](#cqrs--event-sourcing)
    - [CAP Theorem & Consistency](#cap-theorem--consistency)
    - [Idempotency & Reliability](#idempotency--reliability)

9. [Study Resources](#9-study-resources)
    - [Books](#books)
    - [Practice Sites](#practice-sites)
    - [Mindset](#mindset)

---

## 1. Databases & SQL Optimization

### Indexes

Indexes speed up reads by maintaining a sorted data structure.

- **Types:** B-Tree, Hash, Composite, Covering.
- **Use wisely:** Too many indexes slow down writes.

### Execution Plans

Use:

```sql
EXPLAIN
SELECT *
FROM orders
WHERE customer_id = 10;
````

Analyze:

* Full table scans → add indexes
* Temporary tables → simplify joins
* Filesort → consider covering indexes

### Caching & Pooling

* Use **Redis** or **Memcached** for hot data.
* Use **HikariCP** for connection pooling.

### Pagination & Batching

* Use keyset pagination (`WHERE id > ? LIMIT N`) for large tables.
* Use batch updates/inserts via JDBC batch operations.

---

## 2. Concurrency & Multithreading

### Why Thread Pools

Creating a new thread per request wastes resources. Thread pools reuse threads to manage concurrent tasks efficiently.

### ThreadPoolExecutor Internals

```java
ThreadPoolExecutor(
        corePoolSize,
        maxPoolSize,
        keepAliveTime,
        TimeUnit.MILLISECONDS,
        BlockingQueue<Runnable> workQueue
)
```

### Task Queues

| Queue Type          | Bounded?    | Notes                |
|---------------------|-------------|----------------------|
| LinkedBlockingQueue | ❌ Unbounded | May cause OOM        |
| ArrayBlockingQueue  | ✅ Bounded   | Controlled memory    |
| SynchronousQueue    | ✅ No queue  | Used by cached pools |

### Rejection Policies

* `AbortPolicy` → throws exception
* `CallerRunsPolicy` → runs task in caller thread
* `DiscardPolicy` → silently drops task
* `DiscardOldestPolicy` → replaces oldest queued task

### Thread Tuning Formulas

CPU-bound:

```
threads ≈ cores + 1
```

IO-bound:

```
threads ≈ cores * (1 + wait_time / compute_time)
```

### Synchronized vs Lock vs Volatile

* `synchronized`: mutual exclusion, blocking.
* `Lock`: flexible control, tryLock, fairness.
* `volatile`: visibility guarantee, no atomicity.

---

## 3. Networking & Real-Time Communication

### Polling

* Client repeatedly requests updates.
* Simple but inefficient for frequent changes.

### WebSocket

* Persistent, bi-directional TCP connection.
* Real-time and efficient.

### Long Polling

* Server holds request open until data is available.
* Simulates real-time updates over HTTP.

### HTTP vs WebSocket vs gRPC

| Protocol  | Direction       | Encoding          | Use Case      |
|-----------|-----------------|-------------------|---------------|
| HTTP/REST | Uni-directional | Text (JSON)       | CRUD APIs     |
| WebSocket | Bi-directional  | Text/Binary       | Real-time     |
| gRPC      | Bi-directional  | Binary (ProtoBuf) | Microservices |

---

## 4. Messaging & Streaming Systems

### Kafka Core Concepts

* **Producer** → writes data
* **Broker** → stores data
* **Consumer** → reads data
* **Topic** → logical channel
* **Partition** → unit of parallelism
* **Offset** → position in partition

### Kafka Architecture

* Data replicated across brokers.
* Consumers in same group share partitions.
* Offsets stored in Kafka itself.

### Kafka vs RabbitMQ

| Feature   | Kafka           | RabbitMQ       |
|-----------|-----------------|----------------|
| Retention | Time-based      | Until consumed |
| Ordering  | Partition-level | Queue-level    |
| Replay    | ✅               | ❌              |
| Use Case  | Streaming       | Task Queues    |

### Use Cases & Best Practices

* Event-driven microservices
* Log aggregation
* Real-time analytics
* Set appropriate retention + partitions

---

## 5. Algorithmic Thinking & DSA

### Rotting Oranges (BFS)

Each minute, fresh oranges adjacent to rotten ones get infected.

**Approach:**

* Multi-source BFS.
* Count minutes as BFS levels.

### Graph Traversal Patterns

* **BFS** → shortest path, level order.
* **DFS** → path finding, connected components.

### Queue vs Stack vs PriorityQueue

* Queue → FIFO
* Stack → LIFO
* PriorityQueue → ordered by comparator

---

## 6. Microservices & Scalability

### Handling High Load

* **Load Balancing** (Round Robin, Least Connections)
* **Caching Layers**
* **Connection Pooling**
* **Rate Limiting**

### Caching Strategies

* Client-side
* CDN
* Redis
* Application-level cache

### Database Scaling

* Vertical (bigger machine)
* Horizontal (sharding, read replicas)

### Resilience Patterns

* Circuit Breaker
* Retry + Backoff
* Bulkhead
* Timeout + Fallback

### Observability

* Logging → ELK
* Metrics → Prometheus
* Tracing → Jaeger / OpenTelemetry

---

## 7. Computer Science Fundamentals

### Operating Systems

* Process vs Thread
* Context Switching
* Deadlocks (4 conditions)
* Scheduling Algorithms (Round Robin, Priority)

### Networking Basics

* TCP vs UDP
* DNS, HTTP lifecycle
* Latency vs Throughput
* Load balancers & reverse proxies

### Data Structures

* Array, LinkedList, Stack, Queue
* HashMap, Tree, Graph, Heap

### Algorithms

* Sorting → QuickSort, MergeSort
* Searching → Binary Search
* Graph → Dijkstra, BFS, DFS
* Dynamic Programming → LIS, Knapsack

### Java Internals

* JVM Memory Model
* Garbage Collection (G1, CMS)
* ClassLoader Hierarchy
* JIT Compilation

---

## 8. System Design (Advanced)

### Load Balancing

* Round Robin
* Consistent Hashing
* Least Connection

### Caching Layers

* CDN
* Redis / Memcached
* Write-through, Write-back strategies

### Sharding & Replication

* Sharding by key/modulo
* Leader-follower replication
* Consistency tuning

### CQRS & Event Sourcing

* Separate read/write models
* Persist events as source of truth

### CAP Theorem & Consistency

* You can’t have all 3: Consistency, Availability, Partition Tolerance.
* Choose based on business needs.

### Idempotency & Reliability

* Ensure repeated requests have same effect.
* Use unique request IDs or deduplication tables.

---

## 9. Study Resources

### Books

* *Java Concurrency in Practice* – Brian Goetz
* *Kafka: The Definitive Guide* – O’Reilly
* *Designing Data-Intensive Applications* – Martin Kleppmann
* *Grokking the System Design Interview*

### Practice Sites

* [LeetCode](https://leetcode.com)
* [InterviewBit](https://www.interviewbit.com)
* [GeeksforGeeks](https://www.geeksforgeeks.org)
* [System Design Primer](https://github.com/donnemartin/system-design-primer)

### Mindset

> 🧩 “Don’t memorize answers — build mental models.”
> Learn how components interact, not just what they do.

---

## 🧾 Author Notes

Curated by **Bikash Jena**, Java & Microservices Developer, passionate about system architecture, distributed systems,
and high-performance backend design.
