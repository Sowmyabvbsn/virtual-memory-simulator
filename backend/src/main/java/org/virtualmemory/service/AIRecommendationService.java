package org.virtualmemory.service;

import org.springframework.stereotype.Service;
import org.virtualmemory.model.SimulatedThread;
import org.virtualmemory.model.ThreadState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AIRecommendationService {

    public String generateRecommendation(String simulationType, Map<String, Object> simulationData) {
        StringBuilder recommendation = new StringBuilder();

        switch (simulationType.toUpperCase()) {
            case "DEADLOCK":
                recommendation.append(analyzeDeadlock(simulationData));
                break;
            case "RACE_CONDITION":
                recommendation.append(analyzeRaceCondition(simulationData));
                break;
            case "PRODUCER_CONSUMER":
                recommendation.append(analyzeProducerConsumer(simulationData));
                break;
            case "THREAD_POOL":
                recommendation.append(analyzeThreadPool(simulationData));
                break;
            default:
                recommendation.append(getGeneralRecommendations());
        }

        return recommendation.toString();
    }

    private String analyzeDeadlock(Map<String, Object> data) {
        StringBuilder rec = new StringBuilder();
        rec.append("AI ANALYSIS - Deadlock Detected:\n\n");

        rec.append("Problem Identified:\n");
        rec.append("- Circular wait condition exists between threads\n");
        rec.append("- Resources are being held while waiting for others\n\n");

        rec.append("Recommendations:\n");
        rec.append("1. Use Lock Ordering: Always acquire locks in a consistent order\n");
        rec.append("2. Use Timeout: Implement tryLock() with timeout instead of blocking locks\n");
        rec.append("3. Use ReentrantLock: Consider using java.util.concurrent.locks.ReentrantLock\n");
        rec.append("4. Deadlock Detection: Implement periodic deadlock detection\n");
        rec.append("5. Avoid Nested Locks: Minimize holding multiple locks simultaneously\n\n");

        rec.append("Best Practice Code Pattern:\n");
        rec.append("```java\n");
        rec.append("// Always acquire locks in same order\n");
        rec.append("synchronized(lockA) {\n");
        rec.append("    synchronized(lockB) {\n");
        rec.append("        // critical section\n");
        rec.append("    }\n");
        rec.append("}\n");
        rec.append("```\n");

        return rec.toString();
    }

    private String analyzeRaceCondition(Map<String, Object> data) {
        StringBuilder rec = new StringBuilder();
        rec.append("AI ANALYSIS - Race Condition:\n\n");

        rec.append("Problem Identified:\n");
        rec.append("- Multiple threads accessing shared data without proper synchronization\n");
        rec.append("- Non-atomic operations on shared variables\n\n");

        rec.append("Recommendations:\n");
        rec.append("1. Use synchronized keyword for critical sections\n");
        rec.append("2. Use AtomicInteger/AtomicLong for counters\n");
        rec.append("3. Use volatile keyword for visibility\n");
        rec.append("4. Use java.util.concurrent.locks for fine-grained control\n");
        rec.append("5. Consider thread-safe collections (ConcurrentHashMap, etc.)\n\n");

        rec.append("Best Practice Code Pattern:\n");
        rec.append("```java\n");
        rec.append("// Using AtomicInteger\n");
        rec.append("private AtomicInteger counter = new AtomicInteger(0);\n");
        rec.append("public void increment() {\n");
        rec.append("    counter.incrementAndGet();\n");
        rec.append("}\n\n");
        rec.append("// Or using synchronized\n");
        rec.append("private int counter = 0;\n");
        rec.append("public synchronized void increment() {\n");
        rec.append("    counter++;\n");
        rec.append("}\n");
        rec.append("```\n");

        return rec.toString();
    }

    private String analyzeProducerConsumer(Map<String, Object> data) {
        StringBuilder rec = new StringBuilder();
        rec.append("AI ANALYSIS - Producer-Consumer Pattern:\n\n");

        rec.append("Recommendations:\n");
        rec.append("1. Use BlockingQueue: Best practice for producer-consumer\n");
        rec.append("2. Use ArrayBlockingQueue for bounded buffer\n");
        rec.append("3. Use LinkedBlockingQueue for unbounded buffer\n");
        rec.append("4. Handle InterruptedException properly\n");
        rec.append("5. Consider using ExecutorService for thread management\n\n");

        rec.append("Best Practice Code Pattern:\n");
        rec.append("```java\n");
        rec.append("BlockingQueue<Item> queue = new ArrayBlockingQueue<>(10);\n\n");
        rec.append("// Producer\n");
        rec.append("class Producer implements Runnable {\n");
        rec.append("    public void run() {\n");
        rec.append("        try {\n");
        rec.append("            queue.put(new Item());\n");
        rec.append("        } catch (InterruptedException e) {\n");
        rec.append("            Thread.currentThread().interrupt();\n");
        rec.append("        }\n");
        rec.append("    }\n");
        rec.append("}\n\n");
        rec.append("// Consumer\n");
        rec.append("class Consumer implements Runnable {\n");
        rec.append("    public void run() {\n");
        rec.append("        try {\n");
        rec.append("            Item item = queue.take();\n");
        rec.append("        } catch (InterruptedException e) {\n");
        rec.append("            Thread.currentThread().interrupt();\n");
        rec.append("        }\n");
        rec.append("    }\n");
        rec.append("}\n");
        rec.append("```\n");

        return rec.toString();
    }

    private String analyzeThreadPool(Map<String, Object> data) {
        StringBuilder rec = new StringBuilder();
        rec.append("AI ANALYSIS - Thread Pool Pattern:\n\n");

        rec.append("Recommendations:\n");
        rec.append("1. Use ExecutorService instead of manual thread management\n");
        rec.append("2. Choose appropriate pool type:\n");
        rec.append("   - FixedThreadPool: Fixed number of threads\n");
        rec.append("   - CachedThreadPool: Dynamic thread creation\n");
        rec.append("   - ScheduledThreadPool: For periodic tasks\n");
        rec.append("3. Always shutdown the pool when done\n");
        rec.append("4. Handle RejectedExecutionException\n");
        rec.append("5. Set appropriate queue size to prevent memory issues\n\n");

        rec.append("Best Practice Code Pattern:\n");
        rec.append("```java\n");
        rec.append("ExecutorService executor = Executors.newFixedThreadPool(5);\n\n");
        rec.append("// Submit tasks\n");
        rec.append("for (int i = 0; i < 10; i++) {\n");
        rec.append("    executor.submit(() -> {\n");
        rec.append("        // task logic\n");
        rec.append("    });\n");
        rec.append("}\n\n");
        rec.append("// Shutdown properly\n");
        rec.append("executor.shutdown();\n");
        rec.append("try {\n");
        rec.append("    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {\n");
        rec.append("        executor.shutdownNow();\n");
        rec.append("    }\n");
        rec.append("} catch (InterruptedException e) {\n");
        rec.append("    executor.shutdownNow();\n");
        rec.append("}\n");
        rec.append("```\n");

        return rec.toString();
    }

    private String getGeneralRecommendations() {
        return "AI RECOMMENDATIONS:\n\n" +
               "General Concurrency Best Practices:\n" +
               "1. Prefer immutability - use final fields when possible\n" +
               "2. Use thread-safe collections from java.util.concurrent\n" +
               "3. Minimize lock contention - keep critical sections small\n" +
               "4. Use higher-level concurrency utilities over low-level synchronization\n" +
               "5. Document thread-safety guarantees in your code\n" +
               "6. Use ThreadLocal for thread-specific data\n" +
               "7. Consider CompletableFuture for asynchronous operations\n" +
               "8. Always handle InterruptedException appropriately\n" +
               "9. Test with thread sanitizers and stress tests\n" +
               "10. Profile your application to identify concurrency bottlenecks";
    }

    public List<String> detectConcurrencyIssues(List<SimulatedThread> threads) {
        List<String> issues = new ArrayList<>();

        int blockedCount = 0;
        int runningCount = 0;

        for (SimulatedThread thread : threads) {
            if (thread.getState() == ThreadState.BLOCKED) {
                blockedCount++;
            } else if (thread.getState() == ThreadState.RUNNING) {
                runningCount++;
            }
        }

        if (blockedCount > threads.size() / 2) {
            issues.add("WARNING: More than 50% of threads are blocked - possible contention issue");
        }

        if (runningCount > Runtime.getRuntime().availableProcessors() * 2) {
            issues.add("WARNING: Too many running threads - consider using a thread pool");
        }

        return issues;
    }
}
