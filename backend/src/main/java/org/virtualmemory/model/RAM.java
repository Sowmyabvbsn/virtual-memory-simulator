package org.virtualmemory.model;

public class RAM {
    int size;
    Page[] frames;
    PageReplacementAlgorithm algorithm;

    public RAM(int size, PageReplacementAlgorithm algorithm) {
        this.size = size;
        this.frames = new Page[size];
        this.algorithm = algorithm;
    }

    public boolean isFull(){
        for(Page p : frames){
            if (p==null) return false;
        }
        return true;
    }

    public void addPage(Page p, int[] futureSequence, int futureIndex){
        if(!isFull()){
            for(int i=0;i<size;i++){
                if(frames[i]==null){
                    frames[i] = p;
                    p.inRAM = true;
                    p.frameIndex = i;
                    algorithm.onPageAccess(i, p.getId());
                    break;
                }
            }
        }else{
            int frameToReplace = algorithm.selectFrameToReplace(frames, p.getId(), futureSequence, futureIndex);
            Page oldPage = frames[frameToReplace];
            oldPage.inRAM = false;
            oldPage.frameIndex = -1;

            frames[frameToReplace] = p;
            p.inRAM = true;
            p.frameIndex = frameToReplace;
            algorithm.onPageAccess(frameToReplace, p.getId());
        }
    }

    public void notifyPageHit(int frameIndex, int pageId) {
        algorithm.onPageAccess(frameIndex, pageId);
    }

    public void printMemory() {
        System.out.print("RAM: ");
        for (Page p : frames) {
            if (p != null) System.out.print(p.id + " ");
            else System.out.print("- ");
        }
        System.out.println();
    }

    public Page[] getFrames() {
        return frames;
    }

    public int getSize() {
        return size;
    }
}
