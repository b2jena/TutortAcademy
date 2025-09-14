package com.tutort.assignments.Multithreading.frameworks;

import java.util.concurrent.*;

/**
 * Module 12: Spring Boot Threading Patterns
 *
 * @Async, TaskExecutor, @Scheduled, WebFlux
 */
public class Module12_SpringBootThreading {

    public static void main(String[] args) throws Exception {
        demonstrateAsyncPattern();
        demonstrateTaskExecutor();
        demonstrateScheduling();
        demonstrateReactivePattern();
    }

    // Simulating @Async behavior
    private static void demonstrateAsyncPattern() throws Exception {
        System.out.println("=== @Async Pattern Simulation ===");

        ExecutorService asyncExecutor = Executors.newFixedThreadPool(3);

        // Simulate async service method
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return "Async result 1";
        }, asyncExecutor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
            }
            return "Async result 2";
        }, asyncExecutor);

        // Non-blocking combination
        CompletableFuture<String> combined = future1.thenCombine(future2,
                (result1, result2) -> result1 + " + " + result2);

        System.out.println("Main thread continues...");
        System.out.println("Combined result: " + combined.get());

        asyncExecutor.shutdown();
        System.out.println();
    }

    private static void demonstrateTaskExecutor() throws InterruptedException {
        System.out.println("=== TaskExecutor Pattern ===");

        // Custom thread pool configuration
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("SpringTask-");
        executor.initialize();

        // Submit tasks
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Task " + taskId + " on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            });
        }

        Thread.sleep(3000);
        executor.shutdown();
        System.out.println();
    }

    private static void demonstrateScheduling() throws InterruptedException {
        System.out.println("=== @Scheduled Pattern ===");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // Fixed rate execution
        scheduler.scheduleAtFixedRate(() ->
                        System.out.println("Fixed rate task: " + System.currentTimeMillis()),
                0, 1, TimeUnit.SECONDS);

        // Fixed delay execution
        scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("Fixed delay task starting");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            System.out.println("Fixed delay task completed");
        }, 0, 1, TimeUnit.SECONDS);

        Thread.sleep(3500);
        scheduler.shutdown();
        System.out.println();
    }

    private static void demonstrateReactivePattern() {
        System.out.println("=== WebFlux Reactive Pattern ===");

        // Simulating reactive streams
        CompletableFuture<String> reactive1 = CompletableFuture
                .supplyAsync(() -> "Data from DB")
                .thenApply(data -> "Processed: " + data);

        CompletableFuture<String> reactive2 = CompletableFuture
                .supplyAsync(() -> "Data from API")
                .thenApply(data -> "Enriched: " + data);

        // Non-blocking composition
        CompletableFuture<String> result = reactive1
                .thenCombine(reactive2, (db, api) -> db + " | " + api)
                .thenApply(combined -> "Final: " + combined);

        result.thenAccept(System.out::println);

        // Wait for completion
        result.join();

        System.out.println("Key: Non-blocking, event-driven, backpressure handling");
    }

    // Simulating Spring's TaskExecutor
    static class ThreadPoolTaskExecutor {
        private ThreadPoolExecutor executor;
        private int corePoolSize = 1;
        private int maxPoolSize = Integer.MAX_VALUE;
        private int queueCapacity = Integer.MAX_VALUE;
        private String threadNamePrefix = "task-";

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }

        public void setThreadNamePrefix(String prefix) {
            this.threadNamePrefix = prefix;
        }

        public void initialize() {
            BlockingQueue<Runnable> queue = queueCapacity == Integer.MAX_VALUE ?
                    new LinkedBlockingQueue<>() : new ArrayBlockingQueue<>(queueCapacity);

            executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 60L,
                    TimeUnit.SECONDS, queue, r -> new Thread(r, threadNamePrefix + System.nanoTime()));
        }

        public void execute(Runnable task) {
            executor.execute(task);
        }

        public void shutdown() {
            executor.shutdown();
        }
    }
}