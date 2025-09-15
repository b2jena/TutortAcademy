package com.tutort.assignments.ReactiveJava.advanced;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Part 4.3: Integrating with Blocking APIs
 * <p>
 * Challenge: Mix reactive and blocking code without blocking event loop
 * Solution: Proper scheduler usage and wrapping techniques
 */
public class BlockingIntegration {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Integrating with Blocking APIs ===\n");

        explainTheProblem();
        demonstrateWrongWay();
        demonstrateCorrectWay();
        realWorldExamples();
    }

    /**
     * Explain why mixing blocking and reactive code is problematic
     */
    private static void explainTheProblem() {
        System.out.println("--- The Problem with Blocking Code ---");

        System.out.println("REACTIVE PRINCIPLE: Non-blocking, asynchronous");
        System.out.println("LEGACY APIs: Blocking, synchronous");

        System.out.println("\nPROBLEMS:");
        System.out.println("• Blocking calls freeze event loop threads");
        System.out.println("• Reduces concurrency and throughput");
        System.out.println("• Can cause thread starvation");
        System.out.println("• Defeats reactive benefits");

        System.out.println("\nCOMMON BLOCKING APIS:");
        System.out.println("• JDBC database calls");
        System.out.println("• File I/O operations");
        System.out.println("• Legacy HTTP clients");
        System.out.println("• Synchronous message queues");
        System.out.println();
    }

    /**
     * Show the wrong way - blocking on reactive threads
     */
    private static void demonstrateWrongWay() throws InterruptedException {
        System.out.println("--- WRONG WAY: Blocking on Reactive Thread ---");

        ExecutorService reactiveEventLoop = Executors.newSingleThreadExecutor(
                r -> new Thread(r, "reactive-event-loop"));

        long start = System.currentTimeMillis();

        // This blocks the reactive thread - BAD!
        CompletableFuture<String> badExample = CompletableFuture.supplyAsync(() -> {
            System.out.println("❌ Blocking call on: " + Thread.currentThread().getName());

            // Simulate blocking database call
            try {
                Thread.sleep(1000); // This blocks the reactive thread!
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return "Data from blocking call";
        }, reactiveEventLoop);

        System.out.println("Result: " + badExample.get());
        System.out.println("Time taken: " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("Problem: Event loop thread was blocked for 1 second!");

        reactiveEventLoop.shutdown();
        System.out.println();
    }

    /**
     * Show the correct way - using appropriate schedulers
     */
    private static void demonstrateCorrectWay() throws Exception {
        System.out.println("--- CORRECT WAY: Proper Scheduler Usage ---");

        ExecutorService reactiveEventLoop = Executors.newSingleThreadExecutor(
                r -> new Thread(r, "reactive-event-loop"));
        ExecutorService blockingScheduler = Executors.newCachedThreadPool(
                r -> new Thread(r, "blocking-io"));

        long start = System.currentTimeMillis();

        // Wrap blocking call properly
        CompletableFuture<String> goodExample = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("✅ Starting on: " + Thread.currentThread().getName());
                    return "request-data";
                }, reactiveEventLoop)
                .thenCompose(requestData ->
                        // Move blocking operation to appropriate scheduler
                        CompletableFuture.supplyAsync(() -> {
                            System.out.println("✅ Blocking call on: " + Thread.currentThread().getName());

                            // Simulate blocking database call
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }

                            return "Data for " + requestData;
                        }, blockingScheduler)
                )
                .thenApply(result -> {
                    System.out.println("✅ Processing result on: " + Thread.currentThread().getName());
                    return "Processed: " + result;
                });

        System.out.println("Result: " + goodExample.get());
        System.out.println("Time taken: " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("Success: Event loop remained free during blocking operation!");

        reactiveEventLoop.shutdown();
        blockingScheduler.shutdown();
        System.out.println();
    }

    /**
     * Real-world examples of wrapping blocking APIs
     */
    private static void realWorldExamples() throws Exception {
        System.out.println("--- Real-World Examples ---");

        // Example 1: Database operations
        demonstrateDatabaseIntegration();

        // Example 2: File operations
        demonstrateFileIntegration();

        // Example 3: Legacy HTTP client
        demonstrateHttpIntegration();
    }

    /**
     * Example: Wrapping JDBC database calls
     */
    private static void demonstrateDatabaseIntegration() throws Exception {
        System.out.println("1. Database Integration (JDBC):");

        ExecutorService dbScheduler = Executors.newFixedThreadPool(5,
                r -> new Thread(r, "db-pool"));

        // Simulate reactive database service
        CompletableFuture<User> userFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("   DB query on: " + Thread.currentThread().getName());

                    // Simulate JDBC call (blocking)
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    return new User(1, "Alice", "alice@example.com");
                }, dbScheduler);

        User user = userFuture.get();
        System.out.println("   Retrieved user: " + user);

        dbScheduler.shutdown();
        System.out.println();
    }

    /**
     * Example: Wrapping file I/O operations
     */
    private static void demonstrateFileIntegration() throws Exception {
        System.out.println("2. File I/O Integration:");

        ExecutorService fileScheduler = Executors.newCachedThreadPool(
                r -> new Thread(r, "file-io"));

        CompletableFuture<String> fileContent = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("   File read on: " + Thread.currentThread().getName());

                    // Simulate file reading (blocking)
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    return "File content from disk";
                }, fileScheduler);

        System.out.println("   File content: " + fileContent.get());

        fileScheduler.shutdown();
        System.out.println();
    }

    /**
     * Example: Wrapping legacy HTTP client
     */
    private static void demonstrateHttpIntegration() throws Exception {
        System.out.println("3. Legacy HTTP Client Integration:");

        ExecutorService httpScheduler = Executors.newCachedThreadPool(
                r -> new Thread(r, "http-client"));

        CompletableFuture<String> httpResponse = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("   HTTP call on: " + Thread.currentThread().getName());

                    // Simulate blocking HTTP call
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    return "{\"status\": \"success\", \"data\": \"API response\"}";
                }, httpScheduler)
                .thenApply(response -> {
                    System.out.println("   Processing response on: " + Thread.currentThread().getName());
                    return "Parsed: " + response;
                });

        System.out.println("   HTTP result: " + httpResponse.get());

        httpScheduler.shutdown();

        System.out.println("\n=== Best Practices Summary ===");
        System.out.println("1. NEVER block reactive threads");
        System.out.println("2. Use boundedElastic() scheduler for blocking I/O");
        System.out.println("3. Wrap blocking calls with Mono.fromCallable()");
        System.out.println("4. Use subscribeOn() to move to blocking scheduler");
        System.out.println("5. Keep reactive and blocking concerns separated");

        System.out.println("\nCODE PATTERN:");
        System.out.println("Mono.fromCallable(() -> blockingCall())");
        System.out.println("    .subscribeOn(Schedulers.boundedElastic())");
        System.out.println("    .map(result -> processResult(result))");
    }

    // Helper classes
    static class User {
        final int id;
        final String name;
        final String email;

        User(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        @Override
        public String toString() {
            return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
        }
    }
}