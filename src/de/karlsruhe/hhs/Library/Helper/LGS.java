package de.karlsruhe.hhs.Library.Helper;

public class LGS {
    private double[][] v = new double[3][3];
    private double[] r = new double[3];

    public void fill(double[][] values, double[] results){
        v = values;
        r = results;
    }

    public double[][] getValues(){
        return v;
    }

    public double[] getResults(){
        return r;
    }
}
