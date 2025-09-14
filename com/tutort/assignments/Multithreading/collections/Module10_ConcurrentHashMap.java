package com.tutort.assignments.Multithreading.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Module 10: ConcurrentHashMap Deep Dive
 * Internal implementation: segments, buckets, CAS, lock striping
 */
public class Module10_ConcurrentHashMap {

    public static void main(String[] args) throws InterruptedException {
        demonstrateBasicOperations();
        demonstrateWeakConsistency();
        compareWithHashMap();
        demonstrateComputeOperations();
        performanceAnalysis();
    }

    private static void demonstrateBasicOperations() {
        System.out.println("=== ConcurrentHashMap Basic Operations ===");

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // Thread-safe operations
        map.put("key1", 1);
        map.putIfAbsent("key2", 2);
        map.replace("key1", 1, 10); // Atomic replace if current value is 1

        System.out.println("Map: " + map);
        System.out.println("Size: " + map.size());
        System.out.println();
    }

    private static void demonstrateWeakConsistency() throws InterruptedException {
        System.out.println("=== Weakly Consistent Iterators ===");

        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, "value" + i);
        }

        Thread iterator = new Thread(() -> {
            System.out.println("Iterator starting...");
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                System.out.println("Iterating: " + entry.getKey());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });

        Thread modifier = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            System.out.println("Modifying map during iteration...");
            map.put(10, "new value");
            map.remove(5);
        });

        iterator.start();
        modifier.start();
        iterator.join();
        modifier.join();

        System.out.println("Final map size: " + map.size());
        System.out.println();
    }

    private static void compareWithHashMap() throws InterruptedException {
        System.out.println("=== HashMap vs ConcurrentHashMap ===");

        // HashMap (not thread-safe)
        Map<Integer, Integer> hashMap = new HashMap<>();

        // ConcurrentHashMap (thread-safe)
        ConcurrentHashMap<Integer, Integer> concurrentMap = new ConcurrentHashMap<>();

        int iterations = 10000;

        // Test HashMap with multiple threads (will cause issues)
        Thread[] hashMapThreads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            final int threadId = i;
            hashMapThreads[i] = new Thread(() -> {
                for (int j = 0; j < iterations / 4; j++) {
                    hashMap.put(threadId * 1000 + j, j);
                }
            });
            hashMapThreads[i].start();
        }
        for (Thread t : hashMapThreads) t.join();

        // Test ConcurrentHashMap
        Thread[] concurrentThreads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            final int threadId = i;
            concurrentThreads[i] = new Thread(() -> {
                for (int j = 0; j < iterations / 4; j++) {
                    concurrentMap.put(threadId * 1000 + j, j);
                }
            });
            concurrentThreads[i].start();
        }
        for (Thread t : concurrentThreads) t.join();

        System.out.println("HashMap size: " + hashMap.size() + " (may be incorrect)");
        System.out.println("ConcurrentHashMap size: " + concurrentMap.size() + " (correct)");
        System.out.println();
    }

    private static void demonstrateComputeOperations() {
        System.out.println("=== Atomic Compute Operations ===");

        ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<>();

        String[] words = {"java", "thread", "java", "concurrent", "thread", "java"};

        // Atomic increment using compute
        for (String word : words) {
            wordCount.compute(word, (key, val) -> val == null ? 1 : val + 1);
        }

        System.out.println("Word count: " + wordCount);

        // Merge operation
        wordCount.merge("new", 5, Integer::sum);
        wordCount.merge("java", 10, Integer::sum);

        System.out.println("After merge: " + wordCount);
        System.out.println();
    }

    private static void performanceAnalysis() throws InterruptedException {
        System.out.println("=== Performance Analysis ===");

        int size = 100000;
        int threads = 8;

        // ConcurrentHashMap performance
        ConcurrentHashMap<Integer, Integer> concurrentMap = new ConcurrentHashMap<>();
        long start = System.nanoTime();

        Thread[] writers = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            final int threadId = i;
            writers[i] = new Thread(() -> {
                for (int j = 0; j < size / threads; j++) {
                    concurrentMap.put(threadId * (size / threads) + j, j);
                }
            });
            writers[i].start();
        }
        for (Thread t : writers) t.join();

        long concurrentTime = System.nanoTime() - start;

        // Synchronized HashMap performance
        Map<Integer, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
        start = System.nanoTime();

        Thread[] syncWriters = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            final int threadId = i;
            syncWriters[i] = new Thread(() -> {
                for (int j = 0; j < size / threads; j++) {
                    syncMap.put(threadId * (size / threads) + j, j);
                }
            });
            syncWriters[i].start();
        }
        for (Thread t : syncWriters) t.join();

        long syncTime = System.nanoTime() - start;

        System.out.println("ConcurrentHashMap time: " + concurrentTime / 1_000_000 + "ms");
        System.out.println("Synchronized HashMap time: " + syncTime / 1_000_000 + "ms");
        System.out.println("ConcurrentHashMap is " + (double) syncTime / concurrentTime + "x faster");

        System.out.println("\n=== Key Implementation Details ===");
        System.out.println("• Java 8+: Uses CAS + synchronized buckets (no segments)");
        System.out.println("• Lock striping: Only locks specific buckets, not entire map");
        System.out.println("• Weakly consistent iterators: Don't throw ConcurrentModificationException");
        System.out.println("• Atomic operations: compute(), merge(), replace()");
        System.out.println("• Resizing: Concurrent resizing with helping mechanism");
    }
}