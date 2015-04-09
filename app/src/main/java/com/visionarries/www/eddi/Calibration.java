package com.visionarries.www.eddi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;




public class Calibration extends Activity {
int a= 0;
    //defining the images

    private ImageView leftring;
    private ImageView rightring;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this is just needed
        super.onCreate(savedInstanceState);
        //sets the layout to the inputted ID
        setContentView(R.layout.calibration_screen);




leftring = (ImageView) findViewById(R.id.leftFocusRing);
        rightring = (ImageView) findViewById(R.id.rightFocusRing);

//this button makes the pattern

        final ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) leftring.getLayoutParams();
           leftring.setLayoutParams(mlp);
       final ViewGroup.MarginLayoutParams plm = (ViewGroup.MarginLayoutParams) rightring.getLayoutParams();
        rightring.setLayoutParams(plm);


    }



    public boolean onGenericMotionEvent(MotionEvent event) {
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) leftring.getLayoutParams();
        ViewGroup.MarginLayoutParams plm = (ViewGroup.MarginLayoutParams) rightring.getLayoutParams();
        int scroll_action = event.getAction();
int button_click = event.getButtonState();
//        Toast toast_finger_move = Toast.makeText(context, "You moved me.", duration);
        switch (button_click) {
            case MotionEvent.BUTTON_PRIMARY:
                DataSave.from_left_left=mlp.leftMargin;
                DataSave.from_top_left=mlp.topMargin;
                DataSave.from_left_right=plm.leftMargin;
                DataSave.from_top_right=plm.topMargin;

                Intent intent = new Intent(Calibration.this, TestPage.class);
                startActivity(intent);
                break;
            case MotionEvent.BUTTON_TERTIARY:
                a=a+1;
                break;}

        switch (scroll_action) {
            case MotionEvent.ACTION_SCROLL:
                // finger leaves the screen
                if(a%2==0){
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
                }}
                if(a%2==1){
                    if (event.getAxisValue(MotionEvent.AXIS_HSCROLL) < 0.0f){
                        plm.leftMargin=plm.leftMargin-5;
                        rightring.setLayoutParams(plm);
                    }
                    if (event.getAxisValue(MotionEvent.AXIS_HSCROLL) > 0.0f){
                        plm.leftMargin=plm.leftMargin+5;
                        rightring.setLayoutParams(plm);
                    }
                    if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) < 0.0f){
                        plm.topMargin=plm.topMargin+5;
                        rightring.setLayoutParams(plm);
                    }
                    if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) > 0.0f){
                        plm.topMargin=plm.topMargin-5;
                        rightring.setLayoutParams(plm);
                    }}


                break;
        }

        // tell the system that we handled the event and no further processing is required
        return true;
    }}

