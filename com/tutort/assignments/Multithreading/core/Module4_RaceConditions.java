package com.tutort.assignments.Multithreading.core;

/**
 * Module 4: Race Conditions and Synchronization
 * <p>
 * Race Condition: When multiple threads access shared data concurrently
 * and the outcome depends on timing of execution.
 */
public class Module4_RaceConditions {

    private static int counter = 0;
    private static int synchronizedCounter = 0;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Race Conditions Demo ===");

        demonstrateRaceCondition();
        demonstrateSynchronizedSolution();
        demonstrateAtomicSolution();
    }

    private static void demonstrateRaceCondition() throws InterruptedException {
        System.out.println("\n--- Race Condition Problem ---");

        counter = 0; // Reset
        Thread[] threads = new Thread[10];

        // Create 10 threads, each incrementing counter 1000 times
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter++; // NOT thread-safe!
                }
            });
            threads[i].start();
        }

        // Wait for all threads
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Expected: 10000");
        System.out.println("Actual: " + counter);
        System.out.println("Race condition occurred: " + (counter != 10000));
    }

    private static void demonstrateSynchronizedSolution() throws InterruptedException {
        System.out.println("\n--- Synchronized Solution ---");

        synchronizedCounter = 0; // Reset
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    incrementSynchronized();
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Expected: 10000");
        System.out.println("Actual: " + synchronizedCounter);
        System.out.println("Problem solved: " + (synchronizedCounter == 10000));
    }

    private static synchronized void incrementSynchronized() {
        synchronizedCounter++; // Thread-safe
    }

    private static void demonstrateAtomicSolution() throws InterruptedException {
        System.out.println("\n--- Atomic Solution ---");

        java.util.concurrent.atomic.AtomicInteger atomicCounter =
                new java.util.concurrent.atomic.AtomicInteger(0);

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicCounter.incrementAndGet(); // Lock-free thread-safe
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Expected: 10000");
        System.out.println("Actual: " + atomicCounter.get());
        System.out.println("Atomic solution works: " + (atomicCounter.get() == 10000));

        // Performance comparison
        comparePerformance();
    }

    private static void comparePerformance() throws InterruptedException {
        System.out.println("\n--- Performance Comparison ---");

        int iterations = 1_000_000;

        // Synchronized method
        long start = System.nanoTime();
        Thread[] syncThreads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            syncThreads[i] = new Thread(() -> {
                for (int j = 0; j < iterations / 4; j++) {
                    incrementSynchronized();
                }
            });
            syncThreads[i].start();
        }
        for (Thread t : syncThreads) t.join();
        long syncTime = System.nanoTime() - start;

        // Atomic method
        java.util.concurrent.atomic.AtomicInteger atomic =
                new java.util.concurrent.atomic.AtomicInteger(0);

        start = System.nanoTime();
        Thread[] atomicThreads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            atomicThreads[i] = new Thread(() -> {
                for (int j = 0; j < iterations / 4; j++) {
                    atomic.incrementAndGet();
                }
            });
            atomicThreads[i].start();
        }
        for (Thread t : atomicThreads) t.join();
        long atomicTime = System.nanoTime() - start;

        System.out.println("Synchronized time: " + syncTime / 1_000_000 + "ms");
        System.out.println("Atomic time: " + atomicTime / 1_000_000 + "ms");
        System.out.println("Atomic is " + (double) syncTime / atomicTime + "x faster");
    }
}