package hk_1603.neru_plus;

public class DemoState extends SasarachanState {
    // 音声と画像素材の配列定数
    private static final int[] DEMO_UTTERANCES = {
            R.raw.demo
    };
    private static final int[] DEMO_EXPRESSIONS_0 = {
            R.drawable.smile_3
    };
    private static final int[] DEMO_EXPRESSIONS_1 = {
            R.drawable.base
    };

    public DemoState(Sasarachan sasarachan) {
        super(sasarachan);
    }

    public void proposeAction() {
        sasarachan.changeFace(DEMO_EXPRESSIONS_0[0]);
        sasarachan.setConversationPhase(0);
    }

    public void continuePropose() {
        sasarachan.changeFace(DEMO_EXPRESSIONS_0[0]);
        sasarachan.setConversationPhase(1);
    }

    public void greet() {
        sasarachan.changeFace(DEMO_EXPRESSIONS_1[0]);
        sasarachan.invalidate();
        sasarachan.speak(DEMO_UTTERANCES[0]);
        sasarachan.setConversationPhase(2);
    }

    public void praise() {
        //
    }

    public void scold() {
        //
    }

    public void reply(String utteranceText) {
        if(utteranceText.contains("起きて") || utteranceText.contains("おはよう")) {
            greet();
        } else {
            continuePropose();
        }
    }
}
