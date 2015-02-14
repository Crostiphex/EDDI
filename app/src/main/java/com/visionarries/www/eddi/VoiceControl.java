package com.visionarries.www.eddi;

import android.content.Context;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import java.util.ArrayList;
import android.util.Log;
import android.widget.Toast;

public class VoiceControl extends MainActivity implements View.OnClickListener {

    private TextView mText;
    private SpeechRecognizer sr;
    private static final String TAG = "MyStt3Activity";
    private ImageView sop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this is just needed
        super.onCreate(savedInstanceState);
        //sets the layout to the inputted ID
        setContentView(R.layout.voice_control);
        Button speakButton = (Button) findViewById(R.id.btn_speak);
        mText = (TextView) findViewById(R.id.textView1);
        sop=(ImageView) findViewById(R.id.imageVoice);
        speakButton.setOnClickListener(this);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

}
    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params){Log.d(TAG, "onReadyForSpeech");}
        public void onBeginningOfSpeech(){Log.d(TAG, "onBeginningOfSpeech");}
        public void onRmsChanged(float rmsdB){Log.d(TAG, "onRmsChanged");}
        public void onBufferReceived(byte[] buffer){Log.d(TAG, "onBufferReceived");}
        public void onEndOfSpeech(){Log.d(TAG, "onEndofSpeech");}
        public void onError(int error)        {
            Log.d(TAG,  "error " +  error);
            mText.setText("error " + error);
        }
        public void onResults(Bundle results)
        {
            String str= null;
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            DataSave p = new DataSave();

            for (int i = 0; i < data.size(); i++)
            {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }
            long startTime = System.currentTimeMillis();
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) sop.getLayoutParams();

            String up="up";
            String down="down";
            String left="left";
            String right="right";
            if (str.contains(up)){
                mText.setText("results: "+up);
                mlp.topMargin=mlp.topMargin-30;
                sop.setLayoutParams(mlp);

            }
            if (str.contains(down)){
                mText.setText("results: "+down);
                mlp.topMargin=mlp.topMargin+30;
                sop.setLayoutParams(mlp);
            }
            if (str.contains(left)){
                mText.setText("results: "+left);
                mlp.leftMargin=mlp.leftMargin-30;
                sop.setLayoutParams(mlp);
            }
            if (str.contains(right)){
                mText.setText("results: "+right);
                mlp.leftMargin=mlp.leftMargin+30;
                sop.setLayoutParams(mlp);
            }
            long endTime = System.currentTimeMillis();
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            p.DataSaveTest= mlp.topMargin;
//toast to show the time it takes to run the programs
            Toast toast = Toast.makeText(context, "That took " + (endTime - startTime) + " milliseconds"+(p.DataSaveTest), duration);
            toast.show();
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }

    }
    public void onClick(View v) {
        if (v.getId() == R.id.btn_speak)
        {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"com.visionarries.www.eddi");

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
            sr.startListening(intent);
            Log.i("111111","11111111");
        }
    }}