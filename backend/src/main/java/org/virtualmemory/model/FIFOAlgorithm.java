package org.virtualmemory.model;

import java.util.LinkedList;
import java.util.Queue;

public class FIFOAlgorithm implements PageReplacementAlgorithm {
    private Queue<Integer> fifoQueue;

    public FIFOAlgorithm() {
        this.fifoQueue = new LinkedList<>();
    }

    @Override
    public int selectFrameToReplace(Page[] frames, int accessedPageId, int[] futureSequence, int futureIndex) {
        return fifoQueue.poll();
    }

    @Override
    public void onPageAccess(int frameIndex, int pageId) {
        fifoQueue.add(frameIndex);
    }

    @Override
    public void reset() {
        fifoQueue.clear();
    }
}
