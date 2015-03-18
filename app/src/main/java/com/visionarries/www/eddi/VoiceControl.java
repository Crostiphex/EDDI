package com.visionarries.www.eddi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;




public class VoiceControl extends MainActivity {


    private ImageView rightimg;
    private Bitmap rightbmp;
    int photoAry[] = { R.drawable.androidlogo300, R.drawable.focalring, R.drawable.frogger,
            R.drawable.ic_launcher};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this is just needed
        super.onCreate(savedInstanceState);
        //sets the layout to the inputted ID
        setContentView(R.layout.voice_control);

        Timer timer;

        timer = new Timer("TweetCollectorTimer");
        timer.schedule(updateTask, 3000L, 3000L);//here 6000L is starting //delay and 3000L is periodic delay after starting delay


    }

    private TimerTask updateTask = new TimerTask() {
int i =0;


        BitmapDrawable rightabmp = (BitmapDrawable) rightimg.getDrawable();
        @Override

        public void run() {

            VoiceControl.this.runOnUiThread(new Runnable() {

                @Override
                public void run() { // TODO Auto-generated method stub

//                    right_pattern(null, i/10);


                    new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {

                            right_pattern(view, i/10);

                        }
                    };
                    i++;
                    if (i > 3)
                    {
                        i = 0;


                    }
                }

            });
        }
    };


    long lastDown;
    long lastDuration;
    long sumDuration;
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

            sumDuration=sumDuration+lastDuration;
            Toast toast_finger_up = Toast.makeText(context, "You released me." + sumDuration/1000.0, duration);
            toast_finger_up.show();
        }

        // tell the system that we handled the event and no further processing is required
        return true;
    }
    //makes the pattern

    public void right_pattern(View view, double contrast){
        rightimg = (ImageView) findViewById(R.id.rightGrating);
        BitmapDrawable rightabmp = (BitmapDrawable) rightimg.getDrawable();
        rightbmp = rightabmp.getBitmap();

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

