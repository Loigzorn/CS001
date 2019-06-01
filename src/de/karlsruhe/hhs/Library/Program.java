package de.karlsruhe.hhs.Library;

import de.karlsruhe.hhs.GaussianElimination.GaussianElimination;
import de.karlsruhe.hhs.Library.Helper.LGS;
import de.karlsruhe.hhs.Plotter.Plotter;
import de.karlsruhe.hhs.Reader.Reader;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

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
        for (int i = 0; i < lastIndexToIterateTo; i = i + 3) {
            var entries = getEntries(i);
            var lgs = new LGS().calculateLGS(entries);
            var elimination = new GaussianElimination(lgs.getValues(), lgs.getResults());
            var parameterOfFunction = elimination.primal();
            if(!elimination.isFeasible()) {
                //TODO find alternative if two of the three points have the same x-value.
                JOptionPane.showMessageDialog(null, (String.format(
                        "Could not find a function to cover the range between %s and %s",
                        entries.get(0).getX(), entries.get(2).getX()) +
                        "\nFor the points: " +
                        "(" + entries.get(0).getX() + "," + entries.get(0).getY()+ "); " +
                        "(" + entries.get(1).getX() + "," + entries.get(1).getY()+ "); " +
                        "(" + entries.get(2).getX() + "," + entries.get(2).getY()+ ")."));
                continue;
            }
            pointsCorrespondingToSequence.add(entries);
            var function = lgs.createFunction(parameterOfFunction);
            functionsPerSequence.add(function);
        }

        if (lastIndexToIterateTo != sizeOfDataEntries) {
            var entries = getEntries(sizeOfDataEntries-3);

            var lgs = new LGS().calculateLGS(entries);
            var elimination = new GaussianElimination(lgs.getValues(), lgs.getResults());
            if(!elimination.isFeasible()) {
                JOptionPane.showMessageDialog(null, (String.format(
                        "Could not find a function to cover the range between %s and %s",
                        entries.get(0).getX(), entries.get(2).getX()) +
                        "\nFor the points: " +
                        "(" + entries.get(0).getX() + "," + entries.get(0).getY()+ "); " +
                        "(" + entries.get(1).getX() + "," + entries.get(1).getY()+ "); " +
                        "(" + entries.get(2).getX() + "," + entries.get(2).getY()+ ")."));
                return;
            }
            var parameterOfFunction = elimination.primal();
            pointsCorrespondingToSequence.add(entries);
            var function = lgs.createFunction(parameterOfFunction);
            functionsPerSequence.add(function);
        }

    }

    private List<Function> differentiation(List<Point2D> threeEntries) {
        return null;
    }

    private void gaussianElimination(List<Function> functions) {
        //var gaussenElemination = new GaussianEliminationMaintainer();
    }

    private LinkedList<Point2D> getEntries(int from) {
        var to = from + 3;
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
        var isFactorOfThree_MinusOne = (sizeOfDataEntries - 1) % 3 == 0;
        var isFactorOfThree_MinusTwo = (sizeOfDataEntries - 2) % 3 == 0;
        var lastIndexToIterateTo = sizeOfDataEntries;
        if (isFactorOfThree_MinusOne) {
            lastIndexToIterateTo -= 1;
        } else if (isFactorOfThree_MinusTwo) {
            lastIndexToIterateTo -= 2;
        }
        return lastIndexToIterateTo;
    }

    private void startUserInterface() {
        new Plotter("CubicSpinesPlotter", dataEntries, functionsPerSequence, pointsCorrespondingToSequence);
    }

}
