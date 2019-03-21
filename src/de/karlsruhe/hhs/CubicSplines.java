package de.karlsruhe.hhs;

import de.karlsruhe.hhs.Reader.Reader;

public class CubicSplines {

    private static String FILEPATH = "C:/Users/Lukas/IdeaProjects/CS001/src/Debug/Data/Measurements.Points.csv";

    public static void main(String[] args) {
        Reader reader = new Reader(FILEPATH);
        reader.readFile();
    }
}
