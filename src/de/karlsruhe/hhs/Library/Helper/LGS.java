package de.karlsruhe.hhs.Library.Helper;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

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

    public LGS calculateLGS(List<Point2D> points){

        double[][] functions = new double[3][points.size()];
        double[] result = new double[3];
        LGS tempLGS = new LGS();

        for(int j = 0; j < points.size(); j++) {
            double[] tempFunction = new double[3];

            for(int i = tempFunction.length - 1; i >= 0; i--) {
                tempFunction[2 - i] = (Math.pow(points.get(j).getX(), i));
            }
            functions[j] = tempFunction;
            result[j] = points.get(j).getY();
        }
        tempLGS.fill(functions, result);

        return tempLGS;
    }

    public LinkedList<Double> createFunction(double[] results){
        LinkedList<Double> function = new LinkedList<Double>();

        for(int i = results.length - 1; i >= 0; i--){
            function.add(results[i]);
        }

        return function;
    }
}
