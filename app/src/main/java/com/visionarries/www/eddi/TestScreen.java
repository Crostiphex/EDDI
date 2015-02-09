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

    private ImageView leftimg;
    private Bitmap leftbmp;
    private ImageView rightimg;
    private Bitmap rightbmp;

    SeekBar seekbar;
    TextView value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_screen);

        value = (TextView) findViewById(R.id.textView);
        seekbar = (SeekBar) findViewById(R.id.seekBar);


        leftimg = (ImageView) findViewById(R.id.leftGrating);
        BitmapDrawable leftabmp = (BitmapDrawable) leftimg.getDrawable();
        leftbmp = leftabmp.getBitmap();

        rightimg = (ImageView) findViewById(R.id.rightGrating);
        BitmapDrawable rightabmp = (BitmapDrawable) rightimg.getDrawable();
        rightbmp = rightabmp.getBitmap();

        final Button makePattern =(Button)findViewById(R.id.makePattern);
        seekbar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
            {
                // TODO Auto-generated method stub
                value.setText("Contrast value is "+progress+" %");


            }

            public void onStartTrackingTouch(SeekBar seekBar)
            {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar)
            {
                // TODO Auto-generated method stub
            }
        });

        makePattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long startTime = System.currentTimeMillis();

                leftgray(view, 1.0);
                double right=seekbar.getProgress()/100.0;

                rightgray(view, right);
                long endTime = System.currentTimeMillis();

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, "That took " + (endTime - startTime) + " milliseconds", duration);
                toast.show();

            }
        });
    }




    public void leftgray(View view, double contrast){
        Bitmap operation = Bitmap.createBitmap(leftbmp.getWidth(), leftbmp.getHeight(), leftbmp.getConfig());

        for(int i=0; i< leftbmp.getWidth(); i++){
            for(int j=0; j< leftbmp.getHeight(); j++){

                double ij=(i-(leftbmp.getWidth()/2))*(i-(leftbmp.getWidth()/2))+(j-(leftbmp.getWidth()/2))*(j-(leftbmp.getWidth()/2));
                double k = (leftbmp.getWidth()/2)*(leftbmp.getWidth()/2);
                double dis=Math.sqrt(2*(leftbmp.getWidth()^2));

                if (ij<=k){//displays pattern in a circle

                    int test = (int)((contrast*(255/2)*Math.cos((i+j)*Math.PI*(.45/dis))+(255/2))); //pattern
                    operation.setPixel(i, j, Color.argb(255, test, test, test));}

                else{
                    int test=128;
                    operation.setPixel(i, j, Color.argb(255, test, test, test));}


            }
        }
       leftimg.setImageBitmap(operation);
    }

    public void rightgray(View view, double contrast){
        Bitmap operation1 = Bitmap.createBitmap(rightbmp.getWidth(), rightbmp.getHeight(), rightbmp.getConfig());

        for(int i=0; i< rightbmp.getWidth(); i++){

            for(int j=0; j< rightbmp.getHeight(); j++){

                double ij=(i-(rightbmp.getWidth()/2))*(i-(rightbmp.getWidth()/2))+(j-(rightbmp.getWidth()/2))*(j-(rightbmp.getWidth()/2));
                double k = (rightbmp.getWidth()/2)*(rightbmp.getWidth()/2);
                double dis=Math.sqrt(2*(rightbmp.getWidth()^2));
                if (ij<=k){
                    int test = (int)((contrast*255/2*Math.cos((-i+j)*Math.PI*.45/dis)+255/2));
                    //(j-i*Math.sqrt(3)-2*Math.cos((j*Math.sqrt(3)+i)/2));

                    operation1.setPixel(i, j, Color.argb(255, test, test, test));}
                else{
                    int test=128;
                    operation1.setPixel(i, j, Color.argb(255, test, test, test));}
            }
        }
        rightimg.setImageBitmap(operation1);
    }

}

