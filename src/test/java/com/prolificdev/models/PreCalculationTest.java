package com.prolificdev.models;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PreCalculationTest {

    @Test
    void shouldThrowFileNotFoundException() {
        File file = new File("src/test/resources/com/prolificdev/data/imaginary_test_data.txt");

        assertThatExceptionOfType(IOException.class).isThrownBy(() -> new PreCalculation(file));
    }

    @Test
    void shouldGetStartingPoint() throws IOException {
        File file = new File("src/test/resources/com/prolificdev/data/precalc_test_data.txt");
        PreCalculation preCalculation = new PreCalculation(file);

        assertThat(preCalculation.getStartingPoint()).isEqualTo(new Point(1, 1));
    }

    @Test
    void shouldGetPointAngleToStartingPointMap() throws IOException {
        File file = new File("src/test/resources/com/prolificdev/data/precalc_test_data.txt");
        PreCalculation preCalculation = new PreCalculation(file);

        Map<Point, Double> comparisonMap = new HashMap<>();

        comparisonMap.put(new Point(1, 1), 0.0);
        comparisonMap.put(new Point(1, 4), 180.0);
        comparisonMap.put(new Point(3, 1), 90.0);
        comparisonMap.put(new Point(2, 2), 135.0);

        assertThat(preCalculation.getPointAngleMap())
                .isNotEmpty()
                .hasSize(4)
                .containsExactlyInAnyOrderEntriesOf(comparisonMap);

    }
}
