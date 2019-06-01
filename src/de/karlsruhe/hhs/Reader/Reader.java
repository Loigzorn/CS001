package de.karlsruhe.hhs.Reader;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Reader {

    private String filepath;

    public Reader(String filepath) {
        this.filepath = filepath;
    }

    public List<String> readFile() {

        String line;
        String cvsSplitBy = ",";
        List<String> entries = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {

            while ((line = br.readLine()) != null) {

                String [] entriesOfLine = line.split(cvsSplitBy);
                Collections.addAll(entries, entriesOfLine);

            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getCause() + e.getMessage());
        }
        return entries;
    }

    public List<Point2D.Double> convert(List<String> entries) {
        List<Point2D.Double> convertedEntries = new LinkedList<>();
        for (var entry : entries) {
            var validationOfSyntax = entry.contains(";");
            if(!validationOfSyntax) {
                JOptionPane.showMessageDialog(null, "Skipped element: " + entry);
                //System.out.println( "Skipped element: " + entry);
                continue;
            }
            var coordinate = entry.split(";");
            var validationOfCoordinate = coordinate.length != 2;
            if(validationOfCoordinate) {
                JOptionPane.showMessageDialog(null, "Skipped element: " + entry);
                //System.out.println("Skipped element: " + entry);
                continue;
            }
            var firstPart = coordinate[0].substring(1);
            var secondPart = coordinate[1].substring(0,coordinate[1].length() - 1);
            var regExNumber = "-?\\d+";
            var validationFirstPart = !firstPart.matches(regExNumber);
            var validationSecondPart = !secondPart.matches(regExNumber);
            if(validationFirstPart || validationSecondPart) {
                JOptionPane.showMessageDialog(null, "Skipped element: " + entry);
                //System.out.println("Skipped element: " + entry);
                continue;
            }
            var x = Double.parseDouble(firstPart);
            var y = Double.parseDouble(secondPart);
            var point = new Point2D.Double(x,y);
            convertedEntries.add(point);
        }
        if (convertedEntries.size() != entries.size()) {
            JOptionPane.showMessageDialog(null, "Conversion partly failed!");
            //System.out.println((char)27 + "[31m" + "Conversion partly failed!" + (char)27 + "[0m");
        }
        return convertedEntries;
    }
}
