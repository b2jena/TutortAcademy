package com.tutort.assignments.Multithreading.interviews;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * System Design Exercises for HLD Interviews
 * Scalable thread-safe systems
 */
public class SystemDesignExercises {

    public static void main(String[] args) throws Exception {
        demonstrateWebServer();
        demonstrateOrderProcessing();
        demonstrateDistributedCache();
    }

    // HLD Exercise 1: Multi-threaded Web Server
    private static void demonstrateWebServer() throws InterruptedException {
        System.out.println("=== Multi-threaded Web Server ===");

        WebServer server = new WebServer(4, 10);

        // Simulate incoming requests
        for (int i = 0; i < 8; i++) {
            final int requestId = i;
            new Thread(() -> server.handleRequest("Request-" + requestId)).start();
            Thread.sleep(100);
        }

        Thread.sleep(3000);
        server.shutdown();
        System.out.println();
    }

    // HLD Exercise 2: Order Processing System
    private static void demonstrateOrderProcessing() throws InterruptedException {
        System.out.println("=== Order Processing System ===");

        OrderProcessor processor = new OrderProcessor();

        // Submit orders concurrently
        for (int i = 1; i <= 5; i++) {
            final int orderId = i;
            new Thread(() -> processor.processOrder(orderId)).start();
        }

        Thread.sleep(4000);
        processor.shutdown();
        System.out.println();
    }

    // HLD Exercise 3: Distributed Cache with Consistent Hashing
    private static void demonstrateDistributedCache() throws InterruptedException {
        System.out.println("=== Distributed Cache System ===");

        DistributedCache cache = new DistributedCache(3);

        // Concurrent cache operations
        Thread[] threads = new Thread[6];
        for (int i = 0; i < 6; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                cache.put("key" + threadId, "value" + threadId);
                String value = cache.get("key" + threadId);
                System.out.println("Thread " + threadId + " got: " + value);
            });
            threads[i].start();
        }

        for (Thread t : threads) t.join();
        cache.printStats();
    }

    static class WebServer {
        private final ThreadPoolExecutor executor;
        private final AtomicLong requestCounter = new AtomicLong(0);

        public WebServer(int coreThreads, int maxThreads) {
            executor = new ThreadPoolExecutor(
                    coreThreads, maxThreads, 60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(20),
                    r -> new Thread(r, "WebServer-" + requestCounter.incrementAndGet()),
                    new ThreadPoolExecutor.CallerRunsPolicy()
            );
        }

        public void handleRequest(String request) {
            executor.submit(() -> {
                try {
                    System.out.println("Processing " + request + " on " +
                            Thread.currentThread().getName());
                    Thread.sleep(1000); // Simulate processing
                    System.out.println("Completed " + request);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        public void shutdown() {
            executor.shutdown();
            System.out.println("Server shutdown. Processed " +
                    requestCounter.get() + " requests");
        }
    }

    static class OrderProcessor {
        private final ExecutorService orderQueue = Executors.newFixedThreadPool(2);
        private final ExecutorService paymentService = Executors.newFixedThreadPool(2);
        private final ExecutorService inventoryService = Executors.newFixedThreadPool(2);
        private final ExecutorService shippingService = Executors.newSingleThreadExecutor();

        public void processOrder(int orderId) {
            CompletableFuture<Void> orderProcessing = CompletableFuture
                    .runAsync(() -> validateOrder(orderId), orderQueue)
                    .thenCompose(v -> CompletableFuture.allOf(
                            CompletableFuture.runAsync(() -> processPayment(orderId), paymentService),
                            CompletableFuture.runAsync(() -> reserveInventory(orderId), inventoryService)
                    ))
                    .thenRunAsync(() -> shipOrder(orderId), shippingService);

            orderProcessing.whenComplete((result, ex) -> {
                if (ex != null) {
                    System.out.println("Order " + orderId + " failed: " + ex.getMessage());
                } else {
                    System.out.println("Order " + orderId + " completed successfully");
                }
            });
        }

        private void validateOrder(int orderId) {
            System.out.println("Validating order " + orderId);
            sleep(300);
        }

        private void processPayment(int orderId) {
            System.out.println("Processing payment for order " + orderId);
            sleep(500);
        }

        private void reserveInventory(int orderId) {
            System.out.println("Reserving inventory for order " + orderId);
            sleep(400);
        }

        private void shipOrder(int orderId) {
            System.out.println("Shipping order " + orderId);
            sleep(200);
        }

        private void sleep(int ms) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
            }
        }

        public void shutdown() {
            orderQueue.shutdown();
            paymentService.shutdown();
            inventoryService.shutdown();
            shippingService.shutdown();
        }
    }

    static class DistributedCache {
        private final CacheNode[] nodes;
        private final int nodeCount;

        public DistributedCache(int nodeCount) {
            this.nodeCount = nodeCount;
            this.nodes = new CacheNode[nodeCount];
            for (int i = 0; i < nodeCount; i++) {
                nodes[i] = new CacheNode("Node-" + i);
            }
        }

        private int hash(String key) {
            return Math.abs(key.hashCode()) % nodeCount;
        }

        public void put(String key, String value) {
            int nodeIndex = hash(key);
            nodes[nodeIndex].put(key, value);
            System.out.println("Stored " + key + " in " + nodes[nodeIndex].name);
        }

        public String get(String key) {
            int nodeIndex = hash(key);
            return nodes[nodeIndex].get(key);
        }

        public void printStats() {
            System.out.println("\nCache Statistics:");
            for (CacheNode node : nodes) {
                System.out.println(node.name + ": " + node.size() + " items");
            }
        }

        static class CacheNode {
            private final String name;
            private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

            public CacheNode(String name) {
                this.name = name;
            }

            public void put(String key, String value) {
                cache.put(key, value);
            }

            public String get(String key) {
                return cache.get(key);
            }

            public int size() {
                return cache.size();
            }
        }
    }
}