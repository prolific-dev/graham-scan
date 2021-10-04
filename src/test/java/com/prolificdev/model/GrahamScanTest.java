package com.prolificdev.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class GrahamScanTest {

    @Test
    void shouldThrowFileNotFoundException() {
        File file = new File("src/test/resources/com/prolificdev/data/imaginary_test_data.txt");

        assertThatExceptionOfType(IOException.class).isThrownBy(() -> new PreCalculation(file));
    }

    @Test
    void shouldGetConvexHullList() throws IOException {
        File file = new File("src/test/resources/com/prolificdev/data/grahamscan_test_data.txt");
        GrahamScan grahamScan = new GrahamScan(file);


        List<Point> comparisonList = new ArrayList<>();

        comparisonList.add(new Point(1, 1));
        comparisonList.add(new Point(5, 0));
        comparisonList.add(new Point(6, 3));
        comparisonList.add(new Point(4, 6));
        comparisonList.add(new Point(3, 6));
        comparisonList.add(new Point(2, 6));

        assertThat(grahamScan.getUndefinedPointStack()).isEmpty();

        assertThat(grahamScan.getConvexHull())
                .isNotEmpty()
                .hasSize(6)
                .containsExactlyInAnyOrderElementsOf(comparisonList);
    }

    @Test
    void shouldGetInnerPointsList() throws IOException {
        File file = new File("src/test/resources/com/prolificdev/data/grahamscan_test_data.txt");
        GrahamScan grahamScan = new GrahamScan(file);

        List<Point> comparisonList = new ArrayList<>();

        comparisonList.add(new Point(3, 4));
        comparisonList.add(new Point(4, 3));
        comparisonList.add(new Point(3, 2));

        assertThat(grahamScan.getInnerPoints())
                .isNotEmpty()
                .hasSize(3)
                .containsExactlyInAnyOrderElementsOf(comparisonList);
    }
}
