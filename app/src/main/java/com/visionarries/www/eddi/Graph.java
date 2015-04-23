package com.visionarries.www.eddi;

import android.os.Bundle;
import android.view.WindowManager;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import java.util.Arrays;


/**
 * A straightforward example of using AndroidPlot to plot some data.
 */
public class Graph extends WelcomeScreen
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // fun little snippet that prevents users from taking screenshots
        // on ICS+ devices :-)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.simple_xy_plot_example);

        // initialize our XYPlot reference:
        XYPlot plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

        double[] contrastR = Calculation.joy;
        double[] sorted_contrastR = new double[contrastR.length];
        System.arraycopy(contrastR, 0, sorted_contrastR, 0, contrastR.length);
        double[] sorted_time= new double[contrastR.length];
        double [] unsorted_time= Calculation.happy;
        double [] array_1 = new double[contrastR.length*2];
        double [] array_2 = new double[contrastR.length*2];
        Arrays.sort(sorted_contrastR);

       for ( int i = 0 ; i < contrastR.length ; i++ ) {
              for ( int j = 0 ; j < contrastR.length ; j++ ) {
                if(sorted_contrastR[i]==contrastR[j]){

                    sorted_time[i]=unsorted_time[j];
                }
            }
        }
        for ( int i = 0 ; i < contrastR.length ; i++ ) {
            array_1[2*i]=Math.log10(sorted_contrastR[i]);
            array_2[2*i]=Math.log10(sorted_contrastR[i]);
            array_1[2*i+1]=sorted_time[i];
            array_2[2*i+1]=fit(Math.log10(sorted_contrastR[i]));

        }

        Number[] series1Numbers=new Number[array_1.length*2];
        Number[] series2Numbers=new Number[array_1.length*2];
        for ( int i = 0 ; i < array_1.length ; i++ ) {
            series1Numbers[i]=Math.round(array_1[i] * 100) / 100D;
            series2Numbers[i]=Math.round(array_2[i] * 100) / 100D;
        }

        // Create a couple arrays of y-values to plot:


        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array_1 into a List
                SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, // Y_VALS_ONLY means use the element index as the x value
                "Data");                             // Set the display title of the series

// same as above
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, "Fit");

        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_plf1);

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
// same as above:
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_plf2);
        plot.addSeries(series2, series2Format);


        // reduce the number of range labels
        plot.setTicksPerRangeLabel(2);
        plot.getGraphWidget().setDomainLabelOrientation(-45);
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, .1);
        plot.setRangeBoundaries(0.0, 1.0, BoundaryMode.FIXED);

    }

    public double fit (double x){
        {
            return (1/( 1 + Math.pow(Math.E,(-1*(-Calculation.a)*(x-(-Calculation.b/Calculation.a))))));
        }
    }
}
