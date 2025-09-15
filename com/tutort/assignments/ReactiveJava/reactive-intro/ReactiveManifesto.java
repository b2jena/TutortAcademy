package com.tutort.assignments.ReactiveJava.reactive_intro;

import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Part 2.1: Reactive Manifesto & Core Principles
 * <p>
 * The Four Pillars of Reactive Systems:
 * 1. RESPONSIVE: React to users quickly
 * 2. RESILIENT: React to failure gracefully
 * 3. ELASTIC: React to load by scaling
 * 4. MESSAGE-DRIVEN: React to events asynchronously
 */
public class ReactiveManifesto {

    public static void main(String[] args) throws Exception {
        System.out.println("=== The Reactive Manifesto ===\n");

        demonstrateResponsive();
        demonstrateResilient();
        demonstrateElastic();
        demonstrateMessageDriven();
        introducePublisherSubscriber();
    }

    /**
     * RESPONSIVE: System responds in a timely manner
     * Non-blocking operations ensure quick response times
     */
    private static void demonstrateResponsive() throws Exception {
        System.out.println("--- 1. RESPONSIVE ---");
        System.out.println("Goal: Respond quickly to user requests\n");

        // Non-responsive (blocking)
        long start = System.currentTimeMillis();
        blockingOperation();
        System.out.println("Blocking response time: " + (System.currentTimeMillis() - start) + "ms");

        // Responsive (non-blocking)
        start = System.currentTimeMillis();
        CompletableFuture<String> future = nonBlockingOperation();
        System.out.println("Non-blocking response time: " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("Result: " + future.get());
        System.out.println();
    }

    /**
     * RESILIENT: System stays responsive in face of failure
     * Failures are contained and handled gracefully
     */
    private static void demonstrateResilient() {
        System.out.println("--- 2. RESILIENT ---");
        System.out.println("Goal: Handle failures gracefully without cascading\n");

        // Resilient system with fallback
        CompletableFuture<String> resilientCall = CompletableFuture
                .supplyAsync(() -> {
                    if (Math.random() > 0.5) {
                        throw new RuntimeException("Service unavailable");
                    }
                    return "Success from primary service";
                })
                .exceptionally(throwable -> {
                    System.out.println("Primary failed: " + throwable.getMessage());
                    return "Fallback response"; // Graceful degradation
                });

        System.out.println("Result: " + resilientCall.join());
        System.out.println("System remained responsive despite failure\n");
    }

    /**
     * ELASTIC: System scales up/down based on demand
     * Resources are allocated dynamically
     */
    private static void demonstrateElastic() throws InterruptedException {
        System.out.println("--- 3. ELASTIC ---");
        System.out.println("Goal: Scale resources based on load\n");

        // Simulate elastic thread pool that grows with demand
        ThreadPoolExecutor elasticPool = new ThreadPoolExecutor(
                2,                              // Core threads (minimum)
                10,                             // Max threads (scales up)
                60L, TimeUnit.SECONDS,          // Keep alive time
                new LinkedBlockingQueue<>(5),   // Bounded queue
                r -> new Thread(r, "Elastic-" + System.nanoTime())
        );

        // Low load - uses core threads
        System.out.println("Low load (2 tasks):");
        for (int i = 0; i < 2; i++) {
            elasticPool.submit(() -> {
                System.out.println("Task on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            });
        }
        Thread.sleep(100);
        System.out.println("Pool size: " + elasticPool.getPoolSize());

        // High load - scales up
        System.out.println("\nHigh load (10 tasks):");
        for (int i = 0; i < 10; i++) {
            elasticPool.submit(() -> {
                System.out.println("Task on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            });
        }
        Thread.sleep(100);
        System.out.println("Pool size after scaling: " + elasticPool.getPoolSize());

        elasticPool.shutdown();
        elasticPool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println();
    }

    /**
     * MESSAGE-DRIVEN: Components communicate via asynchronous messages
     * Loose coupling through message passing
     */
    private static void demonstrateMessageDriven() throws InterruptedException {
        System.out.println("--- 4. MESSAGE-DRIVEN ---");
        System.out.println("Goal: Asynchronous, non-blocking communication\n");

        // Message-driven communication using queues
        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

        // Producer (sends messages asynchronously)
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String message = "Message " + i;
                    messageQueue.put(message);
                    System.out.println("Sent: " + message);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer (processes messages asynchronously)
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String message = messageQueue.take();
                    System.out.println("Processed: " + message);
                    Thread.sleep(300); // Simulate processing
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("Components communicated without blocking each other\n");
    }

    /**
     * Publisher-Subscriber Pattern Introduction
     * Foundation of reactive streams
     */
    private static void introducePublisherSubscriber() {
        System.out.println("--- Publisher-Subscriber Pattern ---");
        System.out.println("Foundation of Reactive Streams\n");

        // Simple publisher-subscriber implementation
        SimplePublisher<String> publisher = new SimplePublisher<>();

        // Subscribe multiple consumers
        publisher.subscribe(data -> System.out.println("Subscriber 1: " + data));
        publisher.subscribe(data -> System.out.println("Subscriber 2: " + data));

        // Publish data
        publisher.publish("Hello Reactive World!");
        publisher.publish("Data flows asynchronously");

        System.out.println("\nKey Concepts:");
        System.out.println("• Publisher produces data stream");
        System.out.println("• Subscribers consume data asynchronously");
        System.out.println("• Backpressure prevents overwhelming consumers");
    }

    // Helper methods
    private static String blockingOperation() {
        try {
            Thread.sleep(1000); // Simulate blocking I/O
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Blocking result";
    }

    private static CompletableFuture<String> nonBlockingOperation() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000); // I/O happens on different thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Non-blocking result";
        });
    }

    /**
     * Simple Publisher implementation for demonstration
     */
    static class SimplePublisher<T> {
        private final java.util.List<Consumer<T>> subscribers = new java.util.ArrayList<>();

        public void subscribe(Consumer<T> subscriber) {
            subscribers.add(subscriber);
        }

        public void publish(T data) {
            subscribers.forEach(subscriber -> subscriber.accept(data));
        }
    }
}