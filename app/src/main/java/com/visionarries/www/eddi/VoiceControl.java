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

public class VoiceControl extends WelcomeScreen {
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
        waitTimer = new CountDownTimer(11000, 1000) {
            public void onTick(long millisUntilFinished) {
                rightimg = (ImageView) findViewById(R.id.imageVoice);
                text = (TextView) findViewById(R.id.textView1);

//defining the right image
                BitmapDrawable rightabmp = (BitmapDrawable) rightimg.getDrawable();
                rightbmp = rightabmp.getBitmap();
                right_pattern(contrastR[i]);
                i=i+1;
                text.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {

                Intent intent = new Intent(VoiceControl.this, Calculations.class);
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

                    int gray_level = gamma_correction((int)((contrast*(255/2)*Math.cos((i+j)*Math.PI*(.65/dis))+(255/2)))); //pattern
                    operation.setPixel(i, j, Color.argb(255, gray_level, gray_level, gray_level));}//actually assigns the values.

                else{
                    int test=gamma_correction(128);
                    operation.setPixel(i, j, Color.argb(255, test, test, test));}


            }
        }
        rightimg.setImageBitmap(operation);
    }
    public int gamma_correction (int initial_value){

        int[ ] correction ={0, 9, 20, 28, 34, 39, 44, 48, 51, 55, 58, 61, 64, 67, 69, 72, 74, 76,
                79, 81, 83, 85, 87, 89, 91, 92, 94, 96, 98, 99, 101, 102, 104, 105,
                107, 108, 110, 111, 113, 114, 115, 117, 118, 119, 121, 122, 123, 124,
                125, 127, 128, 129, 130, 131, 132, 133, 134, 135, 137, 138, 139, 140,
                141, 142, 143, 144, 145, 146, 147, 147, 148, 149, 150, 151, 152, 153,
                154, 155, 156, 157, 157, 158, 159, 160, 161, 162, 162, 163, 164, 165,
                166, 166, 167, 168, 169, 170, 170, 171, 172, 173, 173, 174, 175, 176,
                176, 177, 178, 179, 179, 180, 181, 181, 182, 183, 183, 184, 185, 185,
                186, 187, 187, 188, 189, 189, 190, 191, 191, 192, 193, 193, 194, 195,
                195, 196, 196, 197, 198, 198, 199, 200, 200, 201, 201, 202, 203, 203,
                204, 204, 205, 205, 206, 207, 207, 208, 208, 209, 209, 210, 211, 211,
                212, 212, 213, 213, 214, 214, 215, 215, 216, 217, 217, 218, 218, 219,
                219, 220, 220, 221, 221, 222, 222, 223, 223, 224, 224, 225, 225, 226,
                226, 227, 227, 228, 228, 229, 229, 230, 230, 231, 231, 232, 232, 233,
                233, 234, 234, 235, 235, 236, 236, 236, 237, 237, 238, 238, 239, 239,
                240, 240, 241, 241, 242, 242, 242, 243, 243, 244, 244, 245, 245, 246,
                246, 246, 247, 247, 248, 248, 249, 249, 249, 250, 250, 251, 251, 252,
                252, 252, 253, 253, 254, 254, 255, 255, 255, 255, 255, 255,255};


        return correction[initial_value];

    }
}

