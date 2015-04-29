package com.visionarries.www.eddi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    double left[];
    double right[];
    public static int seconds = 60;
    ImageView rightimg;
    Bitmap rightbmp;
    ImageView leftimg;
    Bitmap leftbmp;
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
        if(WelcomeScreen.long_or_short){
            contrastR=contrastR_true;
            answer=new double[contrastR.length];left=new double[contrastR.length];right=new double[contrastR.length];
        }else{
            contrastR=contrastR_false;
            answer=new double[contrastR.length];left=new double[contrastR.length];right=new double[contrastR.length];

        }
        rightimg = (ImageView) findViewById(R.id.rightGrating_test);
        leftimg = (ImageView) findViewById(R.id.leftGrating_test);
        ImageView leftring = (ImageView) findViewById(R.id.leftFocusRing_test);
        ImageView rightring = (ImageView) findViewById(R.id.rightFocusRing_test);
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) leftring.getLayoutParams();
        ViewGroup.MarginLayoutParams plm = (ViewGroup.MarginLayoutParams) rightring.getLayoutParams();
        mlp.leftMargin = DataSave.from_left_left;
        mlp.topMargin = DataSave.from_top_left;
        leftring.setLayoutParams(mlp);
        plm.leftMargin = DataSave.from_left_right;
        plm.topMargin = DataSave.from_top_right;
        rightring.setLayoutParams(plm);


//Timer
        waitTimer = new CountDownTimer((contrastR.length)*seconds*1000+500,(seconds)*1000) {
            public void onTick(long millisUntilFinished) {
                i++;
//defining the right image
                BitmapDrawable leftabmp = (BitmapDrawable) leftimg.getDrawable();
                leftbmp = leftabmp.getBitmap();
                left_pattern(.5);

                BitmapDrawable rightabmp = (BitmapDrawable) rightimg.getDrawable();
                rightbmp = rightabmp.getBitmap();
                right_pattern(contrastR[i]);

            }

            @Override
            public void onFinish() {
                if (d != c) {
                    lastDuration_left = Math.abs(System.currentTimeMillis() - lastDown_left);
                    if (x==1){left[i]=left[i]+lastDuration_left;x=0;}

                }if (d_r != c_r) {
                    lastDuration_right = Math.abs(System.currentTimeMillis() - lastDown_right);
                    if(x_r==1){right[i]=right[i]+lastDuration_right;x_r=0;}
                }

                for (int i=0; i<contrastR.length; i++){
                    answer[i]=right[i]/(right[i]+left[i]);
                }

                Intent intent = new Intent(TestPage.this, Calculation.class);
                startActivity(intent);
            }
        }.start();
    }

    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN && event.isButtonPressed(MotionEvent.BUTTON_PRIMARY )) {
            lastDown_left = System.currentTimeMillis();
            a=i;
            c++;
            x=1;
           }

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
    public boolean onKeyUp(int keyCode, KeyEvent event)  {

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
               Bitmap operation = Bitmap.createBitmap(rightbmp.getWidth(), rightbmp.getHeight(), rightbmp.getConfig());
//the loop goes through each picture
        for(int i=0; i< rightbmp.getWidth()-1; i++){
            for(int j=0; j< rightbmp.getHeight()-1; j++){

                double ij=(i-(rightbmp.getWidth()/2))*(i-(rightbmp.getWidth()/2))+(j-(rightbmp.getWidth()/2))*(j-(rightbmp.getWidth()/2));
                double k = (rightbmp.getWidth()/2)*(rightbmp.getWidth()/2);
                double dis=Math.sqrt(2*(rightbmp.getWidth()^2));

                if (ij<=k){//displays pattern in a circle

                    int gray_level = gamma_correction((int) ((contrast * (255 / 2) * Math.cos((i + j) * Math.PI * (1.09 / dis)) + (255 / 2)))); //pattern
                    operation.setPixel(i, j, Color.argb(255, gray_level, gray_level, gray_level));}//actually assigns the values.

                else{
                   int test=gamma_correction(128);
                    operation.setPixel(i, j, Color.argb(255, test, test, test));}


            }
        }
        rightimg.setImageBitmap(operation);
    }
    //makes the left pattern
    public void left_pattern(Double contrast){
        Bitmap operation = Bitmap.createBitmap(leftbmp.getWidth(), leftbmp.getHeight(), leftbmp.getConfig());
//the loop goes through each picture
        for(int i=0; i< leftbmp.getWidth()-1; i++){
            for(int j=leftbmp.getHeight()-1; j>0; j--){

                double ij=(i-(leftbmp.getWidth()/2))*(i-(leftbmp.getWidth()/2))+(j-(leftbmp.getWidth()/2))*(j-(leftbmp.getWidth()/2));
                double k = (leftbmp.getWidth()/2)*(leftbmp.getWidth()/2);
                double dis=Math.sqrt(2*(leftbmp.getWidth()^2));

                if (ij<=k){//displays pattern in a circle

                    int gray_level = gamma_correction((int) ((contrast * (255 / 2) * Math.cos((+i - j) * Math.PI * (1.09 / dis)) + (255 / 2)))); //pattern
                    operation.setPixel(i, j, Color.argb(255, gray_level, gray_level, gray_level));}//actually assigns the values.

                else{
                    int test=gamma_correction(128);
                    operation.setPixel(i, j, Color.argb(255, test, test, test));}


            }
        }
        leftimg.setImageBitmap(operation);
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

