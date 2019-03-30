package de.karlsruhe.hhs.Reader;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Reader {

    public Reader() {
    }

    public List<String> readFile(String filepath) {

        String line;
        String cvsSplitBy = ",";
        List<String> entries = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {

            while ((line = br.readLine()) != null) {

                String [] entriesOfLine = line.split(cvsSplitBy);
                Collections.addAll(entries, entriesOfLine);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public List<Point2D> convert(List<String> entries) {
        List<Point2D> convertedEntries = new LinkedList<>();
        for (var entry : entries) {
            var validationOfSyntax = entry.contains(";");
            if(!validationOfSyntax) {
                System.out.println( "Skipped element: " + entry);
                continue;
            }
            var coordinate = entry.split(";");
            var validationOfCoordinate = coordinate.length != 2;
            if(validationOfCoordinate) {
                System.out.println( "Skipped element: " + entry);
                continue;
            }
            var firstPart = coordinate[0].substring(1);
            var secondPart = coordinate[1].substring(0,1);
            var x = Double.parseDouble(firstPart);
            var y = Double.parseDouble(secondPart);
            var point = new Point2D.Double(x,y);
            convertedEntries.add(point);
        }
        if (convertedEntries.size() != entries.size()) {
            System.out.println("Conversion partly failed!");
        }
        return convertedEntries;
    }

}
