package com.prolificdev.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PreScanTest {

    @Test
    void shouldThrowFileNotFoundException() {
        File file = new File("src/test/resources/com/prolificdev/data/imaginary_test_data.txt");

        assertThatExceptionOfType(IOException.class).isThrownBy(() -> new PreScan(file));
    }

    @Test
    void shouldGetSortedUndefinedPointMap() throws IOException {
        File file = new File("src/test/resources/com/prolificdev/data/prescan_test_data.txt");
        PreScan preScan = new PreScan(file);

        Map<Point, Double> comparisonMap = new LinkedHashMap<>();

        comparisonMap.put(new Point(1, 1), 0.0);
        // comparisonMap.put(new Point(3, 1), 90.0);
        // comparisonMap.put(new Point(6, 1), 90.0);
        comparisonMap.put(new Point(9, 1), 90.0);
        comparisonMap.put(new Point(2, 2), 135.0);
        comparisonMap.put(new Point(1, 4), 180.0);

        assertThat(preScan.getUndefinedPointMap())
                .isNotEmpty()
                .hasSize(4)
                .containsExactlyEntriesOf(comparisonMap);
    }

    @Test
    void shouldGetPreInnerPointList() throws IOException {
        File file = new File("src/test/resources/com/prolificdev/data/prescan_test_data.txt");
        PreScan preScan = new PreScan(file);

        List<Point> comparisonList = new ArrayList<>();

        comparisonList.add(new Point(3, 1));
        comparisonList.add(new Point(6, 1));

        assertThat(preScan.getInnerPointList())
                .isNotEmpty()
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(comparisonList);
    }
}
