package com.visionarries.www.eddi;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class Calculation extends WelcomeScreen implements TextToSpeech.OnInitListener {
    public String text;
    //double x[] =  {.05,.10,.20,.40,.45,.50,.55,.60,.80,.90,.95};

    //<editor-fold desc="Initialization">
    double x[] = TestPage.contrastR;

   double xx[] = new double[x.length];
    double yy[] = new double[x.length];
    double xy[] = new double[x.length];
    double sxx;
    double sx = 0.;
    double sxy = 0.;
    double sy = 0.;
    int m = x.length;
    double d = 0.;
    double a = 0.;
    double b = 0.;
    double k = 0.;
    double x0 = 0.;
    double error_a;
    double error_b;
    double s;
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

        double y[] = TestPage.answer;
        openDB();
        for (int i = 0; i < m; i++) {
                        x[i] = Math.log10(x[i]); //This is you want the x to be on base 10
            y[i]=y[i]/TestPage.seconds;
            x_ave = x_ave + x[i];
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

        d = sx * sx - m * sxx;
        a = (1 / d) * (sx * sy - m * sxy);
        b = (1 / d) * (sx * sxy - sxx * sy);
        k = -a;
        x0 = Math.pow(10, -b / a);
        //error http://mathworld.wolfram.com/LeastSquaresFitting.html
        x_ave = x_ave / m;
        y_ave = y_ave / m;
        SSxx = sxx - m * x_ave * x_ave;
        SSyy = syy - m * y_ave * y_ave;
        SSxy = sxy - m * x_ave * y_ave;
        s = Math.pow((SSyy - Math.pow(SSxy, 2) / SSxx) / (m - 2), 1 / 2);
        error_a = s * Math.pow((1 / m) + (Math.pow(x_ave, 2) / SSxx), (1 / 2));
        error_b = s / Math.pow(SSxx, 1 / 2);
        error_x0 = (b / a) * Math.pow(Math.pow(error_a / a, 2) + Math.pow(error_b / b, 2), 1 / 2);
        error_x0 = error_x0 * x0;
        r_x0 = Math.round(x0 / .5 * 100) / 100D;
        r_error_x0 = Math.round(error_x0 / .5 * 100) / 100D;


        value = (TextView) findViewById(R.id.DomIndex);
        text = "Calculation complete. The ocular dominance value is " + String.valueOf(r_x0)+". The error is " + String.valueOf(r_error_x0);
        // text="A customer is requesting assistance, A customer is requesting assistance, A customer is requesting assistance, A customer is requesting assistance, A customer is requesting assistance";
        value.setText(text);
        if (!DataSave.name.equals("")) {
            myDb.insertRow(DataSave.name, r_x0);
            Intent intent = new Intent(Calculation.this, Database_main.class);
            startActivity(intent);
        }

    }

    public void onClick_GoHome(View v) {
        Intent act2 = new Intent(v.getContext(), WelcomeScreen.class);
        startActivity(act2);
    }

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
            Log.e("TTS", "Initilization Failed");
        }

    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }
}