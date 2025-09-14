package com.tutort.assignments.Multithreading.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Module 7: ExecutorService and Thread Pools
 * <p>
 * Thread Pool Benefits:
 * - Reuse threads (avoid creation overhead)
 * - Control concurrency level
 * - Better resource management
 */
public class Module7_ExecutorService {

    public static void main(String[] args) throws Exception {
        System.out.println("=== ExecutorService and Thread Pools ===");

        demonstrateThreadPoolTypes();
        demonstrateCallableAndFuture();
        demonstrateCompletionService();
        demonstrateThreadPoolTuning();
    }

    private static void demonstrateThreadPoolTypes() throws InterruptedException {
        System.out.println("\n--- Thread Pool Types ---");

        // 1. Fixed Thread Pool
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        System.out.println("Fixed pool (3 threads):");
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            fixedPool.submit(() -> {
                System.out.println("Fixed pool task " + taskId + " on " +
                        Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            });
        }
        fixedPool.shutdown();
        fixedPool.awaitTermination(5, TimeUnit.SECONDS);

        // 2. Cached Thread Pool
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        System.out.println("\nCached pool (creates threads as needed):");
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            cachedPool.submit(() -> {
                System.out.println("Cached pool task " + taskId + " on " +
                        Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            });
        }
        cachedPool.shutdown();
        cachedPool.awaitTermination(3, TimeUnit.SECONDS);

        // 3. Single Thread Executor
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        System.out.println("\nSingle thread executor:");
        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            singlePool.submit(() -> {
                System.out.println("Single pool task " + taskId + " on " +
                        Thread.currentThread().getName());
            });
        }
        singlePool.shutdown();
        singlePool.awaitTermination(2, TimeUnit.SECONDS);
    }

    private static void demonstrateCallableAndFuture() throws Exception {
        System.out.println("\n--- Callable and Future ---");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit Callable tasks that return values
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            final int num = i;
            Future<Integer> future = executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(1000); // Simulate work
                    int result = num * num;
                    System.out.println("Computed " + num + "^2 = " + result);
                    return result;
                }
            });
            futures.add(future);
        }

        // Collect results
        System.out.println("Collecting results:");
        int sum = 0;
        for (Future<Integer> future : futures) {
            sum += future.get(); // Blocking call
        }
        System.out.println("Sum of squares: " + sum);

        // Demonstrate timeout
        Future<String> timeoutFuture = executor.submit(() -> {
            Thread.sleep(3000);
            return "This will timeout";
        });

        try {
            String result = timeoutFuture.get(1, TimeUnit.SECONDS);
            System.out.println("Result: " + result);
        } catch (TimeoutException e) {
            System.out.println("Task timed out!");
            timeoutFuture.cancel(true); // Interrupt the task
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    private static void demonstrateCompletionService() throws Exception {
        System.out.println("\n--- CompletionService (Process as completed) ---");

        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletionService<String> completionService =
                new ExecutorCompletionService<>(executor);

        // Submit tasks with different completion times
        int[] delays = {3000, 1000, 2000, 500, 1500};
        for (int i = 0; i < delays.length; i++) {
            final int taskId = i;
            final int delay = delays[i];

            completionService.submit(() -> {
                Thread.sleep(delay);
                return "Task " + taskId + " completed (delay: " + delay + "ms)";
            });
        }

        // Process results as they complete (not in submission order)
        for (int i = 0; i < delays.length; i++) {
            Future<String> future = completionService.take(); // Blocking
            System.out.println("Completed: " + future.get());
        }

        executor.shutdown();
    }

    private static void demonstrateThreadPoolTuning() throws InterruptedException {
        System.out.println("\n--- Thread Pool Tuning ---");

        // Custom ThreadPoolExecutor for fine control
        ThreadPoolExecutor customPool = new ThreadPoolExecutor(
                2,                              // corePoolSize
                4,                              // maximumPoolSize
                60L, TimeUnit.SECONDS,          // keepAliveTime
                new LinkedBlockingQueue<>(10),  // workQueue (bounded)
                new ThreadFactory() {           // threadFactory
                    private int counter = 0;

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "CustomPool-" + (++counter));
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy() // rejectionHandler
        );

        // Monitor pool statistics
        ScheduledExecutorService monitor = Executors.newSingleThreadScheduledExecutor();
        monitor.scheduleAtFixedRate(() -> {
            System.out.printf("Pool stats - Active: %d, Pool size: %d, Queue size: %d%n",
                    customPool.getActiveCount(),
                    customPool.getPoolSize(),
                    customPool.getQueue().size());
        }, 0, 500, TimeUnit.MILLISECONDS);

        // Submit many tasks to see pool behavior
        for (int i = 0; i < 20; i++) {
            final int taskId = i;
            try {
                customPool.submit(() -> {
                    System.out.println("Task " + taskId + " running on " +
                            Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                });
            } catch (RejectedExecutionException e) {
                System.out.println("Task " + taskId + " rejected!");
            }
        }

        Thread.sleep(8000); // Let tasks complete

        customPool.shutdown();
        monitor.shutdown();

        System.out.println("Final pool stats:");
        System.out.println("Completed tasks: " + customPool.getCompletedTaskCount());
        System.out.println("Largest pool size: " + customPool.getLargestPoolSize());
    }
}