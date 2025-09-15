package com.tutort.assignments.ReactiveJava.project_reactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Part 3.2: Common Reactive Operators
 * <p>
 * Demonstrates key operators for data transformation and manipulation
 * Note: Simulated using CompletableFuture and Streams for educational purposes
 */
public class ReactiveOperators {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Reactive Operators ===\n");

        demonstrateTransformationOperators();
        demonstrateFilteringOperators();
        demonstrateCombinationOperators();
        demonstrateErrorHandlingOperators();
    }

    /**
     * TRANSFORMATION OPERATORS
     * Transform data as it flows through the stream
     */
    private static void demonstrateTransformationOperators() throws Exception {
        System.out.println("--- Transformation Operators ---");

        // MAP: Transform each item (1-to-1 transformation)
        System.out.println("1. MAP (1-to-1 transformation):");

        var monoString = CompletableFuture.completedFuture("hello world");
        var monoUpperCase = monoString.thenApply(String::toUpperCase);
        System.out.println("   Input: " + monoString.get());
        System.out.println("   Output: " + monoUpperCase.get());

        // Flux map example
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> doubled = numbers.stream()
                .map(n -> n * 2)
                .collect(Collectors.toList());
        System.out.println("   Flux map: " + numbers + " -> " + doubled);

        System.out.println();

        // FLATMAP: Transform each item to a stream and flatten (1-to-N transformation)
        System.out.println("2. FLATMAP (1-to-N transformation):");

        // Mono flatMap - chain async operations
        var monoUser = CompletableFuture.completedFuture("user123");
        var monoUserProfile = monoUser.thenCompose(userId ->
                fetchUserProfile(userId) // Returns another CompletableFuture
        );
        System.out.println("   User ID: " + monoUser.get());
        System.out.println("   User Profile: " + monoUserProfile.get());

        // Flux flatMap - each item becomes multiple items
        List<String> sentences = Arrays.asList("Hello World", "Reactive Programming");
        List<String> words = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .collect(Collectors.toList());
        System.out.println("   Sentences: " + sentences);
        System.out.println("   Words: " + words);

        System.out.println("\n   KEY DIFFERENCE:");
        System.out.println("   • map: item -> item (synchronous transformation)");
        System.out.println("   • flatMap: item -> Publisher<item> (async transformation)");
        System.out.println();
    }

    /**
     * FILTERING OPERATORS
     * Filter and limit data in the stream
     */
    private static void demonstrateFilteringOperators() {
        System.out.println("--- Filtering Operators ---");

        // FILTER: Keep items that match predicate
        System.out.println("1. FILTER:");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("   All numbers: " + numbers);
        System.out.println("   Even numbers: " + evenNumbers);

        // TAKE: Limit number of items
        System.out.println("\n2. TAKE (limit):");
        List<String> items = Arrays.asList("A", "B", "C", "D", "E");
        List<String> firstThree = items.stream()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("   All items: " + items);
        System.out.println("   First 3: " + firstThree);

        // SKIP: Skip first N items
        System.out.println("\n3. SKIP:");
        List<String> afterSkip = items.stream()
                .skip(2)
                .collect(Collectors.toList());
        System.out.println("   After skipping 2: " + afterSkip);

        // DISTINCT: Remove duplicates
        System.out.println("\n4. DISTINCT:");
        List<String> withDuplicates = Arrays.asList("A", "B", "A", "C", "B", "D");
        List<String> unique = withDuplicates.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("   With duplicates: " + withDuplicates);
        System.out.println("   Unique: " + unique);
        System.out.println();
    }

    /**
     * COMBINATION OPERATORS
     * Combine multiple streams
     */
    private static void demonstrateCombinationOperators() throws Exception {
        System.out.println("--- Combination Operators ---");

        // ZIP: Combine items from multiple streams pairwise
        System.out.println("1. ZIP (combine pairwise):");

        var mono1 = CompletableFuture.completedFuture("Hello");
        var mono2 = CompletableFuture.completedFuture("World");
        var zipped = mono1.thenCombine(mono2, (a, b) -> a + " " + b);

        System.out.println("   Mono1: " + mono1.get());
        System.out.println("   Mono2: " + mono2.get());
        System.out.println("   Zipped: " + zipped.get());

        // Flux zip example
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<Integer> ages = Arrays.asList(25, 30, 35);
        List<String> combined = new ArrayList<>();
        for (int i = 0; i < Math.min(names.size(), ages.size()); i++) {
            combined.add(names.get(i) + ":" + ages.get(i));
        }
        System.out.println("   Names: " + names);
        System.out.println("   Ages: " + ages);
        System.out.println("   Zipped: " + combined);

        // MERGE: Combine streams by merging emissions
        System.out.println("\n2. MERGE (interleave emissions):");

        List<String> stream1 = Arrays.asList("A1", "A2", "A3");
        List<String> stream2 = Arrays.asList("B1", "B2");
        List<String> merged = new ArrayList<>();
        merged.addAll(stream1);
        merged.addAll(stream2);

        System.out.println("   Stream1: " + stream1);
        System.out.println("   Stream2: " + stream2);
        System.out.println("   Merged: " + merged);

        System.out.println("\n   KEY DIFFERENCE:");
        System.out.println("   • zip: Waits for items from ALL sources (synchronized)");
        System.out.println("   • merge: Emits items as they arrive (asynchronous)");
        System.out.println();
    }

    /**
     * ERROR HANDLING OPERATORS
     * Handle errors gracefully in reactive streams
     */
    private static void demonstrateErrorHandlingOperators() throws Exception {
        System.out.println("--- Error Handling Operators ---");

        // onErrorReturn: Return default value on error
        System.out.println("1. onErrorReturn (fallback value):");

        var monoWithError = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) {
                throw new RuntimeException("Service unavailable");
            }
            return "Success data";
        });

        var withFallback = monoWithError.exceptionally(throwable -> "Default value");
        System.out.println("   Result: " + withFallback.get());

        // onErrorResume: Switch to alternative stream on error
        System.out.println("\n2. onErrorResume (alternative stream):");

        var primaryService = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Primary service down");
        });

        var withAlternative = primaryService.exceptionallyCompose(throwable -> {
            System.out.println("   Primary failed, trying backup...");
            return CompletableFuture.completedFuture("Backup service data");
        });

        System.out.println("   Result: " + withAlternative.get());

        // retry: Retry on error (conceptual)
        System.out.println("\n3. retry (retry on failure):");
        System.out.println("   Conceptual: Retry failed operation up to N times");
        System.out.println("   Example: retry(3) - retry up to 3 times before giving up");

        // doOnError: Side effect on error (logging, metrics)
        System.out.println("\n4. doOnError (side effects):");
        var monoWithLogging = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Something went wrong");
        }).exceptionally(throwable -> {
            System.out.println("   Logging error: " + throwable.getMessage());
            return "Recovered";
        });

        System.out.println("   Result: " + monoWithLogging.get());

        System.out.println("\n   ERROR HANDLING STRATEGIES:");
        System.out.println("   • onErrorReturn: Simple fallback value");
        System.out.println("   • onErrorResume: Switch to backup stream");
        System.out.println("   • retry: Automatic retry with backoff");
        System.out.println("   • doOnError: Logging and monitoring");
    }

    // Helper method to simulate async user profile fetch
    private static CompletableFuture<String> fetchUserProfile(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate async database call
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Profile for " + userId;
        });
    }
}