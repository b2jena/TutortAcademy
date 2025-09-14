package com.tutort.assignments.Multithreading.foundations;

/**
 * Module 3: Thread Scheduling and Context Switching
 * <p>
 * Understanding:
 * - Thread priorities
 * - yield(), sleep(), join()
 * - Context switching overhead
 */
public class Module3_ThreadScheduling {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Thread Scheduling Demo ===");

        demonstratePriorities();
        demonstrateYieldVsSleep();
        demonstrateJoin();
        measureContextSwitchingOverhead();
    }

    private static void demonstratePriorities() throws InterruptedException {
        System.out.println("\n--- Thread Priorities ---");

        Thread lowPriority = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Low priority: " + i);
                Thread.yield(); // Hint to scheduler
            }
        }, "LowPriority");

        Thread highPriority = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("HIGH PRIORITY: " + i);
                Thread.yield();
            }
        }, "HighPriority");

        lowPriority.setPriority(Thread.MIN_PRIORITY);   // 1
        highPriority.setPriority(Thread.MAX_PRIORITY);  // 10

        lowPriority.start();
        highPriority.start();

        lowPriority.join();
        highPriority.join();
    }

    private static void demonstrateYieldVsSleep() throws InterruptedException {
        System.out.println("\n--- yield() vs sleep() ---");

        Thread yieldThread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("Yield thread: " + i);
                Thread.yield(); // Gives up CPU voluntarily
            }
        }, "YieldThread");

        Thread sleepThread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("Sleep thread: " + i);
                try {
                    Thread.sleep(100); // Guaranteed pause
                } catch (InterruptedException e) {
                }
            }
        }, "SleepThread");

        long start = System.currentTimeMillis();
        yieldThread.start();
        sleepThread.start();

        yieldThread.join();
        sleepThread.join();

        System.out.println("Total time: " + (System.currentTimeMillis() - start) + "ms");
    }

    private static void demonstrateJoin() throws InterruptedException {
        System.out.println("\n--- join() Demo ---");

        Thread worker1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Worker 1 completed");
            } catch (InterruptedException e) {
            }
        }, "Worker1");

        Thread worker2 = new Thread(() -> {
            try {
                Thread.sleep(1500);
                System.out.println("Worker 2 completed");
            } catch (InterruptedException e) {
            }
        }, "Worker2");

        System.out.println("Starting workers...");
        worker1.start();
        worker2.start();

        // Wait for both to complete
        worker1.join();
        worker2.join();

        System.out.println("All workers completed!");
    }

    private static void measureContextSwitchingOverhead() throws InterruptedException {
        System.out.println("\n--- Context Switching Overhead ---");

        int iterations = 1000;

        // Single thread
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            // Simple computation
            Math.sqrt(i);
        }
        long singleThreadTime = System.nanoTime() - start;

        // Multiple threads with context switching
        start = System.nanoTime();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < iterations / 10; j++) {
                    Math.sqrt(threadId * 100 + j);
                    Thread.yield(); // Force context switch
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }
        long multiThreadTime = System.nanoTime() - start;

        System.out.println("Single thread time: " + singleThreadTime / 1_000_000 + "ms");
        System.out.println("Multi thread time: " + multiThreadTime / 1_000_000 + "ms");
        System.out.println("Overhead factor: " + (double) multiThreadTime / singleThreadTime);
    }
}