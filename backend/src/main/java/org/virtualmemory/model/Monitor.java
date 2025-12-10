package org.virtualmemory.model;

import java.util.LinkedList;
import java.util.Queue;

public class Monitor {
    private boolean isLocked;
    private SimulatedThread owner;
    private Queue<SimulatedThread> waitQueue;

    public Monitor() {
        this.isLocked = false;
        this.owner = null;
        this.waitQueue = new LinkedList<>();
    }

    public synchronized boolean enter(SimulatedThread thread) {
        if (!isLocked) {
            isLocked = true;
            owner = thread;
            return true;
        }
        waitQueue.add(thread);
        return false;
    }

    public synchronized void exit() {
        isLocked = false;
        owner = null;
        if (!waitQueue.isEmpty()) {
            SimulatedThread next = waitQueue.poll();
            isLocked = true;
            owner = next;
        }
    }

    public boolean isLocked() {
        return isLocked;
    }

    public SimulatedThread getOwner() {
        return owner;
    }

    public int getWaitingThreads() {
        return waitQueue.size();
    }
}
