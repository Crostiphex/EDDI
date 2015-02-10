package com.visionarries.www.eddi;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TestScreen extends MainActivity{

    //defining the images
    private ImageView leftimg;
    private Bitmap leftbmp;
    private ImageView rightimg;
    private Bitmap rightbmp;
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
                public void onClick(View view) {
                //this is to keep track of how long it takes for the image functions to be executed.
                long startTime = System.currentTimeMillis();
//calls the function that rewrites thes the bitmap data for the left and right images

                left_pattern(view, 1.0);
                //calls the value of what the seekbar is.
                double right=seekbar.getProgress()/100.0;
                right_pattern(view, right);
                long endTime = System.currentTimeMillis();

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
//toast to show the time it takes to run the programs
                Toast toast = Toast.makeText(context, "That took " + (endTime - startTime) + " milliseconds", duration);
                toast.show();

            }
        });
    }



//makes the pattern
    public void left_pattern(View view, double contrast){
        Bitmap operation = Bitmap.createBitmap(leftbmp.getWidth(), leftbmp.getHeight(), leftbmp.getConfig());
//the loop goes through each picture
        for(int i=0; i< leftbmp.getWidth(); i++){
            for(int j=0; j< leftbmp.getHeight(); j++){

                double ij=(i-(leftbmp.getWidth()/2))*(i-(leftbmp.getWidth()/2))+(j-(leftbmp.getWidth()/2))*(j-(leftbmp.getWidth()/2));
                double k = (leftbmp.getWidth()/2)*(leftbmp.getWidth()/2);
                double dis=Math.sqrt(2*(leftbmp.getWidth()^2));

                if (ij<=k){//displays pattern in a circle

                    int gray_level = (int)((contrast*(255/2)*Math.cos((i+j)*Math.PI*(.65/dis))+(255/2))); //pattern
                    operation.setPixel(i, j, Color.argb(255, gray_level, gray_level, gray_level));}//actually assigns the values.

                else{
                    int test=128;
                    operation.setPixel(i, j, Color.argb(255, test, test, test));}


            }
        }
       leftimg.setImageBitmap(operation);
    }


    public void right_pattern(View view, double contrast){
        Bitmap operation1 = Bitmap.createBitmap(rightbmp.getWidth(), rightbmp.getHeight(), rightbmp.getConfig());

        for(int i=0; i< rightbmp.getWidth(); i++){
            for(int j=0; j< rightbmp.getHeight(); j++){

                double ij=(i-(rightbmp.getWidth()/2))*(i-(rightbmp.getWidth()/2))+(j-(rightbmp.getWidth()/2))*(j-(rightbmp.getWidth()/2));
                double k = (rightbmp.getWidth()/2)*(rightbmp.getWidth()/2);
                double dis=Math.sqrt(2*(rightbmp.getWidth()^2));
                if (ij<=k){
                    int gray_level = (int)((contrast*255/2*Math.cos((-i+j)*Math.PI*.65/dis+1.1)+255/2));

                    operation1.setPixel(i, j, Color.argb(255, gray_level, gray_level, gray_level));}
                else{
                    int test=128;
                    operation1.setPixel(i, j, Color.argb(255, test, test, test));}
            }
        }
        rightimg.setImageBitmap(operation1);
    }

}

