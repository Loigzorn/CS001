package de.karlsruhe.hhs.Plotter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Plotter extends JFrame{

    private int width = 700;
    private int height = 700;
    private List<Point2D.Double> dateEntries;
    private List<LinkedList<Double>> functionsPerSequence;
    List<LinkedList<Point2D>> pointsCorrespondingToSequence;

    private final JFileChooser openFileChooser;

    private JButton loadDatePointsButton;
    private JLabel messageLabelLoadPoints;
    private JButton button0;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;

    public Plotter(String title, List<Point2D.Double> dataEntries, List<LinkedList<Double>> functionsPerSequence, List<LinkedList<Point2D>> pointsCorrespondingToSequence){
        super(title);
        this.dateEntries = dataEntries;
        this.functionsPerSequence = functionsPerSequence;
        this.pointsCorrespondingToSequence = pointsCorrespondingToSequence;
        openFileChooser = new JFileChooser();
        openFileChooser.setCurrentDirectory(new File("C//temp"));
        openFileChooser.setFileFilter(new FileNameExtensionFilter("Comma Separated Values", "csv"));

        obtainMainFrame();
        arrangeLayout();
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
        //crateBottomPanel();
    }

    private void crateBottomPanel() {
        var  bottomPanel = new JPanel();
        var mainContainer = this.getContentPane();
        bottomPanel.setBorder(new LineBorder(Color.CYAN, 1));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        button0 = new JButton("Button0");
        button1 = new JButton("Button1");
        button2 = new JButton("Button2");
        button3 = new JButton("Button3");
        bottomPanel.add(button0);
        bottomPanel.add(button1);
        bottomPanel.add(button2);
        bottomPanel.add(button3);
        mainContainer.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void createMainPanel() {
        var mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,1,1));
        mainPanel.setBorder(new LineBorder(Color.CYAN, 1));
        mainPanel.setBackground(Color.LIGHT_GRAY);
        var plotterWidth = (int) (width * 0.85);
        var plotterHeight = (int) (height * 0.85);
        var mainDimension = new Dimension(plotterWidth, plotterHeight);
        FunctionGraph graphPanel = new FunctionGraph(dateEntries, functionsPerSequence, pointsCorrespondingToSequence);
        mainPanel.add(graphPanel).setPreferredSize(mainDimension);
        var optionMenu = createOptionMenu();
        mainPanel.add(optionMenu);
        this.add(mainPanel, BorderLayout.WEST);
    }

    private JPanel createOptionMenu() {
        var optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(6,1,20,20));
        optionPanel.setBorder(new LineBorder(Color.CYAN, 1));
        optionPanel.setBackground(Color.LIGHT_GRAY);
        loadDatePointsButton = new JButton("Open file...");
        loadDatePointsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                loadDatePointsButtonActionPerformed(event);
            }
        });
        messageLabelLoadPoints = new JLabel();
//        button4 = new JButton("Button4");
//        button5 = new JButton("Button5");
//        button6 = new JButton("Button6");
//        button7 = new JButton("Button7");
//        button8 = new JButton("Button8");
//        button9 = new JButton("Button9");
        optionPanel.add(messageLabelLoadPoints);
        optionPanel.add(loadDatePointsButton);
//        optionPanel.add(button4);
//        optionPanel.add(button5);
//        optionPanel.add(button6);
//        optionPanel.add(button7);
//        optionPanel.add(button8);
//        optionPanel.add(button9);
        return optionPanel;
    }

    private void loadDatePointsButtonActionPerformed(java.awt.event.ActionEvent event) {
        var returnedValue = openFileChooser.showOpenDialog(this);
        if (returnedValue == JFileChooser.APPROVE_OPTION) {
            openFileChooser.getCurrentDirectory().getPath();
            messageLabelLoadPoints.setText("File successfully loaded!");
        } else {
            messageLabelLoadPoints.setText("No File chosen!");
        }
    }
}

