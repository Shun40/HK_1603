package hk_1603.neru_plus;

public abstract class SasarachanState {
    protected Sasarachan sasarachan; // 状態を持つささらちゃん
    protected int rejectCount; // ユーザに行動を拒否された回数
    protected long startTime;
    protected long endTime;
    protected int secTime; // 起きるまで or 寝るまでにかかった時間[sec]

    public SasarachanState(Sasarachan sasarachan) {
        this.sasarachan = sasarachan;
        this.rejectCount = 0;
        this.startTime = -1;
        this.endTime = -1;
        this.secTime = -1;
    }

    public abstract void proposeAction(); // ユーザに起床 or 就寝を促す
    public abstract void continuePropose(); // ユーザが促しを拒否した場合に粘り強く行動を促す
    public abstract void greet(); // 起床時 or 就寝時のあいさつ
    public abstract void praise(); // ユーザの規則正しい行動を褒める
    public abstract void scold(); // ユーザの規則正しくない行動を叱る
    public abstract void reply(String utteranceText); // ユーザから返答をもらう
}
