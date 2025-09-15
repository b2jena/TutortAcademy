package com.tutort.assignments.ReactiveJava.interview_prep;

/**
 * Part 5.1: SDE Interview Questions & Answers
 * <p>
 * Common reactive programming questions asked in FAANG interviews
 */
public class SDEInterviewQuestions {

    public static void main(String[] args) {
        System.out.println("=== Reactive Programming Interview Q&A ===\n");

        fundamentalQuestions();
        technicalQuestions();
        scenarioBasedQuestions();
        codingQuestions();
    }

    private static void fundamentalQuestions() {
        System.out.println("--- FUNDAMENTAL QUESTIONS ---");

        System.out.println("Q1: What is reactive programming?");
        System.out.println("A1: Programming paradigm focused on asynchronous data streams and");
        System.out.println("    the propagation of changes. Based on Observer pattern with");
        System.out.println("    backpressure handling for scalable, responsive applications.");

        System.out.println("\nQ2: What are the four pillars of reactive systems?");
        System.out.println("A2: 1. RESPONSIVE - React to users quickly");
        System.out.println("    2. RESILIENT - React to failure gracefully");
        System.out.println("    3. ELASTIC - React to load by scaling");
        System.out.println("    4. MESSAGE-DRIVEN - React via async messages");

        System.out.println("\nQ3: What is backpressure and why is it important?");
        System.out.println("A3: Backpressure occurs when producer is faster than consumer.");
        System.out.println("    Important because it prevents memory exhaustion and system");
        System.out.println("    instability. Handled via strategies: drop, buffer, error, latest.");

        System.out.println("\nQ4: Difference between Mono and Flux?");
        System.out.println("A4: • Mono: 0 or 1 item (like CompletableFuture<Optional<T>>)");
        System.out.println("    • Flux: 0 to N items (like reactive Stream<T>)");
        System.out.println("    Both are lazy and support backpressure.");

        System.out.println();
    }

    private static void technicalQuestions() {
        System.out.println("--- TECHNICAL QUESTIONS ---");

        System.out.println("Q5: What's the difference between hot and cold publishers?");
        System.out.println("A5: • COLD: Starts emitting when subscribed (unicast)");
        System.out.println("       Example: HTTP request, database query");
        System.out.println("    • HOT: Emits regardless of subscribers (multicast)");
        System.out.println("       Example: Mouse clicks, stock prices, sensors");

        System.out.println("\nQ6: Explain subscribeOn vs publishOn");
        System.out.println("A6: • subscribeOn: Controls WHERE subscription happens (affects upstream)");
        System.out.println("    • publishOn: Controls WHERE downstream operations happen");
        System.out.println("    Example: subscribeOn(elastic) for I/O, publishOn(parallel) for CPU");

        System.out.println("\nQ7: When would you use flatMap vs map?");
        System.out.println("A7: • map: 1-to-1 synchronous transformation (item -> item)");
        System.out.println("    • flatMap: 1-to-N async transformation (item -> Publisher<item>)");
        System.out.println("    Use flatMap for chaining async operations like API calls.");

        System.out.println("\nQ8: How do you handle errors in reactive streams?");
        System.out.println("A8: • onErrorReturn: Provide fallback value");
        System.out.println("    • onErrorResume: Switch to alternative stream");
        System.out.println("    • retry: Retry failed operation with backoff");
        System.out.println("    • doOnError: Side effects (logging, metrics)");

        System.out.println("\nQ9: What are the Reactive Streams interfaces?");
        System.out.println("A9: 1. Publisher<T> - Produces data");
        System.out.println("    2. Subscriber<T> - Consumes data");
        System.out.println("    3. Subscription - Controls flow (request/cancel)");
        System.out.println("    4. Processor<T,R> - Both publisher and subscriber");

        System.out.println();
    }

    private static void scenarioBasedQuestions() {
        System.out.println("--- SCENARIO-BASED QUESTIONS ---");

        System.out.println("Q10: How would you implement a rate limiter using reactive streams?");
        System.out.println("A10: Use delayElements() or custom operator with Scheduler:");
        System.out.println("     flux.delayElements(Duration.ofMillis(100)) // 10 requests/second");
        System.out.println("     Or use Semaphore with flatMap for token bucket approach.");

        System.out.println("\nQ11: Design a reactive cache that expires entries");
        System.out.println("A11: Combine Mono.cache() with timeout:");
        System.out.println("     Mono.fromCallable(() -> expensiveCall())");
        System.out.println("         .cache(Duration.ofMinutes(5))");
        System.out.println("         .timeout(Duration.ofSeconds(30))");

        System.out.println("\nQ12: How to handle slow consumers in a real-time system?");
        System.out.println("A12: Apply backpressure strategies:");
        System.out.println("     • onBackpressureDrop() - Drop old events");
        System.out.println("     • onBackpressureLatest() - Keep only latest");
        System.out.println("     • onBackpressureBuffer(1000) - Buffer with limit");

        System.out.println("\nQ13: Implement circuit breaker pattern reactively");
        System.out.println("A13: Use retry with exponential backoff and error counting:");
        System.out.println("     mono.retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))");
        System.out.println("         .onErrorReturn(fallbackValue)");

        System.out.println();
    }

    private static void codingQuestions() {
        System.out.println("--- CODING QUESTIONS ---");

        System.out.println("Q14: Write code to fetch user profile and posts in parallel");
        System.out.println("A14:");
        System.out.println("Mono<User> user = userService.getUser(id);");
        System.out.println("Mono<List<Post>> posts = postService.getUserPosts(id);");
        System.out.println("");
        System.out.println("Mono<UserProfile> profile = Mono.zip(user, posts)");
        System.out.println("    .map(tuple -> new UserProfile(tuple.getT1(), tuple.getT2()));");

        System.out.println("\nQ15: Transform blocking JDBC call to reactive");
        System.out.println("A15:");
        System.out.println("Mono<User> getUser(int id) {");
        System.out.println("    return Mono.fromCallable(() -> {");
        System.out.println("        // JDBC blocking call");
        System.out.println("        return jdbcTemplate.queryForObject(sql, User.class, id);");
        System.out.println("    }).subscribeOn(Schedulers.boundedElastic());");
        System.out.println("}");

        System.out.println("\nQ16: Implement retry with exponential backoff");
        System.out.println("A16:");
        System.out.println("mono.retryWhen(Retry.backoff(3, Duration.ofSeconds(1))");
        System.out.println("    .maxBackoff(Duration.ofSeconds(10))");
        System.out.println("    .jitter(0.5)");
        System.out.println("    .filter(throwable -> throwable instanceof RetryableException))");

        System.out.println("\nQ17: Create a reactive pipeline for processing files");
        System.out.println("A17:");
        System.out.println("Flux.fromIterable(fileList)");
        System.out.println("    .flatMap(file -> readFile(file)");
        System.out.println("        .subscribeOn(Schedulers.boundedElastic()), 5) // Concurrency");
        System.out.println("    .map(content -> processContent(content))");
        System.out.println("    .publishOn(Schedulers.parallel())");
        System.out.println("    .buffer(100) // Batch processing");
        System.out.println("    .flatMap(batch -> saveBatch(batch))");

        System.out.println("\n=== INTERVIEW TIPS ===");
        System.out.println("1. Always mention lazy evaluation");
        System.out.println("2. Discuss scheduler selection rationale");
        System.out.println("3. Consider error handling and backpressure");
        System.out.println("4. Explain performance implications");
        System.out.println("5. Compare with traditional approaches");
        System.out.println("6. Mention testing strategies (StepVerifier)");

        System.out.println("\n=== RED FLAGS TO AVOID ===");
        System.out.println("❌ Blocking on reactive threads");
        System.out.println("❌ Not handling backpressure");
        System.out.println("❌ Ignoring error scenarios");
        System.out.println("❌ Using reactive for simple CRUD");
        System.out.println("❌ Not considering scheduler implications");
    }
}