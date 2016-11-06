package hk_1603.neru_plus;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.IOException;

public class Sasarachan extends ImageView {
    private static final int SAMPLING_RATE = 44100; // 音声素材のサンプリングレート(Hz)

    private SasarachanState state; // ささらちゃんの状態変数(起床時 or 就寝時 or デモ時)
    private int conversationPhase; // 発話状態の段階変数
    private Activity activity;

    public Sasarachan(AppCompatActivity activity) {
        super(activity);
        setId(View.generateViewId());
        setImageResource(R.drawable.base);

        this.conversationPhase = 0;
        this.activity = activity;
    }

    /*
     * ささらちゃんが状態変数に応じて対話する
     */
    public void converse() {
        switch(conversationPhase) {
            case 0:
                state.proposeAction();
                break;
            case 1:
                state.continuePropose();
                break;
            case 2:
                state.greet();
                break;
            default:
                break;
        }
    }

    /*
     * ささらちゃんが音声を発話する
     */
    public void speak(int utterance) {
        InputStream input;
        byte[] wavData;

        try {
            // wavを読み込む
            input = getResources().openRawResource(utterance);
            wavData = new byte[(int)input.available()];
            input.read(wavData);
            input.close();
            // バッファサイズの計算
            int bufSize = android.media.AudioTrack.getMinBufferSize(SAMPLING_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
            // MODE_STREAM にてインスタンス生成
            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SAMPLING_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufSize, AudioTrack.MODE_STREAM);
            // 再生
            audioTrack.play();
            // ヘッダ44byteをオミット
            audioTrack.write(wavData, 44, wavData.length-44);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * ささらちゃんがユーザから返事をもらう
     */
    public void reply(String utteranceText) {
        state.reply(utteranceText);
    }

    /*
     * ささらちゃんが表示を変える
     */
    public void changeFace(int expression) {
        setImageResource(expression);
    }

    public void setState(SasarachanState state) { this.state = state; }
    public void setConversationPhase(int conversationPhase) { this.conversationPhase = conversationPhase; }
    public Activity getActivity() { return activity; }
}
