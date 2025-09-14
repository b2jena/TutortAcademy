# 🎯 Java Multithreading Interview Cheatsheet

## 🔥 FAANG Must-Know Concepts

### Thread Fundamentals

- **Process vs Thread**: Process = separate memory, Thread = shared memory
- **Thread States**: NEW → RUNNABLE → BLOCKED/WAITING/TIMED_WAITING → TERMINATED
- **Context Switching**: CPU switches between threads (overhead)

### Synchronization

```java
// synchronized method
public synchronized void method() { }

// synchronized block
synchronized(lock) { /* critical section */ }

// volatile (visibility + ordering, NOT atomicity)
private volatile boolean flag = true;
```

### Locks & Conditions

```java
ReentrantLock lock = new ReentrantLock();
Condition condition = lock.newCondition();

lock.lock();
try {
    while (!ready) condition.await();
    // work
    condition.signal();
} finally {
    lock.unlock();
}
```

### Atomic Classes (Lock-free)

```java
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet(); // Thread-safe
counter.compareAndSet(expected, update); // CAS operation
```

### Thread Pools

```java
// Fixed pool for CPU-bound tasks
ExecutorService executor = Executors.newFixedThreadPool(4);

// Cached pool for I/O-bound tasks  
ExecutorService executor = Executors.newCachedThreadPool();

// Custom pool with fine control
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    coreSize, maxSize, keepAlive, unit, workQueue, handler);
```

### Concurrency Utilities

```java
// Wait for N events
CountDownLatch latch = new CountDownLatch(3);
latch.countDown(); // Decrement
latch.await(); // Wait until 0

// Synchronization point
CyclicBarrier barrier = new CyclicBarrier(3);
barrier.await(); // Wait for all threads

// Resource pool
Semaphore semaphore = new Semaphore(2);
semaphore.acquire(); // Get permit
semaphore.release(); // Return permit

// Producer-Consumer
BlockingQueue<Item> queue = new ArrayBlockingQueue<>(10);
queue.put(item); // Blocks if full
Item item = queue.take(); // Blocks if empty
```

## 🎯 Classic Interview Problems

### 1. Odd-Even Printing

```java
class OddEvenPrinter {
    private int current = 1;
    private final Object lock = new Object();
    
    public void printOdd() throws InterruptedException {
        synchronized (lock) {
            while (current <= max) {
                while (current % 2 == 0) lock.wait();
                if (current <= max) {
                    System.out.print(current + " ");
                    current++;
                    lock.notify();
                }
            }
        }
    }
}
```

### 2. Producer-Consumer

```java
class BoundedBuffer<T> {
    private final Object[] buffer;
    private int count = 0, putIndex = 0, takeIndex = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    
    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            while (count == buffer.length) notFull.await();
            buffer[putIndex] = item;
            putIndex = (putIndex + 1) % buffer.length;
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
}
```

### 3. Thread-Safe Singleton

```java
class Singleton {
    private static volatile Singleton instance;
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

## 🚀 Performance Tips

### Thread Pool Sizing

- **CPU-bound**: Number of cores
- **I/O-bound**: Cores × (1 + wait_time/service_time)
- **Mixed workload**: Profile and test

### Memory Model

- **volatile**: Visibility guarantee, prevents reordering
- **synchronized**: Visibility + atomicity + ordering
- **Atomic classes**: Lock-free, CAS-based

### ConcurrentHashMap vs HashMap

- **HashMap**: Not thread-safe, fast single-threaded
- **ConcurrentHashMap**: Thread-safe, lock striping, weakly consistent iterators
- **Collections.synchronizedMap()**: Synchronized wrapper, slower

## 🎯 System Design Questions

### "Design a thread-safe cache"

```java
class ThreadSafeCache<K, V> {
    private final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<>();
    
    public V get(K key) {
        return cache.computeIfAbsent(key, this::expensiveComputation);
    }
}
```

### "Design a rate limiter"

```java
class RateLimiter {
    private final Semaphore semaphore;
    private final ScheduledExecutorService scheduler;
    
    public RateLimiter(int permits, long period, TimeUnit unit) {
        semaphore = new Semaphore(permits);
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> 
            semaphore.release(permits - semaphore.availablePermits()), 
            period, period, unit);
    }
    
    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }
}
```

## 🔥 Interview Red Flags to Avoid

❌ **Don't say**: "I'll use synchronized everywhere for thread safety"
✅ **Say**: "I'll analyze the specific concurrency requirements and choose appropriate mechanisms"

❌ **Don't**: Ignore deadlock prevention
✅ **Do**: Always acquire locks in consistent order

❌ **Don't**: Use busy waiting
✅ **Do**: Use proper blocking mechanisms (wait/notify, BlockingQueue)

❌ **Don't**: Create threads manually for everything
✅ **Do**: Use thread pools and ExecutorService

## 🎯 Framework-Specific Knowledge

### Spring Boot

- `@Async` for asynchronous method execution
- `TaskExecutor` for custom thread pools
- `@Scheduled` for periodic tasks
- WebFlux for reactive programming

### Quarkus

- Event loop model (Vert.x based)
- `@Blocking` for blocking operations
- Mutiny for reactive streams
- Worker threads for CPU-intensive tasks

## 🚀 Advanced Topics for Senior Roles

- **Lock-free programming**: CAS, atomic references
- **Memory barriers**: Happens-before relationships
- **Thread local storage**: ThreadLocal variables
- **Fork-Join framework**: Work-stealing algorithms
- **Reactive streams**: Backpressure handling
- **Virtual threads** (Java 19+): Lightweight concurrency

Remember: **Understand the problem → Choose right tool → Implement correctly → Handle edge cases**