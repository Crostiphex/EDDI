package com.visionarries.www.eddi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TestPage extends WelcomeScreen {

    public static double contrastR_true[] = {
            0.1,
            0.35,
            0.5,
            0.7,
            0.85,
            0.2,
            0.3,
            0.45,
            0.75,
            0.9,
            0.15,
            0.25,
            0.55,
            0.65,
            0.6
    };
    public static double contrastR_false[] = {
            0.1,
            0.45,
            .3,
            0.9,
            .64
           };
    public static double contrastR[];
    public static double answer[];
    public static int seconds = 60;
    double left[];
    double right[];
    ImageView right_img;
    Bitmap right_bmp;
    ImageView left_img;
    Bitmap left_bmp;
    CountDownTimer waitTimer;
    int i = -1;
    int a=0;
    int b=0;
    int c = 0;
    int d = 0;
    int x=0;
    int a_r=0;
    int b_r=0;
    int c_r = 0;
    int d_r = 0;
    int x_r=0;

    //Button Recognizer
    long lastDown_left;
    long lastDown_right;
    long lastDuration_left;
    long lastDuration_right;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //this is just needed
        super.onCreate(savedInstanceState);
        //sets the layout to the inputted ID
        setContentView(R.layout.test_page);
        //if the long test was checked then it changes which contrast values you run through during the test.
        if(WelcomeScreen.long_or_short){
            contrastR=contrastR_true;
            answer=new double[contrastR.length];left=new double[contrastR.length];right=new double[contrastR.length];
        }else{
            contrastR=contrastR_false;
            answer=new double[contrastR.length];left=new double[contrastR.length];right=new double[contrastR.length];
        }
        //initializing buttons and views.
        right_img = (ImageView) findViewById(R.id.rightGrating_test);
        left_img = (ImageView) findViewById(R.id.leftGrating_test);
        ImageView left_ring = (ImageView) findViewById(R.id.leftFocusRing_test);
        ImageView right_ring = (ImageView) findViewById(R.id.rightFocusRing_test);
        //getting the layout from the calibration page and setting it up here.
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) left_ring.getLayoutParams();
        ViewGroup.MarginLayoutParams plm = (ViewGroup.MarginLayoutParams) right_ring.getLayoutParams();
        mlp.leftMargin = DataSave.from_left_left;
        mlp.topMargin = DataSave.from_top_left;
        left_ring.setLayoutParams(mlp);
        plm.leftMargin = DataSave.from_left_right;
        plm.topMargin = DataSave.from_top_right;
        right_ring.setLayoutParams(plm);


//Timer, will count down until zero, it's not perfect so you have to give some buffer time, which is why I have the .5 seconds added, .5 does not affect the test
        //think of it as a for loop in respect to time.
        waitTimer = new CountDownTimer((contrastR.length) * seconds * 1000 + 500, (seconds) * 1000) {
            public void onTick(long millisUntilFinished) {
                //every time seconds*1000 has passed all of this code functions
                i++;//it isn't a for loop though and that's why i started with negative 1.
//defining the right image
                BitmapDrawable left_a_bmp = (BitmapDrawable) left_img.getDrawable();
                left_bmp = left_a_bmp.getBitmap();
                left_pattern(.5);//contrast value is always .5

                BitmapDrawable right_a_bmp = (BitmapDrawable) right_img.getDrawable();
                right_bmp = right_a_bmp.getBitmap();
                right_pattern(contrastR[i]);//contrast value will cycle through the contrast values that were defined about

            }

            @Override
            public void onFinish() {//when finished this code is activated.
                if (d != c) {//if the person is still holding down the button when the test is finished we take the time it was held down for.
                    lastDuration_left = Math.abs(System.currentTimeMillis() - lastDown_left);
                    if (x==1){left[i]=left[i]+lastDuration_left;x=0;}

                }if (d_r != c_r) {
                    lastDuration_right = Math.abs(System.currentTimeMillis() - lastDown_right);
                    if(x_r==1){right[i]=right[i]+lastDuration_right;x_r=0;}
                }

                for (int i = 0; i < contrastR.length; i++) {//this is the relative duration and the answer is what is getting passed onto the calculation page.
                    answer[i]=right[i]/(right[i]+left[i]);
                }

                Intent intent = new Intent(TestPage.this, Calculation.class);
                startActivity(intent);
            }
        }.start();//starts thr count down
    }

    //this is the code to calculate how long you've been holding down the left mouse button
    public boolean onTouchEvent(MotionEvent event) {
//when you press down you get the current time
        if(event.getAction() == MotionEvent.ACTION_DOWN && event.isButtonPressed(MotionEvent.BUTTON_PRIMARY )) {
            lastDown_left = System.currentTimeMillis();
            a=i;
            c++;
            x=1;
           }
//when you let go you get the current time and subtract it from the previous time and add it for the duration in this contras value,
        //variables are for if you're holding down a button between the two contrast values, this is a fail-safe so you don't give the times to the wrong contrast values
                 if (event.getAction() == MotionEvent.ACTION_UP) {

            lastDuration_left = Math.abs(System.currentTimeMillis() - lastDown_left);
            b=i;
            d++;
            if(a!=b){
                left[i-1]=left[i-1]+lastDuration_left;
                x=0;
            }
            else{
                left[i]=left[i]+lastDuration_left;
               x=0;
            }
        }

        // tell the system that we handled the event and no further processing is required
        return true;
    }

    //this is for the right clicking
    //we need a different method because android sees right clicking as back
    //this means you can't click back throughout the test, which is actually pretty good
    //also a lot more neater to have two different methods,
    //essentially the code is repeated for the left clicking.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        KeyEvent.changeTimeRepeat(event,0,0);
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0&& event.getAction()==KeyEvent.ACTION_DOWN) {
            lastDown_right = System.currentTimeMillis();
            a_r=i;
            c_r++;
            x_r=1;
            return true;
        }

        return super.onKeyDown(keyCode,event);
    }

    @Override
    public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0&& event.getAction()==KeyEvent.ACTION_UP&&event.getFlags()==0) {
            lastDuration_right = Math.abs(System.currentTimeMillis() - lastDown_right);
            b_r=i;
            d_r++;
            if(a_r!=b_r){
                right[i-1]=right[i-1]+lastDuration_right;
                x_r=0;
            }
            else{
                right[i]=right[i]+lastDuration_right;
                x_r=0;
            }
        }

        return super.onKeyUp(keyCode,event);
    }

    //makes the right pattern
    public void right_pattern(Double contrast){
        Bitmap operation = Bitmap.createBitmap(right_bmp.getWidth(), right_bmp.getHeight(), right_bmp.getConfig());
//the loop goes through each picture
        for (int i = 0; i < right_bmp.getWidth() - 1; i++) {
            for (int j = 0; j < right_bmp.getHeight() - 1; j++) {

                double ij = (i - (right_bmp.getWidth() / 2)) * (i - (right_bmp.getWidth() / 2)) + (j - (right_bmp.getWidth() / 2)) * (j - (right_bmp.getWidth() / 2));
                double k = (right_bmp.getWidth() / 2) * (right_bmp.getWidth() / 2);
                double dis = Math.sqrt(2 * (right_bmp.getWidth() ^ 2));

                if (ij<=k){//displays pattern in a circle

                    int gray_level = gamma_correction((int) ((contrast * (255 / 2) * Math.cos((i + j) * Math.PI * (1.09 / dis)) + (255 / 2)))); //pattern
                    operation.setPixel(i, j, Color.argb(255, gray_level, gray_level, gray_level));}//actually assigns the values.

                else{
                   int test=gamma_correction(128);
                    operation.setPixel(i, j, Color.argb(255, test, test, test));}


            }
        }
        right_img.setImageBitmap(operation);
    }
    //makes the left pattern
    public void left_pattern(Double contrast){
        Bitmap operation = Bitmap.createBitmap(left_bmp.getWidth(), left_bmp.getHeight(), left_bmp.getConfig());
//the loop goes through each picture
        for (int i = 0; i < left_bmp.getWidth() - 1; i++) {
            for (int j = left_bmp.getHeight() - 1; j > 0; j--) {

                double ij = (i - (left_bmp.getWidth() / 2)) * (i - (left_bmp.getWidth() / 2)) + (j - (left_bmp.getWidth() / 2)) * (j - (left_bmp.getWidth() / 2));
                double k = (left_bmp.getWidth() / 2) * (left_bmp.getWidth() / 2);
                double dis = Math.sqrt(2 * (left_bmp.getWidth() ^ 2));

                if (ij<=k){//displays pattern in a circle

                    int gray_level = gamma_correction((int) ((contrast * (255 / 2) * Math.cos((+i - j) * Math.PI * (1.09 / dis)) + (255 / 2)))); //pattern
                    operation.setPixel(i, j, Color.argb(255, gray_level, gray_level, gray_level));}//actually assigns the values.

                else{
                    int test=gamma_correction(128);
                    operation.setPixel(i, j, Color.argb(255, test, test, test));}


            }
        }
        left_img.setImageBitmap(operation);
    }

    //this remaps the gray levels so there is a linear increase in them, as the phone brightness as some sort of nonlinear increase in gamma levels.
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

