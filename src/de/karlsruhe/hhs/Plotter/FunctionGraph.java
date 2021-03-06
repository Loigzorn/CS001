package de.karlsruhe.hhs.Plotter;

import de.karlsruhe.hhs.Calculation.Calculations;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class FunctionGraph extends Panel implements MouseWheelListener , MouseListener, MouseMotionListener {

    private final double CONSTANTMARGIN = 0.025;
    private Point2D.Double center;
    private final int INDENTFORFUNCTION = 1;
    private final int INDENTFORPOINTS = 2;
    private List<Point2D.Double> dataEntries;
    private List<LinkedList<Double>> functionsPerSequence;
    private List<LinkedList<Point2D>> pointsCorrespondingToSequence;

    //Zoom
    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zoomer;
    private boolean dragger;
    private boolean released;
    private double xOffset = 0;
    private double yOffset = 0;
    private int xDiff;
    private int yDiff;
    private Point startPoint;
    private double fontSize = 17;

    public FunctionGraph(List<Point2D.Double> dataEntries, List<LinkedList<Double>> functionsPerSequence, List<LinkedList<Point2D>> pointsCorrespondingToSequence) {
        this.dataEntries = dataEntries;
        this.functionsPerSequence = functionsPerSequence;
        this.pointsCorrespondingToSequence = pointsCorrespondingToSequence;
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        if (zoomer) {
            updateZoom(graphics);
        }
        if (dragger) {
            updateDragger(graphics);
        }

        obtainWindowSize(graphics);
        graphics.setColor(Color.blue);
        drawFunction(graphics);
        drawPoints(graphics);
    }

    private void obtainWindowSize(Graphics graphics) {
        var windowFactor = 100000;
        double windowWidth = this.getBounds().width;
        double windowHeight = this.getBounds().height;


        int halfWidth = (int) windowWidth/2;
        int halfHeight = (int) windowHeight/2;

        center = new Point2D.Double(halfWidth, halfHeight);

        int marginWidth = (int) (windowWidth * CONSTANTMARGIN) - windowFactor;
        int marginHeight = (int) (windowHeight * CONSTANTMARGIN) - windowFactor;

        int toYAxis = (int)(windowHeight - marginWidth) + windowFactor;

        int toXAxis = (int) (windowWidth - marginHeight) + windowFactor;

        graphics.drawLine(halfWidth, toYAxis, halfWidth, marginWidth);
        graphics.drawString("Y", halfWidth - 20, marginWidth);

        graphics.drawLine(marginHeight, halfHeight, toXAxis, halfHeight);
        graphics.drawString("X", toXAxis, halfHeight - 20);

    }

    private void drawPoints(Graphics graphics) {
        for (var point : dataEntries) {
            var roundedX = (int) (Math.round(center.x + point.x * 100) - (INDENTFORPOINTS /2));
            var roundedY = (int) (Math.round(center.y - point.y * 100) - (INDENTFORPOINTS /2));
            var scaledX = roundedX;
            var scaledY = roundedY;
            var xForText = scaledX - 2;
            var yForText = scaledY - 2;

            graphics.setColor(Color.BLUE);
            graphics.fillOval(scaledX , scaledY, INDENTFORPOINTS, INDENTFORPOINTS);
            graphics.setColor((Color.WHITE));
            var font = new Font("Default", Font.CENTER_BASELINE, (int)fontSize);
            graphics.setFont(font);
            var description = String.format("(%s, %s)", point.x, point.y);
            graphics.drawString(description, xForText, yForText);
        }
    }

    private void drawFunction(Graphics graphics) {
        graphics.setColor(Color.RED);
        if (functionsPerSequence.size() != pointsCorrespondingToSequence.size()) {
            System.out.println("Mistake");
        }
        for (var sequence : functionsPerSequence) {
            var indexer = functionsPerSequence.indexOf(sequence);
            var from = pointsCorrespondingToSequence.get(indexer).get(0).getX();
            var to = pointsCorrespondingToSequence.get(indexer).get(1).getX();
            var calc = new Calculations();
            for( var x = from; x <= to; x+= 0.01) {
                var yToPaint = (int)((center.y - (calc.yValue(sequence, x) * 100)));
                var bla = calc.yValue(sequence, x);
                var xToPaint = (int)(center.x + (x * 100));
                graphics.drawOval(xToPaint, yToPaint, INDENTFORFUNCTION,INDENTFORFUNCTION);
            }
        }
    }

    private void updateZoom(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;

        AffineTransform at = new AffineTransform();

        double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
        double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

        double zoomDiv = zoomFactor / prevZoomFactor;

        xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
        yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

        at.translate(xOffset, yOffset);
        at.scale(zoomFactor, zoomFactor);
        prevZoomFactor = zoomFactor;
        g2.transform(at);
        zoomer = false;
    }

    private void updateDragger(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;
        AffineTransform at = new AffineTransform();
        at.translate(xOffset + xDiff, yOffset + yDiff);
        at.scale(zoomFactor, zoomFactor);
        g2.transform(at);

        if (released) {
            xOffset += xDiff;
            yOffset += yDiff;
            dragger = false;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        zoomer = true;
        //Zoom in
        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
            fontSize /= 1.1;
            repaint();
        }
        //Zoom out
        if (e.getWheelRotation() > 0) {
            zoomFactor /= 1.1;
            fontSize *= 1.1;
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point curPoint = e.getLocationOnScreen();
        xDiff = curPoint.x - startPoint.x;
        yDiff = curPoint.y - startPoint.y;

        dragger = true;
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        released = false;
        startPoint = MouseInfo.getPointerInfo().getLocation();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        released = true;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}