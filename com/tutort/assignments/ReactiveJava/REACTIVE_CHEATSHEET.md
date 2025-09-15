# 🌊 Java Reactive Programming Cheatsheet

## 🎯 Core Concepts

### Reactive Manifesto (4 Pillars)

- **RESPONSIVE**: React to users quickly
- **RESILIENT**: React to failure gracefully
- **ELASTIC**: React to load by scaling
- **MESSAGE-DRIVEN**: React via async messages

### Mono vs Flux

```java
// Mono: 0 or 1 item
Mono<String> mono = Mono.just("Hello");
Mono<User> user = userService.findById(1);

// Flux: 0 to N items  
Flux<String> flux = Flux.just("A", "B", "C");
Flux<User> users = userService.findAll();
```

## 🔧 Essential Operators

### Creation

```java
// Mono
Mono.just("value")
Mono.empty()
Mono.error(new Exception())
Mono.fromCallable(() -> blockingCall())

// Flux
Flux.just("A", "B", "C")
Flux.fromIterable(list)
Flux.range(1, 10)
Flux.interval(Duration.ofSeconds(1))
```

### Transformation

```java
// Map: 1-to-1 sync transformation
mono.map(String::toUpperCase)
flux.map(x -> x * 2)

// FlatMap: 1-to-N async transformation
mono.flatMap(id -> userService.findById(id))
flux.flatMap(user -> postService.findByUser(user))
```

### Filtering

```java
flux.filter(x -> x > 0)
flux.take(5)
flux.skip(2)
flux.distinct()
```

### Combination

```java
// Zip: Combine pairwise
Mono.zip(mono1, mono2, (a, b) -> a + b)

// Merge: Interleave emissions
Flux.merge(flux1, flux2)
```

### Error Handling

```java
mono.onErrorReturn("fallback")
mono.onErrorResume(ex -> alternativeMono)
mono.retry(3)
mono.doOnError(ex -> log.error("Error", ex))
```

## ⚡ Schedulers

### Types

```java
Schedulers.single()          // Single background thread
Schedulers.elastic()         // Unbounded thread pool (I/O)
Schedulers.parallel()        // Fixed pool (CPU cores)
Schedulers.boundedElastic()  // Bounded pool with queue
```

### Usage

```java
// subscribeOn: WHERE subscription happens (upstream)
mono.subscribeOn(Schedulers.boundedElastic())

// publishOn: WHERE downstream operations happen
flux.publishOn(Schedulers.parallel())
    .map(this::cpuIntensiveWork)
```

## 🔄 Backpressure Strategies

```java
// Drop old items when buffer full
flux.onBackpressureDrop()

// Keep only latest item
flux.onBackpressureLatest()

// Buffer with size limit
flux.onBackpressureBuffer(1000)

// Error when overwhelmed
flux.onBackpressureError()
```

## 🔌 Blocking Integration

```java
// WRONG: Blocks reactive thread
mono.map(data -> blockingCall(data)) // ❌

// CORRECT: Use appropriate scheduler
Mono.fromCallable(() -> blockingCall())
    .subscribeOn(Schedulers.boundedElastic()) // ✅
```

## 🎯 Common Patterns

### Parallel Processing

```java
flux.parallel(4)
    .runOn(Schedulers.parallel())
    .map(this::process)
    .sequential()
```

### Timeout & Retry

```java
mono.timeout(Duration.ofSeconds(5))
    .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
```

### Caching

```java
mono.cache(Duration.ofMinutes(5))
```

### Rate Limiting

```java
flux.delayElements(Duration.ofMillis(100)) // 10/second
```

## 🚨 Interview Red Flags

❌ **Avoid These Mistakes:**

- Blocking on reactive threads
- Not handling backpressure
- Ignoring error scenarios
- Using reactive for simple CRUD
- Not considering scheduler choice

✅ **Best Practices:**

- Use appropriate schedulers
- Handle errors gracefully
- Consider backpressure strategies
- Test with StepVerifier
- Monitor reactive metrics

## 🎯 When to Use Reactive

### ✅ Good Use Cases

- High concurrency (1000+ connections)
- I/O intensive applications
- Real-time data streaming
- Event-driven architectures
- Microservices communication

### ❌ Avoid When

- Simple CRUD operations
- CPU-intensive computations only
- Small scale applications
- Team lacks reactive experience
- Debugging complexity is concern

## 🔥 Interview Questions

**Q: Hot vs Cold Publishers?**
A: Cold starts on subscription (HTTP request), Hot emits regardless (mouse clicks)

**Q: subscribeOn vs publishOn?**  
A: subscribeOn affects upstream, publishOn affects downstream

**Q: When use flatMap vs map?**
A: flatMap for async operations returning Publisher, map for sync transformations

**Q: Handle slow consumers?**
A: Apply backpressure strategies: drop, latest, buffer, or error

## 🏗️ System Design Scenarios

### Real-time Notifications

- WebSocket connections: `Flux<Notification>`
- Backpressure for burst traffic
- Event-driven architecture

### Stock Trading Platform

- Low-latency event processing
- Real-time price feeds: `Flux<Price>`
- Non-blocking order matching

### Chat Application

- Message streams: `Flux<Message>`
- User presence: Hot publisher
- Async message persistence

### IoT Data Processing

- Sensor streams: `Flux<SensorReading>`
- Windowing and aggregation
- Real-time anomaly detection

## 📊 Performance Tips

1. **Choose Right Scheduler**
    - I/O → `boundedElastic()`
    - CPU → `parallel()`
    - Sequential → `single()`

2. **Optimize Pipelines**
    - Use `parallel()` for CPU work
    - Buffer for batch processing
    - Cache expensive operations

3. **Monitor Metrics**
    - Subscription rates
    - Backpressure events
    - Error rates
    - Thread pool usage

Remember: **Reactive is about handling asynchronous data streams with backpressure!** 🌊