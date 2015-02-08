package com.visionarries.www.eddi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class CustomerName extends MainActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_data);


        final Button switch_act =(Button) findViewById(R.id.nameInput);
        switch_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2 = new Intent(view.getContext(), TestScreen.class);
                startActivity(act2);

                EditText edit_text = (EditText) findViewById(R.id.nameString);
                CharSequence edit_text_value = edit_text.getText();

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, "Customer Data saved as: " + edit_text_value, duration);
                toast.show();


            }
        });
    }
}