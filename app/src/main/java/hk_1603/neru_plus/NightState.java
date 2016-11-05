package hk_1603.neru_plus;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NightState extends SasarachanState {
    private Random rand;

    private static final int[] NIGHT_UTTERANCES_0 = {
            R.raw.night_0_0 // 一緒に寝よ
    };
    private static final int[] NIGHT_UTTERANCES_1 = {
            R.raw.night_1_0, // もう寝る時間だよ
            R.raw.night_1_1, // 早く寝ないとだよ
            R.raw.night_1_2  // 夜更かしは駄目だよ
    };
    private static final int[] NIGHT_UTTERANCES_2 = {
            R.raw.night_2_0 // おやすみ
    };
    private static final int[] NIGHT_UTTERANCES_3 = {
            R.raw.night_3_0 // 昨日より早寝だね、えらいよ
    };
    private static final int[] NIGHT_UTTERANCES_4 = {
            R.raw.night_4_0 // 昨日より寝るの遅いよ、ちゃんと早く寝ようね
    };
    private static final int[] NIGHT_EXPRESSIONS_0 = {
            R.drawable.base
    };
    private static final int[] NIGHT_EXPRESSIONS_1 = {
            R.drawable.disappoint_0,
            R.drawable.doubt_0,
            R.drawable.doubt_1,
            R.drawable.hmm_0,
            R.drawable.sulk_0,
            R.drawable.sulk_1,
            R.drawable.trouble_0
    };
    private static final int[] NIGHT_EXPRESSIONS_2 = {
            R.drawable.smile_0,
            R.drawable.smile_1,
            R.drawable.smile_2
    };
    private static final int[] NIGHT_EXPRESSIONS_3 = {
            R.drawable.smile_0,
            R.drawable.smile_1,
            R.drawable.smile_2
    };
    private static final int[] NIGHT_EXPRESSIONS_4 = {
            R.drawable.hmm_0,
            R.drawable.sulk_1,
            R.drawable.trouble_0
    };

    public NightState(Sasarachan sasarachan) {
        super(sasarachan);
        this.rand = new Random();
        startTime = System.currentTimeMillis();
    }

    public void proposeAction() {
        sasarachan.changeFace(NIGHT_EXPRESSIONS_0[rand.nextInt(NIGHT_EXPRESSIONS_0.length)]);
        sasarachan.speak(NIGHT_UTTERANCES_0[rand.nextInt(NIGHT_UTTERANCES_0.length)]);
        sasarachan.setConversationPhase(0);
    }

    public void continuePropose() {
        sasarachan.changeFace(NIGHT_EXPRESSIONS_1[new Random().nextInt(NIGHT_EXPRESSIONS_1.length)]);
        sasarachan.speak(NIGHT_UTTERANCES_1[rand.nextInt(NIGHT_UTTERANCES_1.length)]);
        sasarachan.setConversationPhase(1);
    }

    public void greet() {
        sasarachan.changeFace(NIGHT_EXPRESSIONS_2[new Random().nextInt(NIGHT_EXPRESSIONS_2.length)]);
        sasarachan.speak(NIGHT_UTTERANCES_2[rand.nextInt(NIGHT_UTTERANCES_2.length)]);
        sasarachan.setConversationPhase(2);
    }

    public void praise() {
        sasarachan.changeFace(NIGHT_EXPRESSIONS_3[new Random().nextInt(NIGHT_EXPRESSIONS_3.length)]);
        sasarachan.speak(NIGHT_UTTERANCES_3[rand.nextInt(NIGHT_UTTERANCES_3.length)]);
        sasarachan.setConversationPhase(2);
    }

    public void scold() {
        sasarachan.changeFace(NIGHT_EXPRESSIONS_4[new Random().nextInt(NIGHT_EXPRESSIONS_4.length)]);
        sasarachan.speak(NIGHT_UTTERANCES_4[rand.nextInt(NIGHT_UTTERANCES_4.length)]);
        sasarachan.setConversationPhase(2);
    }

    public void reply(String utteranceText) {
        if(utteranceText.contains("おやすみ")) {
            if(secTime == -1) {
                endTime = System.currentTimeMillis();
                secTime = (int) ((endTime - startTime) / 1000);
                writeTimeToFile();
                ArrayList<Integer> secTimes = readTimeFromFile();
                int todayTime = secTimes.get(0);
                int yesterdayTime = secTimes.get(1);
                if(todayTime <= yesterdayTime) {
                    praise();
                } else {
                    scold();
                }
            }
            greet();
        } else {
            continuePropose();
            rejectCount++;
        }
    }

    public void writeTimeToFile() {
        OutputStream out;
        try {
            out = sasarachan.getActivity().openFileOutput("night.txt", Context.MODE_PRIVATE | Context.MODE_APPEND);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
            //追記する
            writer.append(Integer.toString(secTime));
            writer.println();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> readTimeFromFile() {
        FileInputStream fileInputStream;
        ArrayList<Integer> secTimes = new ArrayList<Integer>();

        try {
            fileInputStream = sasarachan.getActivity().openFileInput("night.txt");
            String secTime = null;

            BufferedReader reader= new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
            while((secTime = reader.readLine()) != null ) {
                secTimes.add(Integer.parseInt(secTime));
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        Collections.reverse(secTimes); // 先頭に最新の記録時間がくるよう要素を逆順に並べる
        return secTimes;
    }
}
