package com.visionarries.www.eddi;


import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.analysis.function.Logistic;


public class Calculations{
        public static void main(String[] args) {
            double[] x = new double[] { 1,2,3,4,5,6,7,8,9,10 };
            double[] y = new double[] {0.1,0.2,0.3,0.33,
                    0.343,0.35,0.351,0.3511,0.35111,0.351111};

            ParametricUnivariateFunction f = new Logistic.Parametric();


        }
}
