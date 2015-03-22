package com.visionarries.www.eddi;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;



public class TestScreen extends Activity {

    //defining the images
    private ImageView leftimg;
    private Bitmap leftbmp;
    private ImageView rightimg;
    private Bitmap rightbmp;
    private ImageView leftring;


    //defining the seekbar and text underneath it
    SeekBar seekbar;
    TextView value;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this is just needed
        super.onCreate(savedInstanceState);
        //sets the layout to the inputted ID
        setContentView(R.layout.test_screen);

        //so we can call and edit the values
        value = (TextView) findViewById(R.id.textView);
        seekbar = (SeekBar) findViewById(R.id.seekBar);



leftring = (ImageView) findViewById(R.id.leftFocusRing);
//defining the left image
        leftimg = (ImageView) findViewById(R.id.leftGrating);
        BitmapDrawable leftabmp = (BitmapDrawable) leftimg.getDrawable();
        leftbmp = leftabmp.getBitmap();
//defining the right image
        rightimg = (ImageView) findViewById(R.id.rightGrating);
        BitmapDrawable rightabmp = (BitmapDrawable) rightimg.getDrawable();
        rightbmp = rightabmp.getBitmap();
//this button makes the pattern
        final Button makePattern =(Button)findViewById(R.id.makePattern);

        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) leftring.getLayoutParams();
           leftring.setLayoutParams(mlp);

        //this code is for the seekbar to function
        seekbar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
            {

                //when you move the seekbar is changed the text changes.
                                value.setText("Contrast value is "+progress+" %");
            }

            public void onStartTrackingTouch(SeekBar seekBar){}

            public void onStopTrackingTouch(SeekBar seekBar){}
        });

//listener for the button

        makePattern.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(final View view) {
                //this is to keep track of how long it takes for the image functions to be executed.
                long startTime = System.currentTimeMillis();
//calls the function that rewrites thes the bitmap data for the left and right images

                left_pattern(view, 1.0);
                //calls the value of what the seekbar is.

// invoke intent
                double right = seekbar.getProgress() / 100.0;
                right_pattern(right);


                long endTime = System.currentTimeMillis();
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
//toast to show the time it takes to run the programs
                Toast toast = Toast.makeText(context, "That took " + (endTime - startTime) + " milliseconds", duration);
                toast.show();






            }
        });
    }

//    public boolean onTouch(View v, MotionEvent event) {
//        if (event.getButtonState() == MotionEvent.BUTTON_PRIMARY) { // API 14 required
//            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) leftring.getLayoutParams();
//            mlp.topMargin = mlp.topMargin + 30;
//            leftring.setLayoutParams(mlp);
//
//
//        }
//        return true;
//    }
long lastDown;
    long lastDuration;
    long sumDuration;
public boolean onTouchEvent(MotionEvent event) {
    int eventaction = event.getButtonState();

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

    public boolean onGenericMotionEvent(MotionEvent event) {
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) leftring.getLayoutParams();

        int eventaction = event.getAction();

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast_finger_down = Toast.makeText(context, "You touched me.", duration);
//        Toast toast_finger_move = Toast.makeText(context, "You moved me.", duration);
//        Toast toast_finger_up = Toast.makeText(context, "You released me.", duration);

        switch (eventaction) {
            case MotionEvent.BUTTON_PRIMARY:

                toast_finger_down.show();
                break;

//        case MotionEvent.ACTION_MOVE:
//            // finger moves on the screen
//          //  toast_finger_move.show();
//            break;

            case MotionEvent.ACTION_SCROLL:
                // finger leaves the screen
                if (event.getAxisValue(MotionEvent.AXIS_HSCROLL) < 0.0f){
                    mlp.leftMargin=mlp.leftMargin-5;
                    leftring.setLayoutParams(mlp);
                }
                if (event.getAxisValue(MotionEvent.AXIS_HSCROLL) > 0.0f){
                    mlp.leftMargin=mlp.leftMargin+5;
                    leftring.setLayoutParams(mlp);
                }
                if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) < 0.0f){
                    mlp.topMargin=mlp.topMargin+5;
                    leftring.setLayoutParams(mlp);
                }
                if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) > 0.0f){
                    mlp.topMargin=mlp.topMargin-5;
                    leftring.setLayoutParams(mlp);
                }


                break;
        }

        // tell the system that we handled the event and no further processing is required
        return true;
    }

//makes the pattern
    public void right_pattern(double contrast){
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


    public void left_pattern(View view, double contrast){
        Bitmap operation1 = Bitmap.createBitmap(leftbmp.getWidth(), leftbmp.getHeight(), leftbmp.getConfig());

        for(int i=0; i< leftbmp.getWidth(); i++){

            for(int j=0; j< leftbmp.getHeight(); j++){

                double ij=(i-(leftbmp.getWidth()/2))*(i-(leftbmp.getWidth()/2))+(j-(leftbmp.getWidth()/2))*(j-(leftbmp.getWidth()/2));
                double k = (leftbmp.getWidth()/2)*(leftbmp.getWidth()/2);
                double dis=Math.sqrt(2*(leftbmp.getWidth()^2));
                if (ij<=k){
                    int gray_level = (int)((contrast*255/2*Math.cos((-i+j)*Math.PI*.65/dis+1.1)+255/2));

                    operation1.setPixel(i, j, Color.argb(255, gray_level, gray_level, gray_level));}
                else{
                    int test=128;
                    operation1.setPixel(i, j, Color.argb(255, test, test, test));}
            }
        }
        leftimg.setImageBitmap(operation1);
    }


}

