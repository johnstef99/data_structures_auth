public class Ladder {
    private int ladderId;
    private int upStepId;
    private int downStepId;
    private boolean broken;

    Ladder() {
        this.broken = false;
        this.ladderId = 0;
        this.upStepId = 0;
        this.downStepId = 0;
    }

    Ladder(int ladderId, int upStepId, int downStepId, boolean broken) {
        this.ladderId = ladderId;
        this.upStepId = upStepId;
        this.downStepId = downStepId;
        this.broken = broken;
    }

    Ladder(Ladder aLadder) {
        this.ladderId = aLadder.ladderId;
        this.upStepId = aLadder.upStepId;
        this.downStepId = aLadder.downStepId;
        this.broken = aLadder.broken;
    }

    /**
     * @return the downStepId
     */
    public int getDownStepId() {
        return downStepId;
    }

    /**
     * @return the ladderId
     */
    public int getLadderId() {
        return ladderId;
    }

    /**
     * @return the upStepId
     */
    public int getUpStepId() {
        return upStepId;
    }

    public boolean getBroken() {
        return this.broken;
    }

    /**
     * @param broken the broken to set
     */
    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    /**
     * @param downStepId the downStepId to set
     */
    public void setDownStepId(int downStepId) {
        this.downStepId = downStepId;
    }

    /**
     * @param ladderId the ladderId to set
     */
    public void setLadderId(int ladderId) {
        this.ladderId = ladderId;
    }

    /**
     * @param upStepId the upStepId to set
     */
    public void setUpStepId(int upStepId) {
        this.upStepId = upStepId;
    }

}
