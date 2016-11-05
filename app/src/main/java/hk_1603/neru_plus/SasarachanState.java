package hk_1603.neru_plus;

public abstract class SasarachanState {
    protected Sasarachan sasarachan;
    protected int rejectCount;
    protected long startTime;
    protected long endTime;
    protected int secTime;

    public SasarachanState(Sasarachan sasarachan) {
        this.sasarachan = sasarachan;
        this.rejectCount = 0;
        this.startTime = -1;
        this.endTime = -1;
        this.secTime = -1;
    }

    public abstract void proposeAction();
    public abstract void continuePropose();
    public abstract void greet();
    public abstract void praise();
    public abstract void scold();
    public abstract void reply(String utteranceText);
}
