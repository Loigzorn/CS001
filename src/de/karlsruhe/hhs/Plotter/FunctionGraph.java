package de.karlsruhe.hhs.Plotter;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class FunctionGraph extends Panel implements MouseWheelListener , MouseListener, MouseMotionListener {

    private final double CONSTANTMARGIN = 0.025;
    private Point2D.Double center;
    private final int INDENT = 2;
    private List<Point2D.Double> dateEntries;
    private List<LinkedList<Double>> functionsPerSequence;
    private boolean areNewDataEntriesProvided;

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

    public FunctionGraph(boolean areNewDataEntriesProvided, List<Point2D.Double> dataEntries, List<LinkedList<Double>> functionsPerSequence) {
        this.areNewDataEntriesProvided = areNewDataEntriesProvided;
        this.dateEntries = dataEntries;
        this.functionsPerSequence = functionsPerSequence;
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
        drawPoints(graphics);
        drawFunction(graphics);
    }

    private void obtainWindowSize(Graphics graphics) {
        double windowWidth = this.getBounds().width;
        double windowHeight = this.getBounds().height;


        int halfWidth = (int) windowWidth/2;
        int halfHeight = (int) windowHeight/2;

        center = new Point2D.Double(halfWidth, halfHeight);

        int marginWidth = (int) (windowWidth * CONSTANTMARGIN);
        int marginHeight = (int) (windowHeight * CONSTANTMARGIN);

        int toYAxis = (int)windowHeight - marginWidth;

        int toXAxis = (int) windowWidth - marginHeight;

        graphics.drawLine(halfWidth, toYAxis, halfWidth, marginWidth);
        graphics.drawString("Y", halfWidth - 20, marginWidth);

        graphics.drawLine(marginHeight, halfHeight, toXAxis, halfHeight);
        graphics.drawString("X", toXAxis, halfHeight - 20);

    }

    private void drawPoints(Graphics graphics) {
        if(!areNewDataEntriesProvided) {
            return;
        }
        for (var point : dateEntries) {
            var roundedX = (int) Math.round(center.x + point.x) - (INDENT /2);
            var roundedY = (int) Math.round(center.y - point.y) - (INDENT/2);
            var xForText = roundedX - 5;
            var yForText = roundedY - 5;

            graphics.setColor(Color.BLUE);

            graphics.fillOval(roundedX, roundedY, INDENT, INDENT);
            var font = new Font("Default", Font.CENTER_BASELINE, (int)fontSize);
            graphics.setFont(font);
            var description = String.format("(%s, %s)", point.x, point.y);
            graphics.drawString(description, xForText, yForText);
        }
    }

    private void drawFunction(Graphics graphics) {
        if(!areNewDataEntriesProvided) {
            return;

        }
        //TODO
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

