package org.virtualmemory.model;

public class OptimalAlgorithm implements PageReplacementAlgorithm {

    @Override
    public int selectFrameToReplace(Page[] frames, int accessedPageId, int[] futureSequence, int futureIndex) {
        int farthestFrame = -1;
        int farthestDistance = -1;

        for (int i = 0; i < frames.length; i++) {
            if (frames[i] != null) {
                int pageId = frames[i].getId();
                int nextUseIndex = findNextUse(pageId, futureSequence, futureIndex);

                if (nextUseIndex == -1) {
                    return i;
                }

                if (nextUseIndex > farthestDistance) {
                    farthestDistance = nextUseIndex;
                    farthestFrame = i;
                }
            }
        }

        return farthestFrame;
    }

    private int findNextUse(int pageId, int[] futureSequence, int currentIndex) {
        for (int i = currentIndex; i < futureSequence.length; i++) {
            if (futureSequence[i] == pageId) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onPageAccess(int frameIndex, int pageId) {
    }

    @Override
    public void reset() {
    }
}
