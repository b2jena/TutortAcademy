package com.tutort.assignments.ReactiveJava.advanced;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Part 4.2: Backpressure and Strategies
 * <p>
 * Backpressure: When producer is faster than consumer
 * Analogy: Water flowing through pipes - need pressure relief valve
 */
public class BackpressureDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Backpressure Handling ===\n");

        explainBackpressure();
        demonstrateBackpressureProblem();
        demonstrateBackpressureStrategies();
        realWorldExample();
    }

    /**
     * Explain what backpressure is and why it matters
     */
    private static void explainBackpressure() {
        System.out.println("--- What is Backpressure? ---");

        System.out.println("ANALOGY: Garden hose and bucket");
        System.out.println("• Hose (Producer): Produces water at high rate");
        System.out.println("• Bucket (Consumer): Limited capacity to hold water");
        System.out.println("• Problem: Water overflows if hose is too fast");
        System.out.println("• Solution: Control water flow rate");

        System.out.println("\nIN REACTIVE SYSTEMS:");
        System.out.println("• Producer: Emits data items");
        System.out.println("• Consumer: Processes data items");
        System.out.println("• Problem: Producer overwhelms consumer");
        System.out.println("• Solution: Backpressure strategies");

        System.out.println("\nWHY IT MATTERS:");
        System.out.println("• Prevents memory exhaustion");
        System.out.println("• Maintains system stability");
        System.out.println("• Ensures responsive performance");
        System.out.println();
    }

    /**
     * Demonstrate the backpressure problem
     */
    private static void demonstrateBackpressureProblem() throws InterruptedException {
        System.out.println("--- Backpressure Problem ---");

        BlockingQueue<String> buffer = new ArrayBlockingQueue<>(5); // Small buffer
        AtomicInteger produced = new AtomicInteger(0);
        AtomicInteger consumed = new AtomicInteger(0);

        // Fast producer
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    String item = "Item-" + i;

                    // Try to add item (will block when buffer is full)
                    boolean added = buffer.offer(item, 100, TimeUnit.MILLISECONDS);
                    if (added) {
                        produced.incrementAndGet();
                        System.out.println("Produced: " + item + " (buffer size: " + buffer.size() + ")");
                    } else {
                        System.out.println("DROPPED: " + item + " (buffer full!)");
                    }

                    Thread.sleep(50); // Fast production
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Slow consumer
        Thread consumer = new Thread(() -> {
            try {
                while (consumed.get() < 15) { // Process some items
                    String item = buffer.poll(1, TimeUnit.SECONDS);
                    if (item != null) {
                        consumed.incrementAndGet();
                        System.out.println("  Consumed: " + item);
                        Thread.sleep(200); // Slow processing
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        Thread.sleep(100); // Let producer get ahead
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Result: Produced=" + produced.get() + ", Consumed=" + consumed.get());
        System.out.println("Problem: Items were dropped due to buffer overflow!");
        System.out.println();
    }

    /**
     * Demonstrate different backpressure strategies
     */
    private static void demonstrateBackpressureStrategies() throws InterruptedException {
        System.out.println("--- Backpressure Strategies ---");

        // Strategy 1: DROP - Drop oldest items when buffer full
        demonstrateDropStrategy();

        // Strategy 2: LATEST - Keep only latest items
        demonstrateLatestStrategy();

        // Strategy 3: BUFFER - Expand buffer (with limits)
        demonstrateBufferStrategy();

        // Strategy 4: ERROR - Fail fast when overwhelmed
        demonstrateErrorStrategy();
    }

    private static void demonstrateDropStrategy() throws InterruptedException {
        System.out.println("1. DROP Strategy (onBackpressureDrop):");
        System.out.println("   Drop items when consumer can't keep up");

        BlockingQueue<String> dropBuffer = new LinkedBlockingQueue<>(3);

        // Producer with drop strategy
        for (int i = 1; i <= 10; i++) {
            String item = "Drop-" + i;
            boolean added = dropBuffer.offer(item);
            if (added) {
                System.out.println("   Accepted: " + item);
            } else {
                System.out.println("   DROPPED: " + item + " (buffer full)");
            }
        }

        System.out.println("   Buffer contents: " + dropBuffer);
        System.out.println();
    }

    private static void demonstrateLatestStrategy() {
        System.out.println("2. LATEST Strategy (onBackpressureLatest):");
        System.out.println("   Keep only the most recent item");

        // Simulate latest strategy with single-item buffer
        String[] latestBuffer = new String[1];

        for (int i = 1; i <= 5; i++) {
            String item = "Latest-" + i;
            latestBuffer[0] = item; // Always overwrite with latest
            System.out.println("   Updated latest: " + item);
        }

        System.out.println("   Final latest item: " + latestBuffer[0]);
        System.out.println();
    }

    private static void demonstrateBufferStrategy() {
        System.out.println("3. BUFFER Strategy (onBackpressureBuffer):");
        System.out.println("   Expand buffer up to limit, then apply overflow strategy");

        // Simulate expanding buffer
        BlockingQueue<String> expandingBuffer = new LinkedBlockingQueue<>();
        int maxSize = 5;

        for (int i = 1; i <= 8; i++) {
            String item = "Buffer-" + i;

            if (expandingBuffer.size() < maxSize) {
                expandingBuffer.offer(item);
                System.out.println("   Buffered: " + item + " (size: " + expandingBuffer.size() + ")");
            } else {
                System.out.println("   OVERFLOW: " + item + " (buffer at max capacity)");
            }
        }

        System.out.println();
    }

    private static void demonstrateErrorStrategy() {
        System.out.println("4. ERROR Strategy (onBackpressureError):");
        System.out.println("   Fail fast when backpressure detected");

        try {
            // Simulate error strategy
            int bufferCapacity = 3;
            int itemCount = 0;

            for (int i = 1; i <= 5; i++) {
                String item = "Error-" + i;
                itemCount++;

                if (itemCount <= bufferCapacity) {
                    System.out.println("   Processed: " + item);
                } else {
                    throw new RuntimeException("Backpressure overflow! Consumer too slow.");
                }
            }
        } catch (RuntimeException e) {
            System.out.println("   ERROR: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * Real-world example: Stock price streaming with backpressure
     */
    private static void realWorldExample() throws InterruptedException {
        System.out.println("--- Real-World Example: Stock Price Stream ---");

        // Simulate stock price updates (fast producer)
        BlockingQueue<StockPrice> priceBuffer = new ArrayBlockingQueue<>(10);

        Thread priceProducer = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    StockPrice price = new StockPrice("AAPL", 150.0 + i);

                    // Apply backpressure strategy: drop oldest if buffer full
                    if (!priceBuffer.offer(price)) {
                        priceBuffer.poll(); // Remove oldest
                        priceBuffer.offer(price); // Add new
                        System.out.println("   Backpressure: Dropped old price for " + price);
                    } else {
                        System.out.println("   Price update: " + price);
                    }

                    Thread.sleep(50); // Fast updates
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Slow consumer (UI updates)
        Thread priceConsumer = new Thread(() -> {
            try {
                while (true) {
                    StockPrice price = priceBuffer.poll(2, TimeUnit.SECONDS);
                    if (price == null) break;

                    System.out.println("     UI Updated: " + price);
                    Thread.sleep(150); // Slow UI updates
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        priceProducer.start();
        Thread.sleep(100);
        priceConsumer.start();

        priceProducer.join();
        priceConsumer.join();

        System.out.println("\nBackpressure Strategy Applied:");
        System.out.println("• Fast price updates don't overwhelm UI");
        System.out.println("• Latest prices are always shown");
        System.out.println("• System remains responsive");

        System.out.println("\n=== Backpressure Strategy Selection ===");
        System.out.println("• DROP: When losing some data is acceptable");
        System.out.println("• LATEST: When only current state matters");
        System.out.println("• BUFFER: When you can afford memory usage");
        System.out.println("• ERROR: When data loss is unacceptable");
    }

    static class StockPrice {
        final String symbol;
        final double price;

        StockPrice(String symbol, double price) {
            this.symbol = symbol;
            this.price = price;
        }

        @Override
        public String toString() {
            return symbol + ": $" + String.format("%.2f", price);
        }
    }
}