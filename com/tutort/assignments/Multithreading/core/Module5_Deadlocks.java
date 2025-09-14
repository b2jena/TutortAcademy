package com.tutort.assignments.Multithreading.core;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Module 5: Deadlocks, Livelocks, and Starvation
 * <p>
 * Deadlock: Two or more threads waiting for each other indefinitely
 * Conditions: Mutual Exclusion, Hold & Wait, No Preemption, Circular Wait
 */
public class Module5_Deadlocks {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Deadlock Scenarios ===");

        // Uncomment ONE at a time to see different scenarios
        // demonstrateDeadlock();
        demonstrateDeadlockPrevention();
        demonstrateLivelock();
        demonstrateStarvation();
    }

    private static void demonstrateDeadlock() throws InterruptedException {
        System.out.println("\n--- Creating Deadlock ---");

        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Acquired lock1");
                try {
                    Thread.sleep(100); // Simulate work
                } catch (InterruptedException e) {
                }

                System.out.println("Thread 1: Waiting for lock2...");
                synchronized (lock2) {
                    System.out.println("Thread 1: Acquired lock2");
                }
            }
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2: Acquired lock2");
                try {
                    Thread.sleep(100); // Simulate work
                } catch (InterruptedException e) {
                }

                System.out.println("Thread 2: Waiting for lock1...");
                synchronized (lock1) {
                    System.out.println("Thread 2: Acquired lock1");
                }
            }
        }, "Thread-2");

        thread1.start();
        thread2.start();

        // Wait a bit, then interrupt if deadlocked
        Thread.sleep(2000);
        if (thread1.isAlive() || thread2.isAlive()) {
            System.out.println("DEADLOCK DETECTED! Interrupting threads...");
            thread1.interrupt();
            thread2.interrupt();
        }

        thread1.join();
        thread2.join();
    }

    private static void demonstrateDeadlockPrevention() throws InterruptedException {
        System.out.println("\n--- Deadlock Prevention (Lock Ordering) ---");

        Thread thread1 = new Thread(() -> {
            // Always acquire locks in same order: lock1 then lock2
            synchronized (lock1) {
                System.out.println("Thread 1: Acquired lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }

                synchronized (lock2) {
                    System.out.println("Thread 1: Acquired lock2");
                }
            }
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            // Same order: lock1 then lock2
            synchronized (lock1) {
                System.out.println("Thread 2: Acquired lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }

                synchronized (lock2) {
                    System.out.println("Thread 2: Acquired lock2");
                }
            }
        }, "Thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("No deadlock - both threads completed!");
    }

    private static void demonstrateLivelock() throws InterruptedException {
        System.out.println("\n--- Livelock Demo ---");

        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    if (lock1.tryLock(50, TimeUnit.MILLISECONDS)) {
                        System.out.println("Thread 1: Got lock1");

                        if (lock2.tryLock(50, TimeUnit.MILLISECONDS)) {
                            System.out.println("Thread 1: Got both locks!");
                            lock2.unlock();
                        } else {
                            System.out.println("Thread 1: Can't get lock2, releasing lock1");
                        }
                        lock1.unlock();
                    }
                    Thread.sleep(10); // Back off
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    if (lock2.tryLock(50, TimeUnit.MILLISECONDS)) {
                        System.out.println("Thread 2: Got lock2");

                        if (lock1.tryLock(50, TimeUnit.MILLISECONDS)) {
                            System.out.println("Thread 2: Got both locks!");
                            lock1.unlock();
                        } else {
                            System.out.println("Thread 2: Can't get lock1, releasing lock2");
                        }
                        lock2.unlock();
                    }
                    Thread.sleep(10); // Back off
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "Thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    private static void demonstrateStarvation() throws InterruptedException {
        System.out.println("\n--- Starvation Demo ---");

        Object resource = new Object();

        // High priority threads that keep getting the resource
        for (int i = 0; i < 3; i++) {
            Thread highPriority = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    synchronized (resource) {
                        System.out.println("High priority thread working: " +
                                Thread.currentThread().getName());
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                    }
                    Thread.yield();
                }
            }, "HighPriority-" + i);
            highPriority.setPriority(Thread.MAX_PRIORITY);
            highPriority.start();
        }

        // Low priority thread that may starve
        Thread lowPriority = new Thread(() -> {
            for (int j = 0; j < 3; j++) {
                synchronized (resource) {
                    System.out.println("*** Low priority thread finally got resource! ***");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }, "LowPriority");
        lowPriority.setPriority(Thread.MIN_PRIORITY);
        lowPriority.start();

        Thread.sleep(3000); // Let them compete
    }
}