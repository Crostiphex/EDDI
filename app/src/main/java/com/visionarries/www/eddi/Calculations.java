package com.visionarries.www.eddi;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Calculations extends MainActivity{

    //    double x[] =  {.05,.10,.20,.40,.45,.50,.55,.60,.80,.90,.95};
//    double y[]=new double[x.length];
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
    TextView value;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //this is just needed
        super.onCreate(savedInstanceState);
        //sets the layout to the inputted ID
        setContentView(R.layout.calc_page);
        for(int i=0; i<m; i++){
            //y[i]=DataSave.time_pressed[i]/60; //to get the data to be a percentage of a minute
           // x[i]=Math.log10(x[i]); //This is you want the x to be on base 10
            y[i] = Math.log((1/y[i])-1);
            xx[i] = x[i]*x[i];
            xy[i] = x[i]*y[i];
            sxx=sxx+xx[i];
            sx=sx+x[i];
            sxy=sxy+xy[i];
            sy=sy+y[i];}

        d=sx*sx-m*sxx;
        a=(1/d)*(sx*sy-m*sxy);
        b=(1/d)*(sx*sxy-sxx*sy);
        k=-a;
        x0=-b/a;
        value = (TextView) findViewById(R.id.DomIndex);
        value.setText("x0 is "+x0);
        int duration = Toast.LENGTH_LONG;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "The Dominance Index is: " + x0, duration);
        toast.show();}}

