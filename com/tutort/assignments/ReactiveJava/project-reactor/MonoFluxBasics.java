package com.tutort.assignments.ReactiveJava.project_reactor;

import java.util.Arrays;
import java.util.List;

/**
 * Part 3.1: Mono & Flux - Core Building Blocks
 * <p>
 * MONO: Represents 0 or 1 item (like Optional but async)
 * FLUX: Represents 0 to N items (like Stream but async)
 * <p>
 * Note: This is a simulation of Project Reactor concepts using standard Java
 * In real projects, you'd use: implementation 'io.projectreactor:reactor-core'
 */
public class MonoFluxBasics {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Mono & Flux Fundamentals ===\n");

        demonstrateMonoBasics();
        demonstrateFluxBasics();
        demonstrateCreationMethods();
        demonstrateSubscription();
        compareWithTraditionalApproaches();
    }

    /**
     * MONO: Asynchronous 0-1 items
     * Think of it as CompletableFuture<Optional<T>>
     */
    private static void demonstrateMonoBasics() {
        System.out.println("--- MONO: 0 or 1 Item ---");

        // Mono with value (simulated using CompletableFuture)
        var monoWithValue = java.util.concurrent.CompletableFuture
                .completedFuture("Hello Mono!");

        // Mono empty (simulated)
        var monoEmpty = java.util.concurrent.CompletableFuture
                .completedFuture((String) null);

        // Mono with error (simulated)
        var monoWithError = java.util.concurrent.CompletableFuture
                .<String>failedFuture(new RuntimeException("Something went wrong"));

        System.out.println("Mono with value: " + monoWithValue.join());
        System.out.println("Mono empty: " + monoEmpty.join());
        System.out.println("Mono with error: " +
                (monoWithError.isCompletedExceptionally() ? "Error occurred" : "Success"));

        System.out.println("\nMono Use Cases:");
        System.out.println("• HTTP requests (single response)");
        System.out.println("• Database save operations");
        System.out.println("• Cache lookups");
        System.out.println();
    }

    /**
     * FLUX: Asynchronous 0-N items
     * Think of it as reactive Stream<T>
     */
    private static void demonstrateFluxBasics() {
        System.out.println("--- FLUX: 0 to N Items ---");

        // Flux with multiple values (simulated using Stream)
        List<String> fluxData = Arrays.asList("Item 1", "Item 2", "Item 3");
        System.out.println("Flux with values: " + fluxData);

        // Flux empty
        List<String> fluxEmpty = Arrays.asList();
        System.out.println("Flux empty: " + fluxEmpty);

        // Flux infinite (conceptual)
        System.out.println("Flux infinite: [1, 2, 3, 4, 5, ...] (conceptual)");

        System.out.println("\nFlux Use Cases:");
        System.out.println("• Real-time data streams");
        System.out.println("• Database query results");
        System.out.println("• Event streams");
        System.out.println("• File processing");
        System.out.println();
    }

    /**
     * Different ways to create Mono and Flux
     */
    private static void demonstrateCreationMethods() {
        System.out.println("--- Creation Methods ---");

        // MONO Creation Methods (simulated)
        System.out.println("Mono Creation:");

        // Mono.just() - with value
        var monoJust = java.util.concurrent.CompletableFuture.completedFuture("Direct value");
        System.out.println("• Mono.just(): " + monoJust.join());

        // Mono.empty() - no value
        var monoEmpty = java.util.concurrent.CompletableFuture.completedFuture((String) null);
        System.out.println("• Mono.empty(): " + (monoEmpty.join() == null ? "empty" : monoEmpty.join()));

        // Mono.error() - with error
        var monoError = java.util.concurrent.CompletableFuture
                .<String>failedFuture(new IllegalArgumentException("Invalid input"));
        System.out.println("• Mono.error(): " +
                (monoError.isCompletedExceptionally() ? "Error state" : "Success"));

        // Mono.fromCallable() - lazy evaluation
        var monoCallable = java.util.concurrent.CompletableFuture.supplyAsync(() -> {
            System.out.println("  Executing expensive operation...");
            return "Computed value";
        });
        System.out.println("• Mono.fromCallable(): " + monoCallable.join());

        System.out.println("\nFlux Creation:");

        // Flux.just() - with values
        var fluxJust = Arrays.asList("A", "B", "C");
        System.out.println("• Flux.just(): " + fluxJust);

        // Flux.fromIterable() - from collection
        var fluxFromIterable = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("• Flux.fromIterable(): " + fluxFromIterable);

        // Flux.range() - sequence of numbers
        var fluxRange = java.util.stream.IntStream.range(1, 6).boxed().toList();
        System.out.println("• Flux.range(1, 5): " + fluxRange);

        // Flux.interval() - periodic emissions (conceptual)
        System.out.println("• Flux.interval(): Emits every 1 second (conceptual)");

        System.out.println();
    }

    /**
     * Subscription and consumption patterns
     */
    private static void demonstrateSubscription() throws InterruptedException {
        System.out.println("--- Subscription Patterns ---");

        // Simple subscription (simulated)
        System.out.println("Simple subscription:");
        var mono = java.util.concurrent.CompletableFuture.completedFuture("Hello World");
        mono.thenAccept(value -> System.out.println("Received: " + value));

        // Subscription with error handling
        System.out.println("\nWith error handling:");
        var monoWithPossibleError = java.util.concurrent.CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) {
                throw new RuntimeException("Random error");
            }
            return "Success!";
        });

        monoWithPossibleError
                .thenAccept(value -> System.out.println("Success: " + value))
                .exceptionally(throwable -> {
                    System.out.println("Error handled: " + throwable.getCause().getMessage());
                    return null;
                });

        Thread.sleep(100); // Wait for async completion

        // Flux subscription (simulated)
        System.out.println("\nFlux subscription:");
        var flux = Arrays.asList("First", "Second", "Third");
        flux.forEach(item -> System.out.println("Processing: " + item));

        System.out.println();
    }

    /**
     * Compare reactive approach with traditional approaches
     */
    private static void compareWithTraditionalApproaches() {
        System.out.println("--- Reactive vs Traditional ---");

        System.out.println("TRADITIONAL (Blocking):");
        System.out.println("String result = service.getData();");
        System.out.println("• Thread blocks until data arrives");
        System.out.println("• Synchronous execution");
        System.out.println("• Limited scalability");

        System.out.println("\nREACTIVE (Non-blocking):");
        System.out.println("Mono<String> result = service.getDataAsync();");
        System.out.println("• Thread continues immediately");
        System.out.println("• Asynchronous execution");
        System.out.println("• Better resource utilization");

        System.out.println("\nKEY DIFFERENCES:");
        System.out.println("• Lazy evaluation (nothing happens until subscription)");
        System.out.println("• Composable operations (chain transformations)");
        System.out.println("• Built-in error handling");
        System.out.println("• Backpressure support");
        System.out.println("• Event-driven processing");

        System.out.println("\nWHEN TO USE:");
        System.out.println("✅ High-concurrency applications");
        System.out.println("✅ I/O intensive operations");
        System.out.println("✅ Real-time data processing");
        System.out.println("✅ Microservices communication");
        System.out.println("❌ Simple CRUD operations");
        System.out.println("❌ CPU-intensive computations");
    }
}