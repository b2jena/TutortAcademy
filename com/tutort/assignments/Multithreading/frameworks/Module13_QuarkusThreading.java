package com.tutort.assignments.Multithreading.frameworks;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Module 13: Quarkus Threading Model
 * Event Loop, Worker Threads, @Blocking, Mutiny Uni/Multi
 */
public class Module13_QuarkusThreading {

    public static void main(String[] args) throws Exception {
        demonstrateEventLoopModel();
        demonstrateWorkerThreads();
        demonstrateBlockingOperations();
        demonstrateMutinyPattern();
    }

    private static void demonstrateEventLoopModel() throws InterruptedException {
        System.out.println("=== Quarkus Event Loop Model ===");

        // Simulate Vert.x event loop (single-threaded)
        ExecutorService eventLoop = Executors.newSingleThreadExecutor(
                r -> new Thread(r, "vert.x-eventloop"));

        // Non-blocking operations on event loop
        for (int i = 0; i < 5; i++) {
            final int requestId = i;
            eventLoop.submit(() -> {
                System.out.println("Processing request " + requestId +
                        " on " + Thread.currentThread().getName());
                // Simulate fast, non-blocking work
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            });
        }

        Thread.sleep(200);
        eventLoop.shutdown();
        System.out.println("Event loop handles many requests efficiently\n");
    }

    private static void demonstrateWorkerThreads() throws InterruptedException {
        System.out.println("=== Worker Threads for Blocking Operations ===");

        ExecutorService eventLoop = Executors.newSingleThreadExecutor(
                r -> new Thread(r, "vert.x-eventloop"));
        ExecutorService workerPool = Executors.newFixedThreadPool(4,
                r -> new Thread(r, "vert.x-worker"));

        // Event loop delegates blocking work to worker threads
        eventLoop.submit(() -> {
            System.out.println("Event loop received blocking request");

            // Delegate to worker thread
            workerPool.submit(() -> {
                System.out.println("Worker thread handling blocking operation on " +
                        Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println("Blocking operation completed");

                // Result back to event loop
                eventLoop.submit(() ->
                        System.out.println("Event loop sending response"));
            });
        });

        Thread.sleep(1500);
        eventLoop.shutdown();
        workerPool.shutdown();
        System.out.println();
    }

    private static void demonstrateBlockingOperations() {
        System.out.println("=== @Blocking Annotation Pattern ===");

        // Simulating @Blocking method behavior
        CompletableFuture<String> blockingOperation = CompletableFuture.supplyAsync(() -> {
            System.out.println("@Blocking method on " + Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            return "Database result";
        }, ForkJoinPool.commonPool()); // Worker thread pool

        // Non-blocking continuation
        blockingOperation.thenAccept(result ->
                System.out.println("Result: " + result + " on " + Thread.currentThread().getName()));

        blockingOperation.join();
        System.out.println();
    }

    private static void demonstrateMutinyPattern() {
        System.out.println("=== Mutiny Uni/Multi Pattern ===");

        // Simulate Uni (single item)
        CompletableFuture<String> uni = CompletableFuture
                .supplyAsync(() -> "Single value")
                .thenApply(value -> "Transformed: " + value);

        // Simulate Multi (multiple items) using CompletableFuture chain
        CompletableFuture<Void> multi = CompletableFuture.runAsync(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Multi item: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });

        // Reactive composition
        CompletableFuture<String> composed = uni
                .thenCombine(multi.thenApply(v -> "Multi completed"),
                        (uniResult, multiResult) -> uniResult + " | " + multiResult);

        composed.thenAccept(result ->
                System.out.println("Composed result: " + result));

        composed.join();

        System.out.println("\nKey Quarkus Benefits:");
        System.out.println("• Event loop for high concurrency");
        System.out.println("• Worker threads for blocking operations");
        System.out.println("• Reactive programming with Mutiny");
        System.out.println("• Fast startup and low memory usage");
    }
}