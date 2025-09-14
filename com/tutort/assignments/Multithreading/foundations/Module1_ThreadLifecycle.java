package com.tutort.assignments.Multithreading.foundations;

/**
 * Module 1: Thread Lifecycle and States
 * <p>
 * Thread States in Java:
 * NEW -> RUNNABLE -> BLOCKED/WAITING/TIMED_WAITING -> TERMINATED
 */
public class Module1_ThreadLifecycle {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Thread Lifecycle Demo ===");

        // Create thread (NEW state)
        Thread worker = new Thread(() -> {
            try {
                System.out.println("Worker: Starting work...");
                Thread.sleep(2000); // TIMED_WAITING
                System.out.println("Worker: Work completed!");
            } catch (InterruptedException e) {
                System.out.println("Worker: Interrupted!");
            }
        }, "WorkerThread");

        // Check initial state
        System.out.println("Initial state: " + worker.getState()); // NEW

        // Start thread (NEW -> RUNNABLE)
        worker.start();
        System.out.println("After start(): " + worker.getState()); // RUNNABLE

        // Check state while running
        Thread.sleep(100);
        System.out.println("While running: " + worker.getState()); // RUNNABLE or TIMED_WAITING

        // Wait for completion
        worker.join(); // Main thread waits
        System.out.println("Final state: " + worker.getState()); // TERMINATED

        demonstrateAllStates();
    }

    private static void demonstrateAllStates() throws InterruptedException {
        System.out.println("\n=== All Thread States Demo ===");

        Object lock = new Object();

        // Thread that will be BLOCKED
        Thread blockedThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
            }
        }, "BlockedThread");

        // Thread that will be WAITING
        Thread waitingThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait(); // WAITING state
                } catch (InterruptedException e) {
                }
            }
        }, "WaitingThread");

        blockedThread.start();
        Thread.sleep(100); // Let first thread acquire lock

        waitingThread.start();
        Thread.sleep(100);

        // Create thread that will try to acquire same lock (BLOCKED)
        Thread secondBlockedThread = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Got the lock!");
            }
        }, "SecondBlockedThread");

        secondBlockedThread.start();
        Thread.sleep(100);

        // Print states
        System.out.println("BlockedThread state: " + blockedThread.getState());
        System.out.println("WaitingThread state: " + waitingThread.getState());
        System.out.println("SecondBlockedThread state: " + secondBlockedThread.getState());

        // Cleanup
        blockedThread.interrupt();
        waitingThread.interrupt();
        secondBlockedThread.interrupt();
    }
}