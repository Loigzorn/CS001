package de.karlsruhe.hhs.Calculation;

import de.karlsruhe.hhs.Calculation.GaussianElimination.GaussianElimination;
import de.karlsruhe.hhs.Library.Helpers.LGS;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Calculations {

    public LinkedList<Double> calculateFunction(List<Point2D> points){

        LinkedList<Double> function = new LinkedList<Double>();
        double[][] functions = new double[4][points.size()];
        double[] result = new double[4];
        LGS tempLGS = new LGS();

        Double[] testSteigung = {2.0, -2.0};

        for(int j = 0; j < points.size(); j++) {
            double[] tempFunction = new double[4];
            double[] tempDerivative = new double[4];

            for(int i = tempFunction.length - 1; i >= 0; i--) {
                tempFunction[(tempFunction.length - 1) - i] = (Math.pow(points.get(j).getX(), i));
            }
            functions[j] = tempFunction;
            result[j] = points.get(j).getY();

            for(int i = tempDerivative.length - 2; i >= 0; i--) {
                tempDerivative[(tempDerivative.length -2) - i] = (Math.pow(points.get(j).getX(), i)) * (i + 1);
            }
            tempDerivative[3] = 0;
            functions[j + 2] = tempDerivative;
            result[j + 2] = testSteigung[j];
        }
        tempLGS.fill(functions, result);

        var elimination = new GaussianElimination(tempLGS.getValues(), tempLGS.getResults());
        if(!elimination.isFeasible()) {
            //TODO find alternative if two of the three points have the same x-value.
            JOptionPane.showMessageDialog(null, (String.format(
                    "Could not find a function to cover the range between %s and %s",
                    points.get(0).getX(), points.get(1).getX()) +
                    "\nFor the points: " +
                    "(" + points.get(0).getX() + "," + points.get(0).getY()+ "); " +
                    "(" + points.get(1).getX() + "," + points.get(1).getY()+ "); " ));

            return null;
        }else{
            var primal = elimination.primal();

            for(int i = primal.length - 1; i >= 0; i--){
                function.add(primal[i]);
            }

            for(int i = function.size() - 1; i >= 0; i--) {
                if(i == 0) {
                    System.out.print("(" + function.get(i) + ")");
                }else {
                    System.out.print("(" + function.get(i) + "x^" + i + ") + ");
                }
            }
            System.out.println("");

            return function;
        }
    }

    public LinkedList<Double> calculateFunctionRounded(List<Point2D> points, List<Double> gradients){
        LinkedList<Double> function = new LinkedList<Double>();
        double[][] functions = new double[4][points.size()];
        double[] result = new double[4];
        LGS tempLGS = new LGS();



        Double[] testSteigung = {0.0, 0.0};

        for(int j = 0; j < points.size(); j++) {
            double[] tempFunction = new double[4];
            double[] tempDerivative = new double[4];

            for(int i = tempFunction.length - 1; i >= 0; i--) {
                tempFunction[(tempFunction.length - 1) - i] = (Math.pow(points.get(j).getX(), i));
            }
            functions[j] = tempFunction;
            result[j] = points.get(j).getY();

            for(int i = tempDerivative.length - 2; i >= 0; i--) {
                tempDerivative[(tempDerivative.length -2) - i] = (Math.pow(points.get(j).getX(), i)) * (i + 1);
            }
            tempDerivative[3] = 0;
            functions[j + 2] = tempDerivative;
            result[j + 2] = gradients.get(0);
            gradients.remove(0);
        }
        tempLGS.fill(functions, result);

        var elimination = new GaussianElimination(tempLGS.getValues(), tempLGS.getResults());
        if(!elimination.isFeasible()) {
            //TODO find alternative if two of the three points have the same x-value.
            JOptionPane.showMessageDialog(null, (String.format(
                    "Could not find a function to cover the range between %s and %s",
                    points.get(0).getX(), points.get(1).getX()) +
                    "\nFor the points: " +
                    "(" + points.get(0).getX() + "," + points.get(0).getY()+ "); " +
                    "(" + points.get(1).getX() + "," + points.get(1).getY()+ "); " ));

            return null;
        }else{
            var primal = elimination.primal();

            for(int i = primal.length - 1; i >= 0; i--){
                function.add(primal[i]);
            }

            printFunction(function);
/*
            LinkedList<Double> tempFunction = new LinkedList<Double>();
            for(var value : function){
                tempFunction.add(round(value, 4));
            }
            function = tempFunction;

            printFunction(function);
*/
            return function;
        }
    }

    public double yValue(LinkedList<Double> function, double x){
        double y = 0;
        for(int i = 0; i < function.size(); i++) {
            y = y + (function.get(i) * Math.pow(x, i));
        }
        return y;
    }

    public LinkedList<Double> calculateLinearFunction(List<Point2D> points){
        LinkedList<Double> linearFunction = new LinkedList<Double>();
        double[][] functions = new double[2][points.size()];
        double[] result = new double[2];
        LGS tempLGS = new LGS();

        for(int j = 0; j < points.size(); j++) {
            double[] tempFunction = new double[2];

            for (int i = tempFunction.length - 1; i >= 0; i--) {
                tempFunction[(tempFunction.length - 1) - i] = (Math.pow(points.get(j).getX(), i));
            }
            functions[j] = tempFunction;
            result[j] = points.get(j).getY();
        }

        tempLGS.fill(functions, result);
        var elimination = new GaussianElimination(tempLGS.getValues(), tempLGS.getResults());

        if(!elimination.isFeasible()) {
            JOptionPane.showMessageDialog(null, (String.format("Error")));
        }else{
            var primal = elimination.primal();

            for(int i = primal.length - 1; i >= 0; i--){
                linearFunction.add(primal[i]);
            }
        }

        printFunction(linearFunction);
/*
        LinkedList<Double> tempFunction = new LinkedList<Double>();
        for(var value : linearFunction){
            tempFunction.add(round(value, 4));
        }
        linearFunction = tempFunction;

        printFunction(linearFunction);
*/
        return linearFunction;
    }

    private double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    private void printFunction(LinkedList<Double> function){
        for(int i = function.size() - 1; i >= 0; i--) {
            if(i == 0) {
                System.out.print("(" + function.get(i) + ")");
            }else {
                System.out.print("(" + function.get(i) + "x^" + i + ") + ");
            }
        }
        System.out.println("");
    }
}
