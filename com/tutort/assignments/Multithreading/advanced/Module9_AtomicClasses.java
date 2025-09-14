package com.tutort.assignments.Multithreading.advanced;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Module 9: Atomic Classes and Compare-And-Swap (CAS)
 * Lock-free thread-safe operations using hardware support
 */
public class Module9_AtomicClasses {

    public static void main(String[] args) throws InterruptedException {
        demonstrateAtomicInteger();
        demonstrateAtomicReference();
        demonstrateCompareAndSwap();
        performanceComparison();
    }

    private static void demonstrateAtomicInteger() throws InterruptedException {
        System.out.println("=== AtomicInteger ===");

        AtomicInteger counter = new AtomicInteger(0);

        Thread[] threads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.incrementAndGet(); // Atomic increment
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) t.join();
        System.out.println("Final count: " + counter.get()); // Always 4000
        System.out.println();
    }

    private static void demonstrateAtomicReference() throws InterruptedException {
        System.out.println("=== AtomicReference ===");

        AtomicReference<String> atomicRef = new AtomicReference<>("initial");

        Thread updater = new Thread(() -> {
            atomicRef.set("updated");
            System.out.println("Updated to: " + atomicRef.get());
        });

        Thread reader = new Thread(() -> {
            String value = atomicRef.get();
            System.out.println("Read: " + value);
        });

        updater.start();
        reader.start();
        updater.join();
        reader.join();
        System.out.println();
    }

    private static void demonstrateCompareAndSwap() {
        System.out.println("=== Compare-And-Swap (CAS) ===");

        AtomicInteger value = new AtomicInteger(10);

        // CAS: if current value is 10, set it to 20
        boolean success = value.compareAndSet(10, 20);
        System.out.println("CAS success: " + success + ", value: " + value.get());

        // This will fail because value is now 20, not 10
        success = value.compareAndSet(10, 30);
        System.out.println("CAS success: " + success + ", value: " + value.get());

        // Lock-free increment using CAS
        lockFreeIncrement(value);
        System.out.println("After lock-free increment: " + value.get());
        System.out.println();
    }

    private static void lockFreeIncrement(AtomicInteger value) {
        int current, next;
        do {
            current = value.get();
            next = current + 1;
        } while (!value.compareAndSet(current, next)); // Retry until success
    }

    private static void performanceComparison() throws InterruptedException {
        System.out.println("=== Performance: Atomic vs Synchronized ===");

        int iterations = 1_000_000;

        // Atomic performance
        AtomicLong atomicCounter = new AtomicLong(0);
        long start = System.nanoTime();

        Thread[] atomicThreads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            atomicThreads[i] = new Thread(() -> {
                for (int j = 0; j < iterations / 4; j++) {
                    atomicCounter.incrementAndGet();
                }
            });
            atomicThreads[i].start();
        }
        for (Thread t : atomicThreads) t.join();
        long atomicTime = System.nanoTime() - start;

        // Synchronized performance
        SyncCounter syncCounter = new SyncCounter();
        start = System.nanoTime();

        Thread[] syncThreads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            syncThreads[i] = new Thread(() -> {
                for (int j = 0; j < iterations / 4; j++) {
                    syncCounter.increment();
                }
            });
            syncThreads[i].start();
        }
        for (Thread t : syncThreads) t.join();
        long syncTime = System.nanoTime() - start;

        System.out.println("Atomic time: " + atomicTime / 1_000_000 + "ms");
        System.out.println("Synchronized time: " + syncTime / 1_000_000 + "ms");
        System.out.println("Atomic is " + (double) syncTime / atomicTime + "x faster");
    }

    static class SyncCounter {
        private long count = 0;

        public synchronized void increment() {
            count++;
        }

        public synchronized long get() {
            return count;
        }
    }
}