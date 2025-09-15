package com.tutort.assignments.ReactiveJava.advanced;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Part 4.1: Schedulers - Managing Thread Execution
 * <p>
 * Schedulers control WHERE and WHEN reactive operations execute
 * Key concepts: subscribeOn vs publishOn
 */
public class ReactiveSchedulers {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Reactive Schedulers ===\n");

        explainSchedulerTypes();
        demonstrateSubscribeOn();
        demonstratePublishOn();
        demonstrateSchedulerChaining();
        bestPractices();
    }

    /**
     * Explain different scheduler types and their use cases
     */
    private static void explainSchedulerTypes() {
        System.out.println("--- Scheduler Types ---");

        System.out.println("1. Schedulers.single():");
        System.out.println("   • Single background thread");
        System.out.println("   • Use for: Lightweight, sequential tasks");
        System.out.println("   • Example: Logging, metrics");

        System.out.println("\n2. Schedulers.elastic():");
        System.out.println("   • Unbounded thread pool (creates threads as needed)");
        System.out.println("   • Use for: I/O operations, blocking calls");
        System.out.println("   • Example: Database calls, HTTP requests");

        System.out.println("\n3. Schedulers.parallel():");
        System.out.println("   • Fixed thread pool (CPU cores count)");
        System.out.println("   • Use for: CPU-intensive computations");
        System.out.println("   • Example: Mathematical calculations, data processing");

        System.out.println("\n4. Schedulers.boundedElastic():");
        System.out.println("   • Bounded thread pool with queue");
        System.out.println("   • Use for: Blocking I/O with backpressure");
        System.out.println("   • Example: File operations, legacy API calls");

        System.out.println();
    }

    /**
     * subscribeOn: Controls WHERE the subscription happens
     * Affects the entire chain from source
     */
    private static void demonstrateSubscribeOn() throws InterruptedException {
        System.out.println("--- subscribeOn (WHERE subscription happens) ---");

        // Simulate different schedulers
        ExecutorService singleScheduler = Executors.newSingleThreadExecutor(
                r -> new Thread(r, "single-scheduler"));
        ExecutorService elasticScheduler = Executors.newCachedThreadPool(
                r -> new Thread(r, "elastic-" + System.nanoTime()));

        System.out.println("Without subscribeOn (main thread):");
        CompletableFuture<String> mainThread = CompletableFuture.supplyAsync(() -> {
            System.out.println("  Source on: " + Thread.currentThread().getName());
            return "Data from main";
        });
        System.out.println("  Result: " + mainThread.get());

        System.out.println("\nWith subscribeOn (single scheduler):");
        CompletableFuture<String> singleThread = CompletableFuture.supplyAsync(() -> {
            System.out.println("  Source on: " + Thread.currentThread().getName());
            return "Data from single scheduler";
        }, singleScheduler);
        System.out.println("  Result: " + singleThread.get());

        System.out.println("\nWith subscribeOn (elastic scheduler):");
        CompletableFuture<String> elasticThread = CompletableFuture.supplyAsync(() -> {
            System.out.println("  Source on: " + Thread.currentThread().getName());
            return "Data from elastic scheduler";
        }, elasticScheduler);
        System.out.println("  Result: " + elasticThread.get());

        System.out.println("\nKey Point: subscribeOn affects the ENTIRE upstream chain");

        singleScheduler.shutdown();
        elasticScheduler.shutdown();
        System.out.println();
    }

    /**
     * publishOn: Controls WHERE downstream operations happen
     * Affects operations AFTER the publishOn call
     */
    private static void demonstratePublishOn() throws InterruptedException {
        System.out.println("--- publishOn (WHERE downstream operations happen) ---");

        ExecutorService sourceScheduler = Executors.newSingleThreadExecutor(
                r -> new Thread(r, "source-thread"));
        ExecutorService processingScheduler = Executors.newSingleThreadExecutor(
                r -> new Thread(r, "processing-thread"));

        System.out.println("Demonstrating publishOn effect:");

        CompletableFuture<String> result = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("  1. Source on: " + Thread.currentThread().getName());
                    return "raw-data";
                }, sourceScheduler)
                .thenApplyAsync(data -> {
                    System.out.println("  2. Transform on: " + Thread.currentThread().getName());
                    return data.toUpperCase();
                }, processingScheduler) // This simulates publishOn
                .thenApply(data -> {
                    System.out.println("  3. Final step on: " + Thread.currentThread().getName());
                    return "Processed: " + data;
                });

        System.out.println("  Result: " + result.get());

        System.out.println("\nKey Point: publishOn affects operations AFTER it in the chain");

        sourceScheduler.shutdown();
        processingScheduler.shutdown();
        System.out.println();
    }

    /**
     * Demonstrates complex scheduler chaining
     */
    private static void demonstrateSchedulerChaining() throws Exception {
        System.out.println("--- Scheduler Chaining ---");

        ExecutorService ioScheduler = Executors.newCachedThreadPool(
                r -> new Thread(r, "io-thread"));
        ExecutorService cpuScheduler = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                r -> new Thread(r, "cpu-thread"));
        ExecutorService dbScheduler = Executors.newFixedThreadPool(2,
                r -> new Thread(r, "db-thread"));

        System.out.println("Complex processing pipeline:");

        CompletableFuture<String> pipeline = CompletableFuture
                // 1. Fetch data from API (I/O operation)
                .supplyAsync(() -> {
                    System.out.println("  1. API call on: " + Thread.currentThread().getName());
                    simulateDelay(200);
                    return "api-data";
                }, ioScheduler)

                // 2. Process data (CPU-intensive)
                .thenApplyAsync(data -> {
                    System.out.println("  2. CPU processing on: " + Thread.currentThread().getName());
                    simulateDelay(100);
                    return "processed-" + data;
                }, cpuScheduler)

                // 3. Save to database (I/O operation)
                .thenApplyAsync(processedData -> {
                    System.out.println("  3. Database save on: " + Thread.currentThread().getName());
                    simulateDelay(150);
                    return "saved-" + processedData;
                }, dbScheduler)

                // 4. Return to main thread for response
                .thenApply(savedData -> {
                    System.out.println("  4. Response on: " + Thread.currentThread().getName());
                    return "Response: " + savedData;
                });

        System.out.println("  Final result: " + pipeline.get());

        ioScheduler.shutdown();
        cpuScheduler.shutdown();
        dbScheduler.shutdown();
        System.out.println();
    }

    /**
     * Best practices for scheduler usage
     */
    private static void bestPractices() {
        System.out.println("--- Scheduler Best Practices ---");

        System.out.println("1. CHOOSE RIGHT SCHEDULER:");
        System.out.println("   • I/O operations → boundedElastic()");
        System.out.println("   • CPU computations → parallel()");
        System.out.println("   • Sequential tasks → single()");
        System.out.println("   • Legacy blocking code → boundedElastic()");

        System.out.println("\n2. AVOID BLOCKING ON EVENT LOOP:");
        System.out.println("   ❌ Don't block reactive threads");
        System.out.println("   ✅ Use subscribeOn(boundedElastic()) for blocking calls");

        System.out.println("\n3. UNDERSTAND subscribeOn vs publishOn:");
        System.out.println("   • subscribeOn: WHERE subscription starts (affects upstream)");
        System.out.println("   • publishOn: WHERE processing continues (affects downstream)");

        System.out.println("\n4. SCHEDULER CHAINING:");
        System.out.println("   • Use different schedulers for different operation types");
        System.out.println("   • Switch contexts with publishOn when needed");
        System.out.println("   • Keep I/O and CPU work on appropriate schedulers");

        System.out.println("\n5. RESOURCE MANAGEMENT:");
        System.out.println("   • Reuse schedulers when possible");
        System.out.println("   • Shutdown custom schedulers properly");
        System.out.println("   • Monitor thread pool metrics");

        System.out.println("\nCOMMON PATTERNS:");
        System.out.println("// I/O operation");
        System.out.println("mono.subscribeOn(Schedulers.boundedElastic())");
        System.out.println();
        System.out.println("// CPU computation");
        System.out.println("flux.parallel().runOn(Schedulers.parallel())");
        System.out.println();
        System.out.println("// Context switching");
        System.out.println("mono.subscribeOn(Schedulers.boundedElastic())");
        System.out.println("    .publishOn(Schedulers.parallel())");
    }

    private static void simulateDelay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}