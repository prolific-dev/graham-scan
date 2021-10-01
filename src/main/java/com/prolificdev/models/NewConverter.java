package com.prolificdev.models;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class NewConverter {
    private static final String VALID_REGEX_FORMAT = "[1-9][0-9]*,\\s?[1-9][0-9]*";
    private final Set<Point> convertedDataSet;
    private final Set<String> invalidDataSet;

    protected NewConverter(File file) throws IOException {
        this.invalidDataSet = new HashSet<>();
        this.convertedDataSet = convertFileToDataSet(file);
    }

    private Set<Point> convertFileToDataSet(File file) throws IOException {
        Set<Point> convertedDataSet = new HashSet<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String singleDataString;

        while ((singleDataString = bufferedReader.readLine()) != null) {
            if (singleDataString.matches(VALID_REGEX_FORMAT)) {
                convertedDataSet.add(formPointOutOfString(singleDataString));
            } else {
                this.invalidDataSet.add(singleDataString);
            }
        }
        return convertedDataSet;
    }

    private Point formPointOutOfString(String singleDataString) {
        String[] splittedDataString = singleDataString.replaceAll(" ", "").split(",");
        double x = Double.parseDouble(String.valueOf(splittedDataString[0].toCharArray()));
        double y = Double.parseDouble(String.valueOf(splittedDataString[1].toCharArray()));
        return new Point(x, y);
    }

    public Set<Point> getConvertedDataSet() {
        return this.convertedDataSet;
    }

    public Set<String> getInvalidDataSet() {
        return this.invalidDataSet;
    }
}
