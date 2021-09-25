package model;

import javafx.geometry.Point2D;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Converter {
    private final Set<Point2D> input;

    public Converter(File file) throws IOException {
        this.input = convertData(file);
    }

    private Set<Point2D> convertData(File file) throws IOException {
        String currentString;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        Set<Point2D> dataSet = new HashSet<>();

        while ((currentString = bufferedReader.readLine()) != null) {
            if (currentString.matches("[1-9][0-9]*,\\s?[1-9][0-9]*")) {
                String[] split = currentString.replaceAll(" ", "").split(",");
                double x = Double.parseDouble(String.valueOf(split[0].toCharArray()));
                double y = Double.parseDouble(String.valueOf(split[1].toCharArray()));
                Point2D point2D = new Point2D(x, y);
                dataSet.add(point2D);
            } else {
                System.err.println("Data String '"
                        + currentString
                        + "' has wrong formatting."
                        + " Check if data is in the right regex structure"
                        + " ([1-9][0-9]*,\\s?[1-9][0-9]*)."
                        + " Correct and restart"
                        + " program to put data into list.");
            }
        }
        return dataSet;
    }

    public List<Point2D> getInput() {
        return new ArrayList<>(this.input);
    }
}
