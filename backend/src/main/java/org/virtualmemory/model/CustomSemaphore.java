package org.virtualmemory.model;

public class CustomSemaphore {
    private int permits;
    private final int maxPermits;

    public CustomSemaphore(int permits) {
        this.permits = permits;
        this.maxPermits = permits;
    }

    public synchronized boolean tryAcquire() {
        if (permits > 0) {
            permits--;
            return true;
        }
        return false;
    }

    public synchronized void release() {
        if (permits < maxPermits) {
            permits++;
        }
    }

    public int getAvailablePermits() {
        return permits;
    }

    public int getMaxPermits() {
        return maxPermits;
    }
}
