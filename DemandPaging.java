import java.util.*;


public class DemandPaging {

    /**
     * Simulates a page replacement algorithm and returns the number of page faults.
     * 
     * @param algorithm The algorithm to use (FIFO, LRU, MRU, OPT)
     * @param pages The reference string (sequence of page accesses)
     * @param frameCount The number of frames available in memory
     * @return The total number of page faults
     */
    public static int simulate(String algorithm, int[] pages, int frameCount) {
        int faults = 0;
        List<Integer> frames = new ArrayList<>();
        Queue<Integer> fifoQueue = new LinkedList<>();

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            boolean hit = frames.contains(page);

            if (hit) {
                // Page hit - update for LRU/MRU if needed
                if ("LRU".equals(algorithm) || "MRU".equals(algorithm)) {
                    frames.remove(Integer.valueOf(page));
                    frames.add(page);
                }
            } else {
                // Page fault
                faults++;
                if (frames.size() < frameCount) {
                    // Still have empty frames
                    frames.add(page);
                } else {
                    // Need to evict a page
                    int removeIndex = switch (algorithm) {
                        case "FIFO" -> frames.indexOf(fifoQueue.poll());
                        case "OPT" -> findOptEviction(frames, pages, i);
                        case "MRU" -> frames.size() - 1;
                        case "LRU" -> 0;
                        default -> 0;
                    };
                    if (!algorithm.equals("FIFO")) {
                        fifoQueue.remove(frames.get(removeIndex));
                    }
                    frames.remove(removeIndex);
                    frames.add(page);
                }
                fifoQueue.add(page);
            }
        }

        return faults;
    }

    /**
     * Finds the optimal page to evict (the one that will be used farthest in the future).
     * 
     * @param frames Current frames in memory
     * @param pages The complete reference string
     * @param currentIndex Current position in the reference string
     * @return Index of the frame to evict
     */
    private static int findOptEviction(List<Integer> frames, int[] pages, int currentIndex) {
        int indexToRemove = 0;
        int farthest = -1;
        
        for (int j = 0; j < frames.size(); j++) {
            int curr = frames.get(j);
            int nextUse = Integer.MAX_VALUE;
            
            // Find when this page will be used next
            for (int k = currentIndex + 1; k < pages.length; k++) {
                if (pages[k] == curr) {
                    nextUse = k;
                    break;
                }
            }
            
            // Keep track of the page used farthest in the future
            if (nextUse > farthest) {
                farthest = nextUse;
                indexToRemove = j;
            }
        }
        
        return indexToRemove;
    }
}
