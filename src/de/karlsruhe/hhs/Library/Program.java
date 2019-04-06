package de.karlsruhe.hhs.Library;

import de.karlsruhe.hhs.GaussianElimination.GaussianEliminationMaintainer;
import de.karlsruhe.hhs.Reader.Reader;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Program {

    private static String DATA_POINTS_PATH = "./src/Debug/Data/Measurements/Points.csv";
    private List<Point2D.Double> dataEntries = new LinkedList<>();

    public void startApplication() {
        readFile();
        //differentiation()
        gaussianElimination();
    }

    private void readFile() {
        Reader reader = new Reader();
        var entries = reader.readFile(DATA_POINTS_PATH);
        dataEntries = reader.convert(entries);
        //A::B refers to method B in class A.
        //in this case getX is a inherited method of Point2D from the class Point.
        dataEntries.sort(Comparator.comparing(Point2D::getX));
    }

    private void gaussianElimination() {
        var gaussenElemination = new GaussianEliminationMaintainer(dataEntries);
    }
}
