package de.karlsruhe.hhs.GaussianElimination;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class GaussianEliminationMaintainer {

    private List<Point2D.Double> dataEntries = new LinkedList<>();

    public GaussianEliminationMaintainer(List<Point2D.Double> dataEntries) {
        this.dataEntries = dataEntries;
    }

    public void maker() {
        var gaussen = new GaussianElimination();
    }
}
