package com.tutort.assignments.ReactiveJava.foundations;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Part 1.2: Thread-per-Request Model Problems
 * <p>
 * Demonstrates scalability issues with traditional blocking I/O model
 */
public class ThreadPerRequestProblem {

    private static final AtomicInteger activeThreads = new AtomicInteger(0);
    private static final AtomicInteger completedRequests = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        System.out.println("=== Thread-per-Request Model Problems ===\n");

        demonstrateThreadPerRequest();
        demonstrateResourceExhaustion();
        introduceNonBlockingAlternative();
    }

    /**
     * Traditional thread-per-request model
     * Each request gets its own thread that blocks during I/O
     */
    private static void demonstrateThreadPerRequest() throws InterruptedException {
        System.out.println("--- Traditional Thread-per-Request Model ---");

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();

        // Simulate 20 concurrent requests (more than thread pool size)
        for (int i = 0; i < 20; i++) {
            final int requestId = i;
            threadPool.submit(() -> handleRequestBlocking(requestId));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(30, TimeUnit.SECONDS);

        long duration = System.currentTimeMillis() - start;
        System.out.println("Completed " + completedRequests.get() + " requests in " + duration + "ms");
        System.out.println("Problem: Limited by thread pool size, threads blocked during I/O\n");

        // Reset counters
        completedRequests.set(0);
    }

    /**
     * Simulates a blocking request handler
     * Thread is blocked during entire request processing
     */
    private static void handleRequestBlocking(int requestId) {
        int currentThreads = activeThreads.incrementAndGet();
        System.out.println("Request " + requestId + " started (Active threads: " + currentThreads + ")");

        try {
            // Simulate database call (blocking I/O)
            Thread.sleep(500);

            // Simulate API call (blocking I/O)  
            Thread.sleep(300);

            // Simulate business logic (CPU work)
            Thread.sleep(100);

            completedRequests.incrementAndGet();
            System.out.println("Request " + requestId + " completed");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            activeThreads.decrementAndGet();
        }
    }

    /**
     * Demonstrates what happens when we exceed thread limits
     */
    private static void demonstrateResourceExhaustion() throws InterruptedException {
        System.out.println("--- Resource Exhaustion Demo ---");

        // Very small thread pool to show the problem
        ExecutorService limitedPool = Executors.newFixedThreadPool(2);

        System.out.println("Submitting 10 requests to 2-thread pool...");

        for (int i = 0; i < 10; i++) {
            final int requestId = i;
            limitedPool.submit(() -> {
                System.out.println("Request " + requestId + " started on " +
                        Thread.currentThread().getName());
                try {
                    Thread.sleep(2000); // Long blocking operation
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Request " + requestId + " completed");
            });
        }

        Thread.sleep(1000);
        System.out.println("Notice: Only 2 requests processing, others queued!");

        limitedPool.shutdown();
        limitedPool.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println();
    }

    /**
     * Introduction to non-blocking alternative using CompletableFuture
     */
    private static void introduceNonBlockingAlternative() throws Exception {
        System.out.println("--- Non-blocking Alternative Preview ---");

        long start = System.currentTimeMillis();

        // Create async operations that don't block threads
        CompletableFuture<?>[] futures = new CompletableFuture[20];

        for (int i = 0; i < 20; i++) {
            final int requestId = i;
            futures[i] = handleRequestNonBlocking(requestId);
        }

        // Wait for all to complete
        CompletableFuture.allOf(futures).get();

        long duration = System.currentTimeMillis() - start;
        System.out.println("Non-blocking: 20 requests completed in " + duration + "ms");
        System.out.println("Key: Threads not blocked during I/O, better resource utilization");

        System.out.println("\n=== Key Problems with Thread-per-Request ===");
        System.out.println("1. Thread creation overhead (1MB stack per thread)");
        System.out.println("2. Context switching overhead");
        System.out.println("3. Limited scalability (threads blocked during I/O)");
        System.out.println("4. Resource exhaustion under high load");
        System.out.println("5. Memory consumption grows with concurrent requests");
    }

    /**
     * Non-blocking request handler using CompletableFuture
     * Threads are not blocked during I/O operations
     */
    private static CompletableFuture<Void> handleRequestNonBlocking(int requestId) {
        return CompletableFuture
                .supplyAsync(() -> {
                    // Simulate database call (non-blocking)
                    System.out.println("Request " + requestId + " - DB call started");
                    return "DB result";
                })
                .thenCompose(dbResult -> CompletableFuture.supplyAsync(() -> {
                    // Simulate API call (non-blocking)
                    System.out.println("Request " + requestId + " - API call started");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                    }
                    return dbResult + " + API result";
                }))
                .thenApply(combinedResult -> {
                    // Business logic
                    System.out.println("Request " + requestId + " completed: " + combinedResult);
                    return null;
                });
    }
}