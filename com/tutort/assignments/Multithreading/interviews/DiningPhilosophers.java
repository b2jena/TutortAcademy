package com.tutort.assignments.Multithreading.interviews;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * FAANG Interview Classic: Dining Philosophers Problem
 * <p>
 * Problem: 5 philosophers sit around a table with 5 forks.
 * Each philosopher needs 2 forks to eat.
 * Prevent deadlock while allowing maximum concurrency.
 */
public class DiningPhilosophers {

    private static final int NUM_PHILOSOPHERS = 5;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Dining Philosophers Solutions ===");

        // Solution 1: Deadlock-prone (DON'T USE)
        // runDeadlockVersion();

        // Solution 2: Lock ordering
        runLockOrderingSolution();

        // Solution 3: Semaphore solution
        // runSemaphoreSolution();
    }

    // Solution 2: Lock Ordering (Prevents Circular Wait)
    private static void runLockOrderingSolution() throws InterruptedException {
        System.out.println("\n--- Lock Ordering Solution ---");

        Lock[] forks = new ReentrantLock[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new ReentrantLock();
        }

        Thread[] philosophers = new Thread[NUM_PHILOSOPHERS];

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            final int id = i;
            philosophers[i] = new Thread(() -> {
                try {
                    for (int meal = 0; meal < 3; meal++) {
                        think(id);
                        eatWithLockOrdering(id, forks);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Philosopher-" + i);
        }

        for (Thread p : philosophers) p.start();
        for (Thread p : philosophers) p.join();

        System.out.println("All philosophers finished eating!");
    }

    private static void eatWithLockOrdering(int id, Lock[] forks) throws InterruptedException {
        int leftFork = id;
        int rightFork = (id + 1) % NUM_PHILOSOPHERS;

        // Always acquire lower-numbered fork first (prevents circular wait)
        Lock firstFork = forks[Math.min(leftFork, rightFork)];
        Lock secondFork = forks[Math.max(leftFork, rightFork)];

        firstFork.lock();
        try {
            System.out.println("Philosopher " + id + " picked up first fork");

            secondFork.lock();
            try {
                System.out.println("Philosopher " + id + " is eating...");
                Thread.sleep(100); // Eating time
                System.out.println("Philosopher " + id + " finished eating");
            } finally {
                secondFork.unlock();
            }
        } finally {
            firstFork.unlock();
        }
    }

    // Solution 3: Semaphore (Limit concurrent eaters)
    private static void runSemaphoreSolution() throws InterruptedException {
        System.out.println("\n--- Semaphore Solution ---");

        Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        // Allow only 4 philosophers to try eating simultaneously
        Semaphore diningRoom = new Semaphore(NUM_PHILOSOPHERS - 1);

        Thread[] philosophers = new Thread[NUM_PHILOSOPHERS];

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            final int id = i;
            philosophers[i] = new Thread(() -> {
                try {
                    for (int meal = 0; meal < 3; meal++) {
                        think(id);
                        eatWithSemaphore(id, forks, diningRoom);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Philosopher-" + i);
        }

        for (Thread p : philosophers) p.start();
        for (Thread p : philosophers) p.join();

        System.out.println("All philosophers finished eating!");
    }

    private static void eatWithSemaphore(int id, Semaphore[] forks, Semaphore diningRoom)
            throws InterruptedException {

        diningRoom.acquire(); // Enter dining room
        try {
            int leftFork = id;
            int rightFork = (id + 1) % NUM_PHILOSOPHERS;

            forks[leftFork].acquire();
            forks[rightFork].acquire();

            System.out.println("Philosopher " + id + " is eating...");
            Thread.sleep(100);
            System.out.println("Philosopher " + id + " finished eating");

            forks[rightFork].release();
            forks[leftFork].release();
        } finally {
            diningRoom.release(); // Leave dining room
        }
    }

    private static void think(int id) throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking...");
        Thread.sleep(50);
    }
}