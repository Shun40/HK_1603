package hk_1603.neru_plus;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup.*;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView  titleText;
    private ImageView sasarachanImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // レイアウトXMLファイルの指定

        // UIコンポーネントセットアップ
        setupTitleText();
        setupSasarachanImage();
    }

    /*
     * アプリタイトルのテキストセットアップ
     */
    public void setupTitleText() {
        titleText = new TextView(this);
        titleText.setText("ねるぷらす ～彼女といっしょ～");
        titleText.setTextSize(16.f);
        titleText.setTextColor(Color.BLACK);
        setContentView(titleText, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    /*
     * 女の子エージェント(ささらちゃん)の画像セットアップ
     */
    public void setupSasarachanImage() {
        sasarachanImage = new ImageView(this);
        sasarachanImage.setImageResource(R.drawable.base);
        setContentView(sasarachanImage, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }
}
