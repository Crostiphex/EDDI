package com.visionarries.www.eddi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;




public class Calibration extends Activity {
int a= 0;
    //defining the images

    private ImageView leftring;
    private ImageView rightring;
    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) leftring.getLayoutParams();
    ViewGroup.MarginLayoutParams plm = (ViewGroup.MarginLayoutParams) rightring.getLayoutParams();

    //defining the seekbar and text underneath it
    SeekBar seekbar;
    TextView value;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this is just needed
        super.onCreate(savedInstanceState);
        //sets the layout to the inputted ID
        setContentView(R.layout.calibration_screen);

        //so we can call and edit the values
        value = (TextView) findViewById(R.id.textView);
        seekbar = (SeekBar) findViewById(R.id.seekBar);



leftring = (ImageView) findViewById(R.id.leftFocusRing);
        rightring = (ImageView) findViewById(R.id.rightFocusRing);

//this button makes the pattern
        final Button makePattern =(Button)findViewById(R.id.makePattern);

        final ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) leftring.getLayoutParams();
           leftring.setLayoutParams(mlp);
       final ViewGroup.MarginLayoutParams plm = (ViewGroup.MarginLayoutParams) rightring.getLayoutParams();
        rightring.setLayoutParams(plm);

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



DataSave.from_left_left=mlp.leftMargin;
DataSave.from_top_left=mlp.topMargin;
DataSave.from_left_right=plm.leftMargin;
DataSave.from_top_right=plm.topMargin;


                Intent intent = new Intent(Calibration.this, VoiceControl.class);
                startActivity(intent);

            }
        });
    }



    public boolean onGenericMotionEvent(MotionEvent event) {

        int scroll_action = event.getAction();
int button_click = event.getButtonState();
//        Toast toast_finger_move = Toast.makeText(context, "You moved me.", duration);
        switch (button_click) {
            case MotionEvent.BUTTON_PRIMARY:
                Intent intent = new Intent(Calibration.this, VoiceControl.class);
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

