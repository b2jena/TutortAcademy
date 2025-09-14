package com.tutort.assignments.Multithreading.core;

/**
 * Module 6: Volatile Keyword and Java Memory Model
 * <p>
 * Understanding:
 * - CPU caches and memory visibility
 * - Happens-before relationship
 * - volatile vs synchronized
 * - Memory barriers
 */
public class Module6_VolatileAndMemoryModel {

    // Without volatile - may not be visible to other threads
    private static boolean flag = false;
    private static int counter = 0;

    // With volatile - guarantees visibility
    private static volatile boolean volatileFlag = false;
    private static volatile int volatileCounter = 0;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Volatile and Memory Model ===");

        demonstrateVisibilityProblem();
        demonstrateVolatileSolution();
        demonstrateHappensBefore();
        compareVolatileVsSynchronized();
    }

    private static void demonstrateVisibilityProblem() throws InterruptedException {
        System.out.println("\n--- Memory Visibility Problem ---");

        flag = false;
        counter = 0;

        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(100);
                counter = 42;
                flag = true; // Signal that counter is ready
                System.out.println("Writer: Set counter = " + counter + ", flag = " + flag);
            } catch (InterruptedException e) {
            }
        }, "Writer");

        Thread reader = new Thread(() -> {
            while (!flag) {
                // Busy wait - may never see flag change without volatile!
                // In practice, this might loop forever due to CPU caching
            }
            System.out.println("Reader: Saw flag = " + flag + ", counter = " + counter);
        }, "Reader");

        reader.start();
        writer.start();

        // Give threads time to run
        Thread.sleep(1000);

        if (reader.isAlive()) {
            System.out.println("Reader is still waiting - visibility problem!");
            reader.interrupt();
        }

        writer.join();
        reader.join();
    }

    private static void demonstrateVolatileSolution() throws InterruptedException {
        System.out.println("\n--- Volatile Solution ---");

        volatileFlag = false;
        volatileCounter = 0;

        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(100);
                volatileCounter = 42;
                volatileFlag = true; // volatile write - creates memory barrier
                System.out.println("Writer: Set volatileCounter = " + volatileCounter +
                        ", volatileFlag = " + volatileFlag);
            } catch (InterruptedException e) {
            }
        }, "VolatileWriter");

        Thread reader = new Thread(() -> {
            while (!volatileFlag) {
                // volatile read - will see the change
            }
            System.out.println("Reader: Saw volatileFlag = " + volatileFlag +
                    ", volatileCounter = " + volatileCounter);
        }, "VolatileReader");

        reader.start();
        writer.start();

        writer.join();
        reader.join();

        System.out.println("Volatile ensures visibility!");
    }

    private static void demonstrateHappensBefore() throws InterruptedException {
        System.out.println("\n--- Happens-Before Relationship ---");

        SharedData data = new SharedData();

        Thread producer = new Thread(() -> {
            data.x = 10;
            data.y = 20;
            data.ready = true; // volatile write establishes happens-before
            System.out.println("Producer: Set x=10, y=20, ready=true");
        }, "Producer");

        Thread consumer = new Thread(() -> {
            while (!data.ready) {
                // Wait for ready flag
            }
            // Due to happens-before, we're guaranteed to see x=10, y=20
            System.out.println("Consumer: x=" + data.x + ", y=" + data.y +
                    " (guaranteed to be 10, 20)");
        }, "Consumer");

        consumer.start();
        producer.start();

        producer.join();
        consumer.join();
    }

    private static void compareVolatileVsSynchronized() throws InterruptedException {
        System.out.println("\n--- Volatile vs Synchronized Performance ---");

        int iterations = 10_000_000;

        // Volatile performance
        VolatileCounter volatileCounter = new VolatileCounter();
        long start = System.nanoTime();

        Thread[] volatileThreads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            volatileThreads[i] = new Thread(() -> {
                for (int j = 0; j < iterations / 4; j++) {
                    volatileCounter.increment(); // NOT atomic!
                }
            });
            volatileThreads[i].start();
        }
        for (Thread t : volatileThreads) t.join();

        long volatileTime = System.nanoTime() - start;

        // Synchronized performance
        SynchronizedCounter syncCounter = new SynchronizedCounter();
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

        System.out.println("Volatile time: " + volatileTime / 1_000_000 + "ms");
        System.out.println("Synchronized time: " + syncTime / 1_000_000 + "ms");
        System.out.println("Volatile counter (incorrect): " + volatileCounter.get());
        System.out.println("Synchronized counter (correct): " + syncCounter.get());
        System.out.println("Note: volatile is faster but NOT atomic for compound operations!");
    }

    static class SharedData {
        int x, y;
        volatile boolean ready; // volatile creates happens-before edge
    }

    static class VolatileCounter {
        private volatile int count = 0;

        public void increment() {
            count++; // NOT atomic! Read-modify-write
        }

        public int get() {
            return count;
        }
    }

    static class SynchronizedCounter {
        private int count = 0;

        public synchronized void increment() {
            count++; // Atomic
        }

        public synchronized int get() {
            return count;
        }
    }
}