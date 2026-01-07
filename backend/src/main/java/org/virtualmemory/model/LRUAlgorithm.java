package org.virtualmemory.model;

import java.util.HashMap;
import java.util.Map;

public class LRUAlgorithm implements PageReplacementAlgorithm {
    private Map<Integer, Integer> lastUsedTime;
    private int timestamp;

    public LRUAlgorithm() {
        this.lastUsedTime = new HashMap<>();
        this.timestamp = 0;
    }

    @Override
    public int selectFrameToReplace(Page[] frames, int accessedPageId, int[] futureSequence, int futureIndex) {
        int lruFrame = -1;
        int oldestTime = Integer.MAX_VALUE;

        for (int i = 0; i < frames.length; i++) {
            if (frames[i] != null) {
                int time = lastUsedTime.getOrDefault(i, -1);
                if (time < oldestTime) {
                    oldestTime = time;
                    lruFrame = i;
                }
            }
        }

        return lruFrame;
    }

    @Override
    public void onPageAccess(int frameIndex, int pageId) {
        lastUsedTime.put(frameIndex, timestamp++);
    }

    @Override
    public void reset() {
        lastUsedTime.clear();
        timestamp = 0;
    }
}
