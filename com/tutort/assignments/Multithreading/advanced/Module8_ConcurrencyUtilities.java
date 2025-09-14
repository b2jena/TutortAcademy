package com.tutort.assignments.Multithreading.advanced;

import java.util.concurrent.*;

/**
 * Module 8: Concurrency Utilities
 * CountDownLatch, CyclicBarrier, Semaphore, BlockingQueue
 */
public class Module8_ConcurrencyUtilities {

    public static void main(String[] args) throws Exception {
        demonstrateCountDownLatch();
        demonstrateCyclicBarrier();
        demonstrateSemaphore();
        demonstrateBlockingQueue();
    }

    private static void demonstrateCountDownLatch() throws InterruptedException {
        System.out.println("=== CountDownLatch (Wait for N events) ===");

        CountDownLatch latch = new CountDownLatch(3);

        // Workers that count down
        for (int i = 0; i < 3; i++) {
            final int workerId = i;
            new Thread(() -> {
                try {
                    Thread.sleep(1000 + workerId * 500);
                    System.out.println("Worker " + workerId + " completed");
                    latch.countDown();
                } catch (InterruptedException e) {
                }
            }).start();
        }

        System.out.println("Main thread waiting for all workers...");
        latch.await(); // Blocks until count reaches 0
        System.out.println("All workers completed!\n");
    }

    private static void demonstrateCyclicBarrier() throws InterruptedException {
        System.out.println("=== CyclicBarrier (Synchronization point) ===");

        CyclicBarrier barrier = new CyclicBarrier(3, () ->
                System.out.println("*** All threads reached barrier! ***"));

        for (int i = 0; i < 3; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    Thread.sleep(1000 + threadId * 300);
                    System.out.println("Thread " + threadId + " reached barrier");
                    barrier.await(); // Wait for others
                    System.out.println("Thread " + threadId + " proceeding");
                } catch (Exception e) {
                }
            }).start();
        }

        Thread.sleep(3000);
        System.out.println();
    }

    private static void demonstrateSemaphore() throws InterruptedException {
        System.out.println("=== Semaphore (Resource pool) ===");

        Semaphore semaphore = new Semaphore(2); // Only 2 permits

        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("Task " + taskId + " acquired resource");
                    Thread.sleep(1000);
                    System.out.println("Task " + taskId + " releasing resource");
                    semaphore.release();
                } catch (InterruptedException e) {
                }
            }).start();
        }

        Thread.sleep(4000);
        System.out.println();
    }

    private static void demonstrateBlockingQueue() throws InterruptedException {
        System.out.println("=== BlockingQueue (Producer-Consumer) ===");

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        // Producer
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    queue.put(i); // Blocks if queue full
                    System.out.println("Produced: " + i);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
            }
        });

        // Consumer
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Integer item = queue.take(); // Blocks if queue empty
                    System.out.println("Consumed: " + item);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}