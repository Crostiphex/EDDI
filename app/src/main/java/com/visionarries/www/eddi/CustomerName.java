package com.visionarries.www.eddi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

<<<<<<< HEAD
=======
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
>>>>>>> bbeb9ddaceb54f327fa518092a986325f5bf52c3


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

<<<<<<< HEAD
=======
WriteBtn(view,edit_text.getText().toString());


>>>>>>> bbeb9ddaceb54f327fa518092a986325f5bf52c3

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

File dir = getDir("name.txt",MODE_PRIVATE);

           Toast.makeText(context, "Customer Data saved as: "+edit_text+". The directory is: "+dir, Toast.LENGTH_LONG).show();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
