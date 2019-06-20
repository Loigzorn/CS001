package de.karlsruhe.hhs.Calculation.GaussianElimination;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class GaussianEliminationMaintainer {

    private List<Point2D.Double> dataEntries = new LinkedList<>();
    private int sizeOfDataEntries;

    public GaussianEliminationMaintainer(List<Point2D.Double> dataEntries) {
        this.dataEntries = dataEntries;
        this.sizeOfDataEntries = dataEntries.size();

    }

    public void maker() {

        //var gaussen = new GaussianElimination();
    }


}
