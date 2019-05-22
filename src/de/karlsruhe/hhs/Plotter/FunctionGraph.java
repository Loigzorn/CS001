package de.karlsruhe.hhs.Plotter;

import java.awt.*;

public class FunctionGraph extends Panel {

    private final double CONSTANTMARGIN = 0.025;

    public void paint(Graphics graphics) {
        obtainWindowSize(graphics);
        graphics.setColor(Color.blue);
        drawFunction();
    }

    private void obtainWindowSize(Graphics graphics) {
        double windowWidth = this.getBounds().width;
        double windowHeight = this.getBounds().height;

        int halfWidth = (int) windowWidth/2;
        int halfHeight = (int) windowHeight/2;
        int marginWidth = (int) (windowWidth * CONSTANTMARGIN);
        int marginHeight = (int) (windowHeight * CONSTANTMARGIN);

        int toYAxis = (int)windowHeight - marginWidth;

        int toXAxis = (int) windowWidth - marginHeight;

        graphics.drawLine(halfWidth, toYAxis, halfWidth, marginWidth);
        graphics.drawString("Y", halfWidth - 20, marginWidth);

        graphics.drawLine(marginHeight, halfHeight, toXAxis, halfHeight);
        graphics.drawString("X", toXAxis, halfHeight - 20);

    }

    private void drawFunction() {

    }

}

