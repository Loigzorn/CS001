package de.karlsruhe.hhs;

import de.karlsruhe.hhs.Reader.Reader;

public class CubicSplines {

    private static String FILEPATH = "./src/Debug/Data/Measurements/Points.csv";

    public static void main(String[] args) {
        Reader reader = new Reader();
        var entries = reader.readFile(FILEPATH);
        var convertedEntries = reader.convert(entries);
        System.out.println(convertedEntries);
    }
}
