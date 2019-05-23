package de.karlsruhe.hhs.Library;

import java.util.LinkedList;

public class FunctionFromPoint {
    public void function(){
        LinkedList<LinkedList<Double>> functions = new LinkedList<LinkedList<Double>>();
        LinkedList<double[]> points = new LinkedList<double[]>();

        double[] temp = {-1.0, 2.0};
        points.add(temp);
        temp = new double[]{2.0, -1.0};
        points.add(temp);
        temp = new double[]{-3.0, 44.0};
        points.add(temp);
        temp = new double[]{1.0, 0.0};
        points.add(temp);

        for(double[] point : points) {
            LinkedList<Double> tempFunction = new LinkedList<Double>();

            for(int i = 0; i < points.size(); i++) {
                tempFunction.add(Math.pow(point[0], i));
            }
            functions.add(tempFunction);
        }

        for(int j = 0; j < functions.size(); j++) {
            for(int i = functions.get(j).size() - 1; i >= 0; i--) {
                System.out.print("(" + functions.get(j).get(i) + "a" + i + ") + ");
            }
            System.out.println(" = "+ points.get(j)[1]);
            System.out.println("");
        }
    }
}
