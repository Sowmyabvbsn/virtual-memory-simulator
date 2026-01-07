package org.virtualmemory.model;

import java.util.HashMap;
import java.util.Map;

public class MRUAlgorithm implements PageReplacementAlgorithm {
    private Map<Integer, Integer> lastUsedTime;
    private int timestamp;

    public MRUAlgorithm() {
        this.lastUsedTime = new HashMap<>();
        this.timestamp = 0;
    }

    @Override
    public int selectFrameToReplace(Page[] frames, int accessedPageId, int[] futureSequence, int futureIndex) {
        int mruFrame = -1;
        int newestTime = -1;

        for (int i = 0; i < frames.length; i++) {
            if (frames[i] != null) {
                int time = lastUsedTime.getOrDefault(i, -1);
                if (time > newestTime) {
                    newestTime = time;
                    mruFrame = i;
                }
            }
        }

        return mruFrame;
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
