package com.prolificdev.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Converter {
    private static final String VALID_REGEX_FORMAT = "[1-9][0-9]*,\\s?[1-9][0-9]*";
    private final Set<Point> convertedDataSet;
    private final Set<String> invalidDataSet;

    protected Converter(File file) throws IOException {
        this.invalidDataSet = new HashSet<>();
        this.convertedDataSet = convertFileToDataSet(file);
    }

    private Set<Point> convertFileToDataSet(File file) throws IOException {
        Set<Point> convertedDataSet = new LinkedHashSet<>();
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
