package de.karlsruhe.hhs.Plotter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Plotter extends JFrame{

    private int width = 700;
    private int height = 700;
    private Panel graphPanel;

    public Plotter(String title){
        super(title);
        arrangeLayout();
        obtainMainFrame();
    }

    private void obtainMainFrame() {
        setSize(width, height);
        setLocation(5,5);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    }

    private void arrangeLayout() {
        var mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout(2,1));
        createMainPanel();
        crateBottomPanel();
    }

    private void crateBottomPanel() {
        var  bottomPanel = new JPanel();
        var mainContainer = this.getContentPane();
        bottomPanel.setBorder(new LineBorder(Color.CYAN, 1));
        bottomPanel.setLayout(new FlowLayout(0));
        var button0 = new JButton("Button0");
        var button1 = new JButton("Button1");
        var button2 = new JButton("Button2");
        var button3 = new JButton("Button3");
        bottomPanel.add(button0);
        bottomPanel.add(button1);
        bottomPanel.add(button2);
        bottomPanel.add(button3);
        mainContainer.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void createMainPanel() {
        var mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(2,1,1));
        mainPanel.setBorder(new LineBorder(Color.CYAN, 1));
        mainPanel.setBackground(Color.LIGHT_GRAY);
        var plotterWidth = (int) (width * 0.85);
        var plotterHeight = (int) (height * 0.85);
        var mainDimension = new Dimension(plotterWidth, plotterHeight);
        graphPanel = new FunctionGraph();
        mainPanel.add(graphPanel).setPreferredSize(mainDimension);
        var optionMenu = createOptionMenu();
        mainPanel.add(optionMenu);
        this.add(mainPanel, BorderLayout.WEST);
    }

    private JPanel createOptionMenu() {
        var optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(6,1,1,1));
        optionPanel.setBorder(new LineBorder(Color.CYAN, 1));
        var button4 = new JButton("Button4");
        var button5 = new JButton("Button5");
        var button6 = new JButton("Button6");
        var button7 = new JButton("Button7");
        var button8 = new JButton("Button8");
        var button9 = new JButton("Button9");
        optionPanel.add(button4);
        optionPanel.add(button5);
        optionPanel.add(button6);
        optionPanel.add(button7);
        optionPanel.add(button8);
        optionPanel.add(button9);
        return optionPanel;
    }
}

