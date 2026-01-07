package org.virtualmemory.model;

public interface PageReplacementAlgorithm {
    int selectFrameToReplace(Page[] frames, int accessedPageId, int[] futureSequence, int futureIndex);
    void onPageAccess(int frameIndex, int pageId);
    void reset();
}
