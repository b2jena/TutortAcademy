package com.tutort.assignments.ReactiveJava.foundations;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Part 1.1: Synchronous vs Asynchronous Communication
 * <p>
 * Demonstrates the fundamental difference between blocking and non-blocking operations
 */
public class SyncVsAsync {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Synchronous vs Asynchronous Demo ===\n");

        demonstrateSynchronous();
        demonstrateAsynchronous();
        comparePerformance();
    }

    /**
     * SYNCHRONOUS: Blocking operation - thread waits for completion
     * Like a phone call - you wait for the other person to answer
     */
    private static void demonstrateSynchronous() {
        System.out.println("--- Synchronous Execution ---");
        long start = System.currentTimeMillis();

        // Each operation blocks until complete
        String result1 = fetchDataSync("Database", 1000);
        String result2 = fetchDataSync("API", 800);
        String result3 = fetchDataSync("Cache", 200);

        long duration = System.currentTimeMillis() - start;
        System.out.println("Results: " + result1 + ", " + result2 + ", " + result3);
        System.out.println("Total time: " + duration + "ms (sequential)\n");
    }

    /**
     * ASYNCHRONOUS: Non-blocking operation - thread continues while work happens
     * Like sending emails - you send and continue with other work
     */
    private static void demonstrateAsynchronous() throws ExecutionException, InterruptedException {
        System.out.println("--- Asynchronous Execution ---");
        long start = System.currentTimeMillis();

        // All operations start immediately (non-blocking)
        CompletableFuture<String> future1 = fetchDataAsync("Database", 1000);
        CompletableFuture<String> future2 = fetchDataAsync("API", 800);
        CompletableFuture<String> future3 = fetchDataAsync("Cache", 200);

        // Combine results when all complete
        CompletableFuture<String> combined = CompletableFuture.allOf(future1, future2, future3)
                .thenApply(v -> future1.join() + ", " + future2.join() + ", " + future3.join());

        String result = combined.get(); // Wait for completion
        long duration = System.currentTimeMillis() - start;

        System.out.println("Results: " + result);
        System.out.println("Total time: " + duration + "ms (parallel)\n");
    }

    /**
     * Synchronous data fetching - blocks the calling thread
     */
    private static String fetchDataSync(String source, int delayMs) {
        System.out.println("Fetching from " + source + "...");
        try {
            Thread.sleep(delayMs); // Simulate I/O operation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Data from " + source;
    }

    /**
     * Asynchronous data fetching - returns immediately with a Future
     */
    private static CompletableFuture<String> fetchDataAsync(String source, int delayMs) {
        System.out.println("Starting async fetch from " + source + "...");
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(delayMs); // Simulate I/O operation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Data from " + source;
        });
    }

    /**
     * Performance comparison under load
     */
    private static void comparePerformance() throws Exception {
        System.out.println("--- Performance Under Load ---");

        // Simulate 5 concurrent requests synchronously
        long syncStart = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            fetchDataSync("Service", 200);
        }
        long syncTime = System.currentTimeMillis() - syncStart;

        // Simulate 5 concurrent requests asynchronously
        long asyncStart = System.currentTimeMillis();
        CompletableFuture<?>[] futures = new CompletableFuture[5];
        for (int i = 0; i < 5; i++) {
            futures[i] = fetchDataAsync("Service", 200);
        }
        CompletableFuture.allOf(futures).get();
        long asyncTime = System.currentTimeMillis() - asyncStart;

        System.out.println("Synchronous (5 requests): " + syncTime + "ms");
        System.out.println("Asynchronous (5 requests): " + asyncTime + "ms");
        System.out.println("Async is " + (syncTime / (double) asyncTime) + "x faster");
    }
}