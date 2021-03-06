package com.visionarries.www.eddi;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Locale;

public class Calculation extends WelcomeScreen implements TextToSpeech.OnInitListener {


    //this is just for the graphing
    public static double joy[] = new double[TestPage.contrastR_false.length];
    public static double happy[] = new double[TestPage.contrastR_false.length];
/*
double x[] = {0.1,
0.3,
0.45,
0.65,
0.9
};
double y[] = {
0.1300,
0.7600,
0.5600,
0.8700,
0.7000

};
*/
public static double a = 0.;
    public static double b = 0.;
    public static double x0 = 0.;
/*
double x[] =  { 0.1,
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
0.6

};
double y[] =
{0.1055,
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
0.878

};
*/
public String text;
    //<editor-fold desc="Initialization">
    //gathers the contrast and answers from test page
    double x[] = TestPage.contrastR;
    double y[] = TestPage.answer;
    double database_save[] = new double[y.length];
    //initializing
    double xx[] = new double[x.length];
    double yy[] = new double[x.length];
    double xy[] = new double[x.length];
    double sxx;
    double sx = 0.;
    double sxy = 0.;
    double sy = 0.;
    int m = x.length;
    double d = 0.;
    double k = 0.;
    double error_a=0;
    double error_b=0;
    double s=0;
    double x_ave = 0.;
    double y_ave = 0.;
    double SSxx = 0.;
    double SSyy = 0.;
    double SSxy = 0.;
    double syy = 0.;
    double error_x0 = 0.;
    double r_x0 = 0.;
    double r_error_x0 = 0.;
    TextView value;
    DBAdapter myDb;
    private TextToSpeech tts;

    //</editor-fold>

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //this is just needed
        super.onCreate(savedInstanceState);
        //sets the layout to the inputted ID
        setContentView(R.layout.calc_page);
        tts = new TextToSpeech(this, this);

        System.arraycopy(TestPage.contrastR_false, 0, joy, 0, joy.length);
        System.arraycopy(y, 0, happy, 0, joy.length);
        System.arraycopy(y, 0, database_save, 0, y.length);


//opens the database
        openDB();
        //this for loop will go through the values for the least fit squares, it makes a fit for the sigmoidal function, the theory is shown here: http://web.cecs.pdx.edu/~gerry/nmm/course/slides/ch09Slides.pdf
        for (int i = 0; i < m; i++) {
            x[i] = Math.log10(x[i]); //This is you want the x to be on base 10
            x_ave = x_ave + x[i];
            if (y[i] == 0) {
                y[i] = .01;
            }//zero will give you a DNE error so we have to make it really low instead,
            y[i] = Math.log((1 / y[i]) - 1);
            y_ave = y_ave + y[i];
            xx[i] = x[i] * x[i];
            yy[i] = y[i] * y[i];
            xy[i] = x[i] * y[i];
            sxx = sxx + xx[i];
            syy = syy + yy[i];
            sx = sx + x[i];
            sxy = sxy + xy[i];
            sy = sy + y[i];
        }

        d = (sx * sx) - (m * sxx);
        a = (1 / d) * (sx * sy - m * sxy);
        b = (1 / d) * (sx * sxy - sxx * sy);
        k = -a;
        x0 = 2 * Math.pow(10, -b / a);
        //error method was used from this http://mathworld.wolfram.com/LeastSquaresFitting.html
        x_ave = x_ave / m;
        y_ave = y_ave / m;
        SSxx = sxx - (m * x_ave * x_ave);
        SSyy = syy - (m * y_ave * y_ave);
        SSxy = sxy - (m * x_ave * y_ave);
       // s = Math.pow((SSyy - (Math.pow(SSxy, 2) / SSxx)) / (m - 2), 1 / 2);
        s = Math.pow((SSyy - (SSxy * SSxy / SSxx)) / (m - 2), (.5));
        error_a = s * Math.pow((1 / m) + ((Math.pow(x_ave, 2) / SSxx)), (.5));
        error_b = s / Math.pow(SSxx,.5);
        error_x0 = x0*(b/a)*Math.pow(Math.pow(error_a / a, 2) + Math.pow(error_b / b, 2),.5);
        r_x0 = Math.round((x0 - 1) * 100) / 100D;//subtracted 1 to make values go from -1, to 1, rather than 0 to 2
        r_error_x0 = Math.round(error_x0*100) / 100D;

//displaying the calculated values, the who thing basically takes less than a second to calculate.
        value = (TextView) findViewById(R.id.DomIndex);
        text = "Calculation complete. The ocular dominance value is " + String.valueOf(r_x0)+". The error is " + String.valueOf(r_error_x0)+".";
        value.setText(text);
        if (!DataSave.name.equals("")) {//if you made a profile (which is the same as having this field not be bland/null then you'll add these values to a database.
for (int i=0;i<y.length; i++){database_save[i]=Math.round((database_save[i])*100) / 100D;}
            myDb.insertRow(DataSave.name,r_x0,Arrays.toString(database_save));
//            Intent intent = new Intent(Calculation.this, Database_main.class);
//            startActivity(intent);
        }

    }

    public void onClick_GoHome(View v) {
        WelcomeScreen.doRestart(this);
    }
    public void onClick_Graph(View v) {
        Intent act2 = new Intent(v.getContext(), Graph.class);
        startActivity(act2);
    }

    //good to have stops the device from using power for tts
    @Override
    protected void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        closeDB();
        super.onDestroy();


    }

    private void speakOut(String text) {

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    //tts
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.ENGLISH);

            //tts.setPitch(-10); // set pitch level

            // tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                speakOut(text);
            }

        } else {
            Log.e("TTS", "Initialization Failed");
        }

    }

    //opening and closing the database
    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }
}