package com.tutort.assignments.Multithreading.foundations;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Module 2: Different Ways to Create Threads
 * <p>
 * 1. Extending Thread class
 * 2. Implementing Runnable interface
 * 3. Using Callable interface
 * 4. Using ExecutorService
 */
public class Module2_CreatingThreads {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Thread Creation Methods ===");

        // Method 1: Extending Thread class
        MyThread thread1 = new MyThread("Thread-1");
        thread1.start();

        // Method 2: Implementing Runnable
        Thread thread2 = new Thread(new MyRunnable(), "Thread-2");
        thread2.start();

        // Method 3: Lambda expression (Runnable)
        Thread thread3 = new Thread(() -> {
            System.out.println("Lambda thread: " + Thread.currentThread().getName());
        }, "Thread-3");
        thread3.start();

        // Method 4: Using Callable (returns value)
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new MyCallable());
        System.out.println("Callable result: " + future.get());

        // Wait for all threads
        thread1.join();
        thread2.join();
        thread3.join();
        executor.shutdown();

        demonstrateThreadProperties();
    }

    private static void demonstrateThreadProperties() throws InterruptedException {
        System.out.println("\n=== Thread Properties ===");

        Thread mainThread = Thread.currentThread();
        System.out.println("Main thread name: " + mainThread.getName());
        System.out.println("Main thread priority: " + mainThread.getPriority());
        System.out.println("Active threads: " + Thread.activeCount());

        // Create daemon thread
        Thread daemon = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println("Daemon working...");
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        daemon.setDaemon(true); // Dies when main thread dies
        daemon.start();

        Thread.sleep(2000);
        System.out.println("Main thread ending...");
    }

    // Method 1: Extending Thread
    static class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println("MyThread running: " + getName());
            System.out.println("  Priority: " + getPriority());
            System.out.println("  Is Daemon: " + isDaemon());
        }
    }

    // Method 2: Implementing Runnable
    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            Thread current = Thread.currentThread();
            System.out.println("MyRunnable running: " + current.getName());
            System.out.println("  Thread ID: " + current.getId());
        }
    }

    // Method 3: Implementing Callable
    static class MyCallable implements Callable<String> {
        @Override
        public String call() {
            return "Result from " + Thread.currentThread().getName();
        }
    }
}