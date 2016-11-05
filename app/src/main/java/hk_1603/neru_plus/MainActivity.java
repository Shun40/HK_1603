package hk_1603.neru_plus;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.content.ActivityNotFoundException;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int SR_REQUEST_CODE = 0; // 音声認識用リクエストコード
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    private RelativeLayout relativeLayout;
    private Sasarachan   sasarachan;
    private Button       speechRecogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //resetFile();

        relativeLayout = new RelativeLayout(this);
        setContentView(relativeLayout);
        // UIコンポーネントセットアップ
        setupSpeechRecogButton();
        setupSasarachan();

        sasarachan.converse();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 自分が投げたインテントであれば応答する
        if (requestCode == SR_REQUEST_CODE && resultCode == RESULT_OK) {
            String result = "";
            // 結果文字列リスト
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            result = results.get(0); // 認識結果の上位1件のみ取得
            // トーストを使って結果を表示
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            // ささらちゃんへ返答する
            sasarachan.reply(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
     * 音声認識実行ボタンセットアップ
     */
    public void setupSpeechRecogButton() {
        speechRecogButton = new Button(this);
        speechRecogButton.setId(View.generateViewId());
        speechRecogButton.setText("話しかける");
        speechRecogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // インテント作成
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // ACTION_WEB_SEARCH
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "話しかけてあげよう!");
                    // インテント発行
                    startActivityForResult(intent, SR_REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    // このインテントに応答できるアクティビティがインストールされていない場合
                    e.printStackTrace();
                }
            }
        });
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(WC, WC);
        param.addRule(RelativeLayout.CENTER_HORIZONTAL);
        param.addRule(RelativeLayout.ABOVE);
        relativeLayout.addView(speechRecogButton, param);
    }

    /*
     * ささらちゃんセットアップ
     */
    public void setupSasarachan() {
        sasarachan = new Sasarachan(this);
        sasarachan.setState(new MorningState(sasarachan)); // 起床時 or 就寝時の状態をセット
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(WC, WC);
        param.addRule(RelativeLayout.CENTER_HORIZONTAL);
        param.addRule(RelativeLayout.BELOW, speechRecogButton.getId());
        relativeLayout.addView(sasarachan, param);
    }

    public void resetFile() {
        OutputStream out;
        try {
            out = this.openFileOutput("morning.txt", Context.MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
            writer.print("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = this.openFileOutput("night.txt", Context.MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
            writer.print("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
