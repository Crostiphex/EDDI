package com.visionarries.www.eddi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Christopher on 4/27/2015.
 */
public class Password_screen extends WelcomeScreen{

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //this is just needed
        super.onCreate(savedInstanceState);
        //sets the layout to the inputted ID
        setContentView(R.layout.password_screen);


}

    public void onClick_Password(View v) {
        EditText edit_text = (EditText) findViewById(R.id.pw);
        if(edit_text.getText().toString().equals("3341")){
        Intent act2 = new Intent(v.getContext(), Database_main.class);
        startActivity(act2);}
        else{
            Context context = getApplicationContext();
            CharSequence text = "Wrong password, enter again.";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }}
