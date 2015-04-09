package com.visionarries.www.eddi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class Calculations extends WelcomeScreen {
TextToSpeech tts_object;

    //    double x[] =  {.05,.10,.20,.40,.45,.50,.55,.60,.80,.90,.95};
 // double y[]=DataSave.time_pressed;
    double x[]={0.1,
            0.35,
            0.5,
            0.7,
            0.85,
            0.2,
            0.3,
            0.45,
            0.75,
            0.9,
            0.15,
            0.25,
            0.55,
            0.65,
            0.6};
    double y[]={0.1055,
            0.4836,
            0.7712,
            0.7554,
            0.8531,
            0.1671,
            0.4146,
            0.6077,
            0.9079,
            0.8648,
            0.1874,
            0.3254,
            0.6852,
            0.8494,
            0.878};
    double xx[]=new double[x.length];
    double yy[]=new double[x.length];
    double xy[]=new double[x.length];
    double sxx=0;
    double sx=0;
    double sxy=0;
    double sy=0;
    int m = x.length;
    double d=0;
    double a=0;
    double b=0;
    double k=0;
    double x0=0;
    double error_a ;
    double error_b ;
    double s;
    double x_ave=0;
    double y_ave=0;
    double SSxx = 0;
    double SSyy = 0;
    double SSxy = 0;
    double syy = 0;
    double error_x0 = 0;

    TextView value;
    DBAdapter myDb;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //this is just needed
        super.onCreate(savedInstanceState);
        //sets the layout to the inputted ID
        setContentView(R.layout.calc_page);
        tts_object = new TextToSpeech(Calculations.this, new TextToSpeech.OnInitListener(){
            @Override
        public void onInit(int status){
                tts_object.setLanguage(Locale.ENGLISH);
            }
        });
        openDB();
        for(int i=0; i<m; i++){
            //y[i]=DataSave.time_pressed[i]/60; //to get the data to be a percentage of a minute
           x[i]=Math.log10(x[i]); //This is you want the x to be on base 10
            x_ave=x_ave+x[i];
            y[i] = Math.log((1/y[i])-1);
            y_ave=y_ave+y[i];
            xx[i] = x[i]*x[i];
            yy[i] = y[i]*y[i];
            xy[i] = x[i]*y[i];
            sxx=sxx+xx[i];
            syy=syy+yy[i];
            sx=sx+x[i];
            sxy=sxy+xy[i];
            sy=sy+y[i];}

        d=sx*sx-m*sxx;
        a=(1/d)*(sx*sy-m*sxy);
        b=(1/d)*(sx*sxy-sxx*sy);
        k=-a;
        x0=Math.pow(10,-b/a);
        //error http://mathworld.wolfram.com/LeastSquaresFitting.html
x_ave=x_ave/m; y_ave=y_ave/m;
        SSxx=sxx-m*x_ave*x_ave;
        SSyy=syy-m*y_ave*y_ave;
        SSxy=sxy-m*x_ave*y_ave;
        s=Math.pow((SSyy-Math.pow(SSxy,2)/SSxx)/(m-2),1/2);
        error_a = s*Math.pow((1/m)+(Math.pow(x_ave,2)/SSxx),(1/2));
        error_b = s/Math.pow(SSxx,1/2);
        error_x0 = (b/a)*Math.pow(Math.pow(error_a/a,2)+Math.pow(error_b/b,2),1/2);
        error_x0 = error_x0*x0;


        value = (TextView) findViewById(R.id.DomIndex);
        value.setText("x0 is "+x0);
        int duration = Toast.LENGTH_LONG;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "The Dominance Index is: " + x0 + ". The error is: " + error_x0, duration);
        toast.show();
        tts_object.speak("Hello, the test is completed.", TextToSpeech.QUEUE_FLUSH, null,null);
//ttsobject.speak("Hello, the test is completed.",TextToSpeech.QUEUE_FLUSH,null,"");

        if (!DataSave.name.equals("")){
        myDb.insertRow(DataSave.name, x0);

        Intent intent = new Intent(Calculations.this, Database_main.class);
        startActivity(intent);}

    }

        @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }


    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }
    private void closeDB() {
        myDb.close();
    }
}

