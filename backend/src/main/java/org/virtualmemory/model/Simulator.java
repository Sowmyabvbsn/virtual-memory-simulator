package org.virtualmemory.model;

public class Simulator {
    RAM memory;
    PageTable pageTable;
    int hits = 0;
    int faults = 0;
    int[] fullSequence;
    int currentIndex = 0;

    public Simulator(int numFrames, int numPages, String algorithmType) {
        PageReplacementAlgorithm algorithm = createAlgorithm(algorithmType);
        memory = new RAM(numFrames, algorithm);
        pageTable = new PageTable(numPages);
    }

    private PageReplacementAlgorithm createAlgorithm(String type) {
        switch (type.toUpperCase()) {
            case "LRU":
                return new LRUAlgorithm();
            case "MRU":
                return new MRUAlgorithm();
            case "OPTIMAL":
                return new OptimalAlgorithm();
            case "FIFO":
            default:
                return new FIFOAlgorithm();
        }
    }

    public void setSequence(int[] sequence) {
        this.fullSequence = sequence;
        this.currentIndex = 0;
    }

    public void accessPage(int pageId){
        Page p = pageTable.getPage(pageId);
        if (p.inRAM) {
            hits++;
            System.out.println("HIT: Page " + pageId);
            memory.notifyPageHit(p.frameIndex, pageId);
        } else {
            faults++;
            System.out.println("PAGE FAULT: Page " + pageId);
            memory.addPage(p, fullSequence, currentIndex + 1);
        }
        currentIndex++;
        memory.printMemory();
    }

    public void printStats() {
        System.out.println("Hits: " + hits + ", Page Faults: " + faults);
    }

    public RAM getMemory() {
        return memory;
    }

    public int getHits() {
        return hits;
    }

    public int getFaults() {
        return faults;
    }

    public PageTable getPageTable() {
        return pageTable;
    }
}
