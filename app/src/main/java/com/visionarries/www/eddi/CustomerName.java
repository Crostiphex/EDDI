package com.visionarries.www.eddi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class CustomerName extends MainActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_data);


        final Button switchact =(Button) findViewById(R.id.nameInput);
        switchact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2 = new Intent(view.getContext(),TestScreen.class);
                startActivity(act2);

                EditText edit_text = (EditText) findViewById(R.id.nameString);
                CharSequence edit_text_value = edit_text.getText();

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, "Customer Data saved as: "+edit_text_value, duration);
                toast.show();

WriteBtn(view,edit_text.getText().toString());



            }
        });
    }
    // write text to file
    public void WriteBtn (View v,String edit_text) {
        // add-write text into file
        try {
            FileOutputStream file_out=openFileOutput(edit_text+".txt",MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(file_out);
            outputWriter.write(edit_text);
            outputWriter.close();

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
File dir = getDir("name.txt",MODE_PRIVATE);
            Toast toast = Toast.makeText(context, "Customer Data saved as: "+edit_text+dir, duration);
            toast.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
