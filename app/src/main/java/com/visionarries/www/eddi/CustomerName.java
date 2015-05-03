package com.visionarries.www.eddi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class CustomerName extends WelcomeScreen {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_data);
//when you push the button it saves the name

        final Button switch_act =(Button) findViewById(R.id.nameInput);
        switch_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2 = new Intent(CustomerName.this, Calibration.class);
                EditText edit_text = (EditText) findViewById(R.id.nameString);

                DataSave.name=edit_text.getText().toString();
                startActivity(act2);


            }
        });
    }

}
