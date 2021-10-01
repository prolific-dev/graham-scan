package com.prolificdev.models;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NewConverterTest {

    @Test
    void shouldThrowFileNotFoundException() throws IOException {
        File file = new File("src/test/resources/com/prolificdev/data/imaginary_test_data.txt");

        Assertions.assertThrows(FileNotFoundException.class, () -> new NewConverter(file));

    }

    @Test
    void shouldConvertFileToDataSetTest() throws IOException {
        File file = new File("src/test/resources/com/prolificdev/data/valid_test_data.txt");
        NewConverter newConverter = new NewConverter(file);

        Point[] points = {
                new Point(1, 2),
                new Point(2, 1),
                new Point(2, 1),
                new Point(12, 2),
                new Point(2, 12),
                new Point(12, 12)
        };
        Set<Point> setToCompare = new HashSet<>(Arrays.asList(points));

        Assertions.assertEquals(setToCompare.size(), newConverter.getConvertedDataSet().size());

    }

    @Test
    void shouldAddInvalidDataToSet() throws IOException {
        File file = new File("src/test/resources/com/prolificdev/data/invalid_test_data.txt");
        NewConverter newConverter = new NewConverter(file);

        String[] invalidStrings = {
                "+, -",
                "a, b",
                "1. 2"
        };
        Set<String> setToCompare = new HashSet<>(Arrays.asList(invalidStrings));

        Assertions.assertEquals(setToCompare.size(), newConverter.getInvalidDataSet().size());
        Assertions.assertEquals(setToCompare, newConverter.getInvalidDataSet());
    }
}
