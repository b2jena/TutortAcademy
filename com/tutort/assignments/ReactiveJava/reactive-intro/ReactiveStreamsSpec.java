package com.tutort.assignments.ReactiveJava.reactive_intro;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

/**
 * Part 2.2: Reactive Streams Specification
 * <p>
 * Four Core Interfaces:
 * 1. Publisher<T> - Produces data
 * 2. Subscriber<T> - Consumes data
 * 3. Subscription - Controls flow between Publisher and Subscriber
 * 4. Processor<T,R> - Both Publisher and Subscriber (transformation)
 */
public class ReactiveStreamsSpec {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Reactive Streams Specification ===\n");

        demonstrateBasicFlow();
        demonstrateBackpressure();
        demonstrateProcessor();
        explainSpecification();
    }

    /**
     * Basic Publisher-Subscriber interaction
     * Shows the fundamental reactive streams flow
     */
    private static void demonstrateBasicFlow() throws InterruptedException {
        System.out.println("--- Basic Publisher-Subscriber Flow ---");

        // Publisher: Produces data stream
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // Subscriber: Consumes data stream
        Subscriber<String> subscriber = new Subscriber<String>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                System.out.println("Subscribed! Requesting 1 item...");
                subscription.request(1); // Request first item
            }

            @Override
            public void onNext(String item) {
                System.out.println("Received: " + item);
                // Request next item (pull-based)
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Stream completed!");
            }
        };

        // Connect publisher and subscriber
        publisher.subscribe(subscriber);

        // Publish data
        publisher.submit("Item 1");
        publisher.submit("Item 2");
        publisher.submit("Item 3");

        // Close publisher (triggers onComplete)
        publisher.close();

        Thread.sleep(1000);
        System.out.println();
    }

    /**
     * Demonstrates backpressure handling
     * Subscriber controls the flow rate
     */
    private static void demonstrateBackpressure() throws InterruptedException {
        System.out.println("--- Backpressure Demo ---");
        System.out.println("Subscriber controls flow to prevent overwhelming\n");

        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        // Slow subscriber that processes items with delay
        Subscriber<Integer> slowSubscriber = new Subscriber<Integer>() {
            private Subscription subscription;
            private int processed = 0;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                System.out.println("Slow subscriber connected");
                subscription.request(2); // Request only 2 items initially
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("Processing item: " + item + " (slow)");
                try {
                    Thread.sleep(500); // Simulate slow processing
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                processed++;
                if (processed < 5) {
                    subscription.request(1); // Request next item when ready
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Slow subscriber completed");
            }
        };

        publisher.subscribe(slowSubscriber);

        // Publisher tries to send many items quickly
        System.out.println("Publisher sending items rapidly...");
        for (int i = 1; i <= 5; i++) {
            publisher.submit(i);
            System.out.println("Submitted: " + i);
        }

        publisher.close();
        Thread.sleep(3000);
        System.out.println();
    }

    /**
     * Demonstrates Processor - both Publisher and Subscriber
     * Transforms data in the stream
     */
    private static void demonstrateProcessor() throws InterruptedException {
        System.out.println("--- Processor Demo (Transformation) ---");

        SubmissionPublisher<String> source = new SubmissionPublisher<>();

        // Processor that transforms strings to uppercase
        SubmissionPublisher<String> processor = new SubmissionPublisher<String>() {
            {
                // This processor subscribes to source
                source.subscribe(new Subscriber<String>() {
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        subscription.request(Long.MAX_VALUE); // Request all
                    }

                    @Override
                    public void onNext(String item) {
                        // Transform and forward
                        String transformed = item.toUpperCase();
                        System.out.println("Processor: " + item + " -> " + transformed);
                        submit(transformed);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        closeExceptionally(throwable);
                    }

                    @Override
                    public void onComplete() {
                        close();
                    }
                });
            }
        };

        // Final subscriber
        processor.subscribe(new Subscriber<String>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String item) {
                System.out.println("Final result: " + item);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Processing pipeline completed");
            }
        });

        // Send data through the pipeline
        source.submit("hello");
        source.submit("reactive");
        source.submit("world");
        source.close();

        Thread.sleep(1000);
        System.out.println();
    }

    /**
     * Explains the Reactive Streams specification
     */
    private static void explainSpecification() {
        System.out.println("--- Reactive Streams Specification Summary ---");
        System.out.println();

        System.out.println("1. PUBLISHER<T>");
        System.out.println("   • Produces unlimited sequence of elements");
        System.out.println("   • subscribe(Subscriber) - connects to subscriber");
        System.out.println();

        System.out.println("2. SUBSCRIBER<T>");
        System.out.println("   • onSubscribe(Subscription) - receives subscription");
        System.out.println("   • onNext(T) - receives data items");
        System.out.println("   • onError(Throwable) - receives error signal");
        System.out.println("   • onComplete() - receives completion signal");
        System.out.println();

        System.out.println("3. SUBSCRIPTION");
        System.out.println("   • request(long) - requests n items (backpressure)");
        System.out.println("   • cancel() - cancels subscription");
        System.out.println();

        System.out.println("4. PROCESSOR<T,R>");
        System.out.println("   • Extends both Publisher<R> and Subscriber<T>");
        System.out.println("   • Transforms data in the stream");
        System.out.println();

        System.out.println("KEY PRINCIPLES:");
        System.out.println("• Asynchronous processing");
        System.out.println("• Non-blocking backpressure");
        System.out.println("• Minimal resource consumption");
        System.out.println("• Push-pull hybrid model");
    }
}