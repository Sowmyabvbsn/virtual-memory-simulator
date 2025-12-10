package org.virtualmemory.model;

import java.util.*;

public class ConcurrencySimulator {
    private List<SimulatedThread> threads;
    private List<Resource> resources;
    private List<String> executionLog;
    private boolean deadlockDetected;
    private List<SimulatedThread> deadlockedThreads;

    public ConcurrencySimulator() {
        this.threads = new ArrayList<>();
        this.resources = new ArrayList<>();
        this.executionLog = new ArrayList<>();
        this.deadlockDetected = false;
        this.deadlockedThreads = new ArrayList<>();
    }

    public SimulatedThread createThread(String name, int priority) {
        SimulatedThread thread = new SimulatedThread(threads.size(), name, priority);
        threads.add(thread);
        log("Created " + thread.toString());
        return thread;
    }

    public Resource createResource(String name) {
        Resource resource = new Resource(resources.size(), name);
        resources.add(resource);
        log("Created " + resource.toString());
        return resource;
    }

    public void threadRequestsResource(SimulatedThread thread, Resource resource) {
        log(thread.getName() + " requests " + resource.getName());

        if (resource.tryAcquire(thread)) {
            thread.acquireResource(resource);
            thread.setState(ThreadState.RUNNING);
            log(thread.getName() + " acquired " + resource.getName());
        } else {
            thread.waitForResource(resource);
            thread.setState(ThreadState.BLOCKED);
            log(thread.getName() + " is blocked waiting for " + resource.getName());

            detectDeadlock();
        }
    }

    public void threadReleasesResource(SimulatedThread thread, Resource resource) {
        if (thread.getHeldResources().contains(resource)) {
            thread.releaseResource(resource);
            resource.release();
            log(thread.getName() + " released " + resource.getName());

            for (SimulatedThread waitingThread : threads) {
                if (waitingThread.getWaitingForResources().contains(resource)) {
                    threadRequestsResource(waitingThread, resource);
                    break;
                }
            }
        }
    }

    public void detectDeadlock() {
        for (SimulatedThread thread : threads) {
            if (thread.getState() == ThreadState.BLOCKED) {
                Set<SimulatedThread> visited = new HashSet<>();
                if (hasCycle(thread, visited)) {
                    deadlockDetected = true;
                    deadlockedThreads = new ArrayList<>(visited);
                    log("DEADLOCK DETECTED involving: " + getThreadNames(deadlockedThreads));
                    return;
                }
            }
        }
    }

    private boolean hasCycle(SimulatedThread thread, Set<SimulatedThread> visited) {
        if (visited.contains(thread)) {
            return true;
        }

        visited.add(thread);

        for (Resource waitingResource : thread.getWaitingForResources()) {
            if (waitingResource.getHolder() != null) {
                if (hasCycle(waitingResource.getHolder(), visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    private String getThreadNames(List<SimulatedThread> threadList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < threadList.size(); i++) {
            sb.append(threadList.get(i).getName());
            if (i < threadList.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public void simulateRaceCondition(int numThreads, int sharedCounter) {
        log("Simulating Race Condition with " + numThreads + " threads");

        for (int i = 0; i < numThreads; i++) {
            SimulatedThread thread = createThread("Thread-" + i, 5);
            thread.setState(ThreadState.RUNNABLE);
            thread.setState(ThreadState.RUNNING);
            thread.setState(ThreadState.TERMINATED);
        }

        log("Race condition simulation completed");
    }

    public void simulateProducerConsumer(int producers, int consumers, int bufferSize) {
        log("Simulating Producer-Consumer with " + producers + " producers, " +
            consumers + " consumers, buffer size: " + bufferSize);

        Resource buffer = createResource("Buffer");

        for (int i = 0; i < producers; i++) {
            SimulatedThread producer = createThread("Producer-" + i, 5);
            producer.setState(ThreadState.RUNNABLE);
        }

        for (int i = 0; i < consumers; i++) {
            SimulatedThread consumer = createThread("Consumer-" + i, 5);
            consumer.setState(ThreadState.RUNNABLE);
        }

        log("Producer-Consumer simulation completed");
    }

    public void simulateDeadlock() {
        log("Simulating Deadlock Scenario");

        Resource r1 = createResource("Resource-A");
        Resource r2 = createResource("Resource-B");

        SimulatedThread t1 = createThread("Thread-1", 5);
        SimulatedThread t2 = createThread("Thread-2", 5);

        t1.setState(ThreadState.RUNNABLE);
        t2.setState(ThreadState.RUNNABLE);

        threadRequestsResource(t1, r1);
        threadRequestsResource(t2, r2);

        threadRequestsResource(t1, r2);
        threadRequestsResource(t2, r1);

        log("Deadlock simulation completed");
    }

    public void simulateThreadPool(int poolSize, int taskCount) {
        log("Simulating Thread Pool with " + poolSize + " threads and " + taskCount + " tasks");

        for (int i = 0; i < poolSize; i++) {
            SimulatedThread thread = createThread("PoolThread-" + i, 5);
            thread.setState(ThreadState.WAITING);
        }

        for (int i = 0; i < taskCount; i++) {
            int threadIndex = i % poolSize;
            SimulatedThread thread = threads.get(threadIndex);
            thread.setState(ThreadState.RUNNING);
            thread.setExecutionTime(100 + (int)(Math.random() * 200));
            thread.setState(ThreadState.WAITING);
        }

        log("Thread Pool simulation completed");
    }

    private void log(String message) {
        executionLog.add(message);
        System.out.println("[SIMULATION] " + message);
    }

    public List<SimulatedThread> getThreads() {
        return threads;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public List<String> getExecutionLog() {
        return executionLog;
    }

    public boolean isDeadlockDetected() {
        return deadlockDetected;
    }

    public List<SimulatedThread> getDeadlockedThreads() {
        return deadlockedThreads;
    }

    public Map<String, Object> getSimulationState() {
        Map<String, Object> state = new HashMap<>();

        List<Map<String, Object>> threadStates = new ArrayList<>();
        for (SimulatedThread thread : threads) {
            Map<String, Object> threadInfo = new HashMap<>();
            threadInfo.put("id", thread.getId());
            threadInfo.put("name", thread.getName());
            threadInfo.put("state", thread.getState().toString());
            threadInfo.put("priority", thread.getPriority());
            threadInfo.put("heldResources", thread.getHeldResources().size());
            threadInfo.put("waitingForResources", thread.getWaitingForResources().size());
            threadStates.add(threadInfo);
        }

        List<Map<String, Object>> resourceStates = new ArrayList<>();
        for (Resource resource : resources) {
            Map<String, Object> resourceInfo = new HashMap<>();
            resourceInfo.put("id", resource.getId());
            resourceInfo.put("name", resource.getName());
            resourceInfo.put("locked", resource.isLocked());
            resourceInfo.put("holder", resource.getHolder() != null ? resource.getHolder().getName() : null);
            resourceStates.add(resourceInfo);
        }

        state.put("threads", threadStates);
        state.put("resources", resourceStates);
        state.put("deadlockDetected", deadlockDetected);
        state.put("executionLog", executionLog);

        return state;
    }
}
