package org.virtualmemory.model;

public class Resource {
    private int id;
    private String name;
    private boolean isLocked;
    private SimulatedThread holder;

    public Resource(int id, String name) {
        this.id = id;
        this.name = name;
        this.isLocked = false;
        this.holder = null;
    }

    public synchronized boolean tryAcquire(SimulatedThread thread) {
        if (!isLocked) {
            isLocked = true;
            holder = thread;
            return true;
        }
        return false;
    }

    public synchronized void release() {
        isLocked = false;
        holder = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public SimulatedThread getHolder() {
        return holder;
    }

    @Override
    public String toString() {
        return "Resource-" + id + " [" + name + "]" + (isLocked ? " (Locked by " + holder.getName() + ")" : " (Free)");
    }
}
