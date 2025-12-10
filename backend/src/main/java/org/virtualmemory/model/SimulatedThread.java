package org.virtualmemory.model;

import java.util.ArrayList;
import java.util.List;

public class SimulatedThread {
    private int id;
    private String name;
    private ThreadState state;
    private int priority;
    private List<Resource> heldResources;
    private List<Resource> waitingForResources;
    private long startTime;
    private long endTime;
    private int executionTime;

    public SimulatedThread(int id, String name, int priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.state = ThreadState.NEW;
        this.heldResources = new ArrayList<>();
        this.waitingForResources = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
    }

    public void setState(ThreadState state) {
        this.state = state;
        if (state == ThreadState.TERMINATED) {
            this.endTime = System.currentTimeMillis();
        }
    }

    public void acquireResource(Resource resource) {
        this.heldResources.add(resource);
        this.waitingForResources.remove(resource);
    }

    public void releaseResource(Resource resource) {
        this.heldResources.remove(resource);
    }

    public void waitForResource(Resource resource) {
        this.waitingForResources.add(resource);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ThreadState getState() {
        return state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Resource> getHeldResources() {
        return heldResources;
    }

    public List<Resource> getWaitingForResources() {
        return waitingForResources;
    }

    public long getExecutionTimeMs() {
        if (endTime > 0) {
            return endTime - startTime;
        }
        return System.currentTimeMillis() - startTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    @Override
    public String toString() {
        return "Thread-" + id + " [" + name + "] - State: " + state +
               " | Priority: " + priority +
               " | Held: " + heldResources.size() +
               " | Waiting: " + waitingForResources.size();
    }
}
