package com.tutort.assignments.Multithreading.systemdesign;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Module 14: Thread-Safe Design Patterns
 * Singleton, Producer-Consumer, Thread-Safe Cache, Rate Limiter
 */
public class Module14_DesignPatterns {

    public static void main(String[] args) throws Exception {
        demonstrateThreadSafeSingleton();
        demonstrateProducerConsumer();
        demonstrateThreadSafeCache();
        demonstrateRateLimiter();
    }

    private static void demonstrateThreadSafeSingleton() throws InterruptedException {
        System.out.println("=== Thread-Safe Singleton ===");

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                ThreadSafeSingleton instance = ThreadSafeSingleton.getInstance();
                System.out.println("Instance: " + instance.hashCode() +
                        " on " + Thread.currentThread().getName());
            });
            threads[i].start();
        }

        for (Thread t : threads) t.join();
        System.out.println();
    }

    private static void demonstrateProducerConsumer() throws InterruptedException {
        System.out.println("=== Producer-Consumer Pattern ===");

        ProducerConsumerQueue<Integer> queue = new ProducerConsumerQueue<>(3);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    queue.put(i);
                    System.out.println("Produced: " + i);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Integer item = queue.take();
                    System.out.println("Consumed: " + item);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println();
    }

    private static void demonstrateThreadSafeCache() throws InterruptedException {
        System.out.println("=== Thread-Safe Cache ===");

        ThreadSafeCache<String, String> cache = new ThreadSafeCache<>();

        Thread[] threads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                String value = cache.get("key" + (threadId % 2));
                System.out.println("Thread " + threadId + " got: " + value);
            });
            threads[i].start();
        }

        for (Thread t : threads) t.join();
        System.out.println();
    }

    private static void demonstrateRateLimiter() throws InterruptedException {
        System.out.println("=== Rate Limiter ===");

        RateLimiter rateLimiter = new RateLimiter(3, 1000); // 3 requests per second

        Thread[] threads = new Thread[6];
        for (int i = 0; i < 6; i++) {
            final int requestId = i;
            threads[i] = new Thread(() -> {
                if (rateLimiter.tryAcquire()) {
                    System.out.println("Request " + requestId + " allowed");
                } else {
                    System.out.println("Request " + requestId + " rate limited");
                }
            });
            threads[i].start();
            Thread.sleep(200);
        }

        for (Thread t : threads) t.join();
    }

    // Double-checked locking singleton
    static class ThreadSafeSingleton {
        private static volatile ThreadSafeSingleton instance;

        private ThreadSafeSingleton() {
        }

        public static ThreadSafeSingleton getInstance() {
            if (instance == null) {
                synchronized (ThreadSafeSingleton.class) {
                    if (instance == null) {
                        instance = new ThreadSafeSingleton();
                    }
                }
            }
            return instance;
        }
    }

    static class ProducerConsumerQueue<T> {
        private final Object[] buffer;
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition notEmpty = lock.newCondition();
        private final Condition notFull = lock.newCondition();
        private int count = 0, putIndex = 0, takeIndex = 0;

        public ProducerConsumerQueue(int capacity) {
            buffer = new Object[capacity];
        }

        public void put(T item) throws InterruptedException {
            lock.lock();
            try {
                while (count == buffer.length) {
                    notFull.await();
                }
                buffer[putIndex] = item;
                putIndex = (putIndex + 1) % buffer.length;
                count++;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        @SuppressWarnings("unchecked")
        public T take() throws InterruptedException {
            lock.lock();
            try {
                while (count == 0) {
                    notEmpty.await();
                }
                T item = (T) buffer[takeIndex];
                buffer[takeIndex] = null;
                takeIndex = (takeIndex + 1) % buffer.length;
                count--;
                notFull.signal();
                return item;
            } finally {
                lock.unlock();
            }
        }
    }

    static class ThreadSafeCache<K, V> {
        private final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<>();

        public V get(K key) {
            return cache.computeIfAbsent(key, k -> {
                // Simulate expensive computation
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                return (V) ("Computed value for " + k);
            });
        }
    }

    static class RateLimiter {
        private final int maxRequests;
        private final long windowMs;
        private final ConcurrentLinkedQueue<Long> requests = new ConcurrentLinkedQueue<>();

        public RateLimiter(int maxRequests, long windowMs) {
            this.maxRequests = maxRequests;
            this.windowMs = windowMs;
        }

        public boolean tryAcquire() {
            long now = System.currentTimeMillis();

            // Remove old requests outside window
            while (!requests.isEmpty() && now - requests.peek() > windowMs) {
                requests.poll();
            }

            if (requests.size() < maxRequests) {
                requests.offer(now);
                return true;
            }
            return false;
        }
    }
}