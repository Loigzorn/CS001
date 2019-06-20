package de.karlsruhe.hhs.Library;

import de.karlsruhe.hhs.Calculation.Calculations;
import de.karlsruhe.hhs.Plotter.Plotter;
import de.karlsruhe.hhs.Reader.Reader;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Program {

    private String dataPointsPath = "./src/Debug/Data/Measurements/Points.csv";
    private List<Point2D.Double> dataEntries = new LinkedList<>();
    private int sizeOfDataEntries;
    private List<LinkedList<Double>> functionsPerSequence = new LinkedList<>();
    private List<LinkedList<Point2D>> pointsCorrespondingToSequence = new LinkedList<>();

    public void startApplication() {
        readFile();
        calculateFunctions();
        startUserInterface();
    }

    private void readFile() {
        Reader reader = new Reader(dataPointsPath);
        var dataPoints = reader.readFile();
        dataEntries = reader.convert(dataPoints);
        //A::B refers to method B in class A.
        //in this case getX is a inherited method of Point2D from the class Point.
        dataEntries.sort(Comparator.comparing(Point2D::getX));
        sizeOfDataEntries = dataEntries.size();
    }

    private void calculateFunctions() {
        var lastIndexToIterateTo = determineLastIndexToIterateTo();
        var calc = new Calculations();
        List<LinkedList<Double>> linearFunctions = new ArrayList<LinkedList<Double>>();
        List<Double> gradients = new ArrayList<Double>();
        List<Double> mediants = new ArrayList<Double>();
        List<Double> activeGradients = new ArrayList<Double>();

        for(int i = 0; i < lastIndexToIterateTo - 1; i = i + 1){
            var entries = getEntries(i);
            var linearFunction = calc.calculateLinearFunction(entries);
            linearFunctions.add(linearFunction);
        }
        if(lastIndexToIterateTo != sizeOfDataEntries){
            var entries = getEntries(sizeOfDataEntries-2);
            var linearFunction = calc.calculateLinearFunction(entries);
            linearFunctions.add(linearFunction);
        }
        var startGradient = getGradientFromUserInput("Input a start Gradient");
        var endGradient = getGradientFromUserInput("Input a end Gradient");
        gradients.add(startGradient);
        for(int i = 0; i < linearFunctions.size(); i++){
            gradients.add(linearFunctions.get(i).get(1));
        }
        gradients.add(endGradient);

        mediants.add(gradients.get(0));
        for(int i = 0; i < gradients.size() - 1; i++){
            mediants.add((gradients.get(i) + gradients.get(i + 1)) / 2);
        }
        mediants.remove(mediants.size() - 1);
        mediants.remove(mediants.size() - 1);
        mediants.add(endGradient);

        for (int i = 0; i < lastIndexToIterateTo - 1; i = i + 1) {
            activeGradients.add(mediants.get(0));
            activeGradients.add(mediants.get(1));
            mediants.remove(0);
            var entries = getEntries(i);
            var function = calc.calculateFunctionRounded(entries, activeGradients);
            pointsCorrespondingToSequence.add(entries);
            functionsPerSequence.add(function);
        }

        if (lastIndexToIterateTo != sizeOfDataEntries) {
            activeGradients.add(mediants.get(0));
            activeGradients.add(mediants.get(1));
            var entries = getEntries(sizeOfDataEntries-2);
            var function = calc.calculateFunctionRounded(entries, activeGradients);
            pointsCorrespondingToSequence.add(entries);
            functionsPerSequence.add(function);
        }

    }

    private double getGradientFromUserInput(String userRequest) {
        var inputGraient = 0.0;
        boolean graientValid = false;
        do {
            try{
                inputGraient = Double.parseDouble(JOptionPane.showInputDialog(userRequest));
            } catch (Exception e) {
                continue;
            }
            graientValid = true;
        } while (!graientValid);

        return inputGraient;
    }

    private LinkedList<Point2D> getEntries(int from) {
        var to = from + 2;
        if (to > sizeOfDataEntries) {
            to = sizeOfDataEntries;
        }
        LinkedList<Point2D> entries = new LinkedList<>();
        for(int i = from; i < to; i ++) {
            entries.add(dataEntries.get(i));
        }
        return entries;
    }

    private int determineLastIndexToIterateTo() {
        if(sizeOfDataEntries == 0) {
            return sizeOfDataEntries;
        }
        var isFactorOfTwo_MinusOne = (sizeOfDataEntries - 1) % 2 == 0;
        var lastIndexToIterateTo = sizeOfDataEntries;
        if (isFactorOfTwo_MinusOne)
            lastIndexToIterateTo -= 1;
        return lastIndexToIterateTo;
    }

    private void startUserInterface() {
        new Plotter("CubicSpinesPlotter", dataEntries, functionsPerSequence, pointsCorrespondingToSequence);
    }

}
