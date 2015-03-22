package com.visionarries.www.eddi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceControl extends MainActivity {
    int i = 0;
    ImageView rightimg;
    Bitmap rightbmp;
    CountDownTimer waitTimer;
    TextView text;

   double contrastR[] = {.05,.10,.20,.40,.45,.50,.55,.60,.80,.90,.95};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //this is just needed
        super.onCreate(savedInstanceState);
        //sets the layout to the inputted ID
        setContentView(R.layout.voice_control);
//Timer
        waitTimer = new CountDownTimer(60000*contrastR.length^2, 60000*contrastR.length) {
            public void onTick(long millisUntilFinished) {
                rightimg = (ImageView) findViewById(R.id.imageVoice);
                text = (TextView) findViewById(R.id.textView1);

//defining the right image
                BitmapDrawable rightabmp = (BitmapDrawable) rightimg.getDrawable();
                rightbmp = rightabmp.getBitmap();

                right_pattern(contrastR[i]);


                i=i+1;
//
                text.setText("seconds remaining: " + millisUntilFinished / 1000);

            }

            public void onFinish() {

                Intent intent = new Intent(VoiceControl.this, CustomerName.class);
                startActivity(intent);

            }



        }.start();

    }

//Button Recognizer
    long lastDown;
    long lastDuration;
    public boolean onTouchEvent(MotionEvent event) {
//        int eventaction = event.getButtonState();

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
//    Toast toast_finger_down = Toast.makeText(context, "You touched me.", duration);


        if(event.getAction() == android.view.MotionEvent.ACTION_DOWN ) {
            lastDown = System.currentTimeMillis();
                    }

        else if (event.getAction() == MotionEvent.ACTION_UP) {
            lastDuration = System.currentTimeMillis() - lastDown;


            DataSave.time_pressed[i]=DataSave.time_pressed[i]+lastDuration/1000.;
            Toast toast_finger_up = Toast.makeText(context, "You released me." + DataSave.time_pressed[i], duration);
            toast_finger_up.show();

        }

        // tell the system that we handled the event and no further processing is required
        return true;
    }

//makes the pattern
    public void right_pattern(Double contrast){
               Bitmap operation = Bitmap.createBitmap(rightbmp.getWidth(), rightbmp.getHeight(), rightbmp.getConfig());
//the loop goes through each picture
        for(int i=0; i< rightbmp.getWidth(); i++){
            for(int j=0; j< rightbmp.getHeight(); j++){

                double ij=(i-(rightbmp.getWidth()/2))*(i-(rightbmp.getWidth()/2))+(j-(rightbmp.getWidth()/2))*(j-(rightbmp.getWidth()/2));
                double k = (rightbmp.getWidth()/2)*(rightbmp.getWidth()/2);
                double dis=Math.sqrt(2*(rightbmp.getWidth()^2));

                if (ij<=k){//displays pattern in a circle

                    int gray_level = (int)((contrast*(255/2)*Math.cos((i+j)*Math.PI*(.65/dis))+(255/2))); //pattern
                    operation.setPixel(i, j, Color.argb(255, gray_level, gray_level, gray_level));}//actually assigns the values.

                else{
                    int test=128;
                    operation.setPixel(i, j, Color.argb(255, test, test, test));}


            }
        }
        rightimg.setImageBitmap(operation);
    }
}

