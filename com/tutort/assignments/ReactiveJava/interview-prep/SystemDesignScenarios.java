package com.tutort.assignments.ReactiveJava.interview_prep;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Part 5.2: System Design Scenarios
 * <p>
 * Real-world system design problems that benefit from reactive programming
 */
public class SystemDesignScenarios {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Reactive System Design Scenarios ===\n");

        realTimeNotificationService();
        stockTradingPlatform();
        chatApplication();
        iotDataProcessing();
    }

    /**
     * Scenario 1: Real-time Notification Service
     * Requirements: Handle millions of users, real-time delivery, scalable
     */
    private static void realTimeNotificationService() throws InterruptedException {
        System.out.println("--- SCENARIO 1: Real-time Notification Service ---");

        System.out.println("REQUIREMENTS:");
        System.out.println("• Handle 10M+ concurrent users");
        System.out.println("• Real-time push notifications");
        System.out.println("• Multiple channels (email, SMS, push)");
        System.out.println("• High availability and fault tolerance");

        System.out.println("\nHLD (High-Level Design):");
        System.out.println("┌─────────────┐    ┌──────────────┐    ┌─────────────┐");
        System.out.println("│   Clients   │───▶│ Load Balancer│───▶│ API Gateway │");
        System.out.println("└─────────────┘    └──────────────┘    └─────────────┘");
        System.out.println("                                              │");
        System.out.println("                   ┌─────────────────────────────┼─────────────────────────────┐");
        System.out.println("                   ▼                             ▼                             ▼");
        System.out.println("            ┌─────────────┐              ┌─────────────┐              ┌─────────────┐");
        System.out.println("            │Notification │              │ WebSocket   │              │ Event Store │");
        System.out.println("            │  Service    │              │  Service    │              │   (Kafka)   │");
        System.out.println("            └─────────────┘              └─────────────┘              └─────────────┘");

        System.out.println("\nWHY REACTIVE?");
        System.out.println("• Non-blocking I/O for millions of connections");
        System.out.println("• Backpressure handling for burst traffic");
        System.out.println("• Event-driven architecture");
        System.out.println("• Efficient resource utilization");

        // Simulate reactive notification service
        NotificationService notificationService = new NotificationService();
        notificationService.demonstrateReactiveFlow();

        System.out.println();
    }

    /**
     * Scenario 2: Stock Trading Platform
     * Requirements: Low latency, high throughput, real-time price feeds
     */
    private static void stockTradingPlatform() throws InterruptedException {
        System.out.println("--- SCENARIO 2: Stock Trading Platform ---");

        System.out.println("REQUIREMENTS:");
        System.out.println("• Sub-millisecond latency for trades");
        System.out.println("• Handle 1M+ trades per second");
        System.out.println("• Real-time market data streaming");
        System.out.println("• Risk management and compliance");

        System.out.println("\nHLD (High-Level Design):");
        System.out.println("┌─────────────┐    ┌──────────────┐    ┌─────────────┐");
        System.out.println("│ Trading UI  │───▶│   WebSocket  │───▶│ Price Feed  │");
        System.out.println("└─────────────┘    └──────────────┘    └─────────────┘");
        System.out.println("                                              │");
        System.out.println("                   ┌─────────────────────────────┼─────────────────────────────┐");
        System.out.println("                   ▼                             ▼                             ▼");
        System.out.println("            ┌─────────────┐              ┌─────────────┐              ┌─────────────┐");
        System.out.println("            │Order Engine │              │Risk Manager │              │ Match Engine│");
        System.out.println("            └─────────────┘              └─────────────┘              └─────────────┘");

        System.out.println("\nWHY REACTIVE?");
        System.out.println("• Low-latency event processing");
        System.out.println("• Backpressure for market data bursts");
        System.out.println("• Non-blocking order processing");
        System.out.println("• Real-time risk calculations");

        // Simulate reactive trading platform
        TradingPlatform tradingPlatform = new TradingPlatform();
        tradingPlatform.demonstrateReactiveTrading();

        System.out.println();
    }

    /**
     * Scenario 3: Real-time Chat Application
     * Requirements: Instant messaging, presence, message history
     */
    private static void chatApplication() throws InterruptedException {
        System.out.println("--- SCENARIO 3: Real-time Chat Application ---");

        System.out.println("REQUIREMENTS:");
        System.out.println("• Instant message delivery");
        System.out.println("• User presence tracking");
        System.out.println("• Message history and search");
        System.out.println("• File sharing and media");

        System.out.println("\nLLD (Low-Level Design) - Message Flow:");
        System.out.println("User A ──send──▶ Message Service ──publish──▶ Message Broker");
        System.out.println("                       │                           │");
        System.out.println("                   ┌───store───┐               ┌──subscribe──┐");
        System.out.println("                   ▼           ▼               ▼             ▼");
        System.out.println("              Database    Search Index    User B        User C");

        System.out.println("\nREACTIVE COMPONENTS:");
        System.out.println("• WebSocket connections (Flux<Message>)");
        System.out.println("• Message streaming with backpressure");
        System.out.println("• Presence updates (hot publisher)");
        System.out.println("• Async message persistence");

        // Simulate reactive chat
        ChatApplication chatApp = new ChatApplication();
        chatApp.demonstrateReactiveChat();

        System.out.println();
    }

    /**
     * Scenario 4: IoT Data Processing Pipeline
     * Requirements: Handle sensor data, real-time analytics, alerting
     */
    private static void iotDataProcessing() throws InterruptedException {
        System.out.println("--- SCENARIO 4: IoT Data Processing Pipeline ---");

        System.out.println("REQUIREMENTS:");
        System.out.println("• Process 1M+ sensor readings/second");
        System.out.println("• Real-time anomaly detection");
        System.out.println("• Data aggregation and analytics");
        System.out.println("• Alert generation and routing");

        System.out.println("\nDATA FLOW:");
        System.out.println("Sensors ──▶ Ingestion ──▶ Processing ──▶ Analytics ──▶ Alerts");
        System.out.println("   │           │             │             │            │");
        System.out.println("   │           ▼             ▼             ▼            ▼");
        System.out.println("   └──────▶ Raw Data ──▶ Enriched ──▶ Aggregated ──▶ Actions");

        System.out.println("\nREACTIVE PIPELINE:");
        System.out.println("• Sensor data streams (Flux<SensorReading>)");
        System.out.println("• Windowing and aggregation");
        System.out.println("• Parallel processing with backpressure");
        System.out.println("• Event-driven alerting");

        // Simulate IoT processing
        IoTProcessor iotProcessor = new IoTProcessor();
        iotProcessor.demonstrateReactiveProcessing();

        System.out.println("\n=== SYSTEM DESIGN INTERVIEW TIPS ===");
        System.out.println("1. START WITH REQUIREMENTS");
        System.out.println("   • Clarify scale, latency, consistency needs");
        System.out.println("   • Identify reactive vs traditional trade-offs");

        System.out.println("\n2. JUSTIFY REACTIVE CHOICE");
        System.out.println("   • High concurrency requirements");
        System.out.println("   • Real-time data processing");
        System.out.println("   • Resource efficiency needs");
        System.out.println("   • Event-driven architecture");

        System.out.println("\n3. ADDRESS CHALLENGES");
        System.out.println("   • Debugging complexity");
        System.out.println("   • Learning curve");
        System.out.println("   • Testing strategies");
        System.out.println("   • Monitoring and observability");

        System.out.println("\n4. DISCUSS ALTERNATIVES");
        System.out.println("   • When NOT to use reactive");
        System.out.println("   • Hybrid approaches");
        System.out.println("   • Migration strategies");
    }

    // Supporting classes for demonstrations
    static class NotificationService {
        void demonstrateReactiveFlow() throws InterruptedException {
            System.out.println("\nReactive Notification Flow:");

            BlockingQueue<String> notifications = new ArrayBlockingQueue<>(1000);
            AtomicLong processed = new AtomicLong(0);

            // Producer (notification events)
            Thread producer = new Thread(() -> {
                for (int i = 1; i <= 10; i++) {
                    try {
                        notifications.put("Notification-" + i);
                        System.out.println("  Queued: Notification-" + i);
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });

            // Consumer (reactive processing)
            Thread consumer = new Thread(() -> {
                try {
                    while (processed.get() < 10) {
                        String notification = notifications.poll(1, TimeUnit.SECONDS);
                        if (notification != null) {
                            System.out.println("  Processed: " + notification);
                            processed.incrementAndGet();
                            Thread.sleep(30); // Simulate processing
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            producer.start();
            consumer.start();
            producer.join();
            consumer.join();

            System.out.println("  Total processed: " + processed.get());
        }
    }

    static class TradingPlatform {
        void demonstrateReactiveTrading() throws InterruptedException {
            System.out.println("\nReactive Trading Flow:");

            // Simulate price updates and order processing
            for (int i = 1; i <= 5; i++) {
                double price = 100.0 + i;
                System.out.println("  Price update: AAPL $" + price);
                System.out.println("  Order processed: BUY 100 shares at $" + price);
                Thread.sleep(100);
            }
        }
    }

    static class ChatApplication {
        void demonstrateReactiveChat() throws InterruptedException {
            System.out.println("\nReactive Chat Flow:");

            String[] messages = {"Hello!", "How are you?", "Great weather today"};
            for (String message : messages) {
                System.out.println("  User A: " + message);
                System.out.println("  → Delivered to User B, User C");
                Thread.sleep(200);
            }
        }
    }

    static class IoTProcessor {
        void demonstrateReactiveProcessing() throws InterruptedException {
            System.out.println("\nReactive IoT Processing:");

            for (int i = 1; i <= 5; i++) {
                double temperature = 20.0 + (Math.random() * 10);
                System.out.println("  Sensor reading: " + String.format("%.1f°C", temperature));

                if (temperature > 25.0) {
                    System.out.println("  → ALERT: High temperature detected!");
                }

                Thread.sleep(150);
            }
        }
    }
}