package com.tutort.assignments.Multithreading.collections;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Module 11: Other Concurrent Collections
 * CopyOnWriteArrayList, ConcurrentLinkedQueue, etc.
 */
public class Module11_ConcurrentCollections {

    public static void main(String[] args) throws InterruptedException {
        demonstrateCopyOnWriteArrayList();
        demonstrateConcurrentLinkedQueue();
        demonstrateBlockingDeque();
    }

    private static void demonstrateCopyOnWriteArrayList() throws InterruptedException {
        System.out.println("=== CopyOnWriteArrayList ===");

        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("item1");
        list.add("item2");

        Thread reader = new Thread(() -> {
            for (String item : list) { // Safe iteration
                System.out.println("Reading: " + item);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });

        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
            list.add("item3"); // Creates new copy
            System.out.println("Added item3");
        });

        reader.start();
        writer.start();
        reader.join();
        writer.join();

        System.out.println("Final list: " + list);
        System.out.println("Use case: Read-heavy, write-light scenarios\n");
    }

    private static void demonstrateConcurrentLinkedQueue() throws InterruptedException {
        System.out.println("=== ConcurrentLinkedQueue ===");

        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                queue.offer(i);
                System.out.println("Produced: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                Integer item;
                while ((item = queue.poll()) == null) {
                    Thread.yield(); // Non-blocking wait
                }
                System.out.println("Consumed: " + item);
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println();
    }

    private static void demonstrateBlockingDeque() throws InterruptedException {
        System.out.println("=== LinkedBlockingDeque ===");

        BlockingDeque<String> deque = new LinkedBlockingDeque<>(3);

        Thread producer = new Thread(() -> {
            try {
                deque.putFirst("first");
                deque.putLast("last");
                deque.putFirst("new-first");
                System.out.println("Producer done");
            } catch (InterruptedException e) {
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(500);
                System.out.println("From first: " + deque.takeFirst());
                System.out.println("From last: " + deque.takeLast());
                System.out.println("From first: " + deque.takeFirst());
            } catch (InterruptedException e) {
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}