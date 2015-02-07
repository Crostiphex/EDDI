package com.visionarries.www.eddi;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TestScreen extends MainActivity{

    private ImageView img;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_screen);

        img = (ImageView)findViewById(R.id.leftGrating);
        BitmapDrawable abmp = (BitmapDrawable)img.getDrawable();
        bmp = abmp.getBitmap();
    }


    double  contrast = 1.0;

    public void gray(View view){
        Bitmap operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig());

        for(int i=0; i<bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){

                double ij=(i-(bmp.getWidth()/2))*(i-(bmp.getWidth()/2))+(j-(bmp.getWidth()/2))*(j-(bmp.getWidth()/2));
                double k = (bmp.getWidth()/2)*(bmp.getWidth()/2);
                double dis=Math.sqrt(2*(bmp.getWidth()^2));

                if (ij<=k){//displays pattern in a circle

                    int test = (int)((contrast*(255/2)*Math.cos((i+j)*Math.PI*(.45/dis))+(255/2))); //pattern
                    operation.setPixel(i, j, Color.argb(255, test, test, test));}

                else{
                    int test=128;
                    operation.setPixel(i, j, Color.argb(255, test, test, test));}


            }
        }
        img.setImageBitmap(operation);
    }}

}

