package com.visionarries.www.eddi;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TestScreen extends MainActivity{

    private ImageView leftimg;
    private Bitmap leftbmp;
    private ImageView rightimg;
    private Bitmap rightbmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_screen);

        leftimg = (ImageView) findViewById(R.id.leftGrating);
        BitmapDrawable leftabmp = (BitmapDrawable) leftimg.getDrawable();
        leftbmp = leftabmp.getBitmap();

        rightimg = (ImageView) findViewById(R.id.rightGrating);
        BitmapDrawable rightabmp = (BitmapDrawable) rightimg.getDrawable();
        rightbmp = rightabmp.getBitmap();

        final Button makePattern =(Button)findViewById(R.id.makePattern);
        makePattern.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                leftgray(view);
           rightgray(view);
            }
        });
    }


    double  contrast = 1.0;

    public void leftgray(View view){
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
        rightimg.setImageBitmap(operation);
    }

    public void rightgray(View view){
        Bitmap operation = Bitmap.createBitmap(rightbmp.getWidth(), rightbmp.getHeight(), rightbmp.getConfig());

        for(int i=0; i< rightbmp.getWidth(); i++){

            for(int j=0; j< rightbmp.getHeight(); j++){

                double ij=(i-(rightbmp.getWidth()/2))^2*(j-(rightbmp.getWidth()/2))^2;
                double k = (rightbmp.getWidth()/2)*(rightbmp.getWidth()/3);
                double dis=Math.sqrt(2*(rightbmp.getWidth()^2));
                if (ij<=k){
                    int test = (int)((contrast*255/2*Math.cos((-i+j)*Math.PI*.45/dis)+255/2));
                    //(j-i*Math.sqrt(3)-2*Math.cos((j*Math.sqrt(3)+i)/2));

                    operation.setPixel(i, j, Color.argb(255, test, test, test));}
                else{
                    int test=128;
                    operation.setPixel(i, j, Color.argb(255, test, test, test));}
            }
        }
        rightimg.setImageBitmap(operation);
    }

}

