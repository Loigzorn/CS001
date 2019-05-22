package de.karlsruhe.hhs.Library;

import de.karlsruhe.hhs.Plotter.Plotter;
import de.karlsruhe.hhs.Reader.Reader;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Program {

    private static String DATA_POINTS_PATH = "./src/Debug/Data/Measurements/Points.csv";
    private List<Point2D.Double> dataEntries = new LinkedList<>();
    private int sizeOfDataEntries;

    public void startApplication() {
        readFile();
        calculateFunctions();
        showResults();

    }

    private void calculateFunctions() {
        var lastIndexToIterateTo = determineLastIndexToIterateTo();
        for (int i = 0; i <= lastIndexToIterateTo; i = i + 3) {
            var entries = getEntries(i);
            var functions = differentiation(entries);
            gaussianElimination(functions);
        }
        if (lastIndexToIterateTo != sizeOfDataEntries) {
            var entries = getEntries(sizeOfDataEntries-3);
            var functions = differentiation(entries);
            gaussianElimination(functions);
        }

    }

    private List<Function> differentiation(List<Point2D> threeEntries) {
        return null;
    }

    private void readFile() {
        Reader reader = new Reader();
        var entries = reader.readFile(DATA_POINTS_PATH);
        dataEntries = reader.convert(entries);
        //A::B refers to method B in class A.
        //in this case getX is a inherited method of Point2D from the class Point.
        dataEntries.sort(Comparator.comparing(Point2D::getX));
        sizeOfDataEntries = dataEntries.size();
    }

    private void gaussianElimination(List<Function> functions) {
        //var gaussenElemination = new GaussianEliminationMaintainer();
    }

    private List<Point2D> getEntries(int from) {
        var to = from + 3;
        if (to > sizeOfDataEntries) {
            to = sizeOfDataEntries;
        }
        List<Point2D> entries = new LinkedList<>();
        for(int i = from; i < to; i ++) {
            entries.add(dataEntries.get(i));
        }
        return entries;
    }

    private int determineLastIndexToIterateTo() {
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

    private void showResults() {
        new Plotter("CubicSpinesPlotter");
    }

}
