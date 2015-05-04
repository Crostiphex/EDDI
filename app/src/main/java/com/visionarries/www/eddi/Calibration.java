package com.visionarries.www.eddi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Locale;


public class Calibration extends Activity implements TextToSpeech.OnInitListener {
    public String text;
    int a = 0;
    private TextToSpeech tts;
    //defining the images
    private ImageView left_ring;
    private ImageView right_ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this is just needed
        super.onCreate(savedInstanceState);
        //sets the layout to the inputted ID
        setContentView(R.layout.calibration_screen);


//delete this line if you don't want it to talk
        tts = new TextToSpeech(this, this);
        text = "Calibration page initiated. Slide finger on middle button to move pattern, click the middle button to move other pattern. When finished, left click to continue to the test.";


        left_ring = (ImageView) findViewById(R.id.leftFocusRing);
        right_ring = (ImageView) findViewById(R.id.rightFocusRing);

//this button makes the pattern

        final ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) left_ring.getLayoutParams();
        left_ring.setLayoutParams(mlp);
        final ViewGroup.MarginLayoutParams plm = (ViewGroup.MarginLayoutParams) right_ring.getLayoutParams();
        right_ring.setLayoutParams(plm);

    }

    //moves the pattern with the mouse touch pad
    public boolean onGenericMotionEvent(MotionEvent event) {
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) left_ring.getLayoutParams();
        ViewGroup.MarginLayoutParams plm = (ViewGroup.MarginLayoutParams) right_ring.getLayoutParams();
        int scroll_action = event.getAction();
        int button_click = event.getButtonState();
//        Toast toast_finger_move = Toast.makeText(context, "You moved me.", duration);
        switch (button_click) {
            case MotionEvent.BUTTON_PRIMARY:
                DataSave.from_left_left = mlp.leftMargin;
                DataSave.from_top_left = mlp.topMargin;
                DataSave.from_left_right = plm.leftMargin;
                DataSave.from_top_right = plm.topMargin;

                Intent intent = new Intent(Calibration.this, TestPage.class);
                startActivity(intent);
                break;
            case MotionEvent.BUTTON_TERTIARY://every time you click it adds one
                a = a + 1;
                break;
        }

        switch (scroll_action) {
            case MotionEvent.ACTION_SCROLL:
                // finger leaves the screen
                if (a % 2 == 0) {//if you click and the remainder is even or odd it will control a different image (left or right)
                    if (event.getAxisValue(MotionEvent.AXIS_HSCROLL) < 0.0f) {
                        mlp.leftMargin = mlp.leftMargin - 5;
                        left_ring.setLayoutParams(mlp);
                    }
                    if (event.getAxisValue(MotionEvent.AXIS_HSCROLL) > 0.0f) {
                        mlp.leftMargin = mlp.leftMargin + 5;
                        left_ring.setLayoutParams(mlp);
                    }
                    if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) < 0.0f) {
                        mlp.topMargin = mlp.topMargin + 5;
                        left_ring.setLayoutParams(mlp);
                    }
                    if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) > 0.0f) {
                        mlp.topMargin = mlp.topMargin - 5;
                        left_ring.setLayoutParams(mlp);
                    }
                }
                if (a % 2 == 1) {
                    if (event.getAxisValue(MotionEvent.AXIS_HSCROLL) < 0.0f) {
                        plm.leftMargin = plm.leftMargin - 5;
                        right_ring.setLayoutParams(plm);
                    }
                    if (event.getAxisValue(MotionEvent.AXIS_HSCROLL) > 0.0f) {
                        plm.leftMargin = plm.leftMargin + 5;
                        right_ring.setLayoutParams(plm);
                    }
                    if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) < 0.0f) {
                        plm.topMargin = plm.topMargin + 5;
                        right_ring.setLayoutParams(plm);
                    }
                    if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) > 0.0f) {
                        plm.topMargin = plm.topMargin - 5;
                        right_ring.setLayoutParams(plm);
                    }
                }


                break;
        }

        // tell the system that we handled the event and no further processing is required
        return true;
    }

    @Override
    protected void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();


    }

    private void speakOut(String text) {

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.ENGLISH);

            //tts.setPitch(-10); // set pitch level

            // tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                speakOut(text);
            }

        } else {
            Log.e("TTS", "Initialization Failed");
        }

    }
}

