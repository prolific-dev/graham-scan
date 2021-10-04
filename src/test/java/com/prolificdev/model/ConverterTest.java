package com.prolificdev.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ConverterTest {

    @Test
    void shouldThrowFileNotFoundException() {
        File file = new File("src/test/resources/com/prolificdev/data/imaginary_test_data.txt");

        assertThatExceptionOfType(IOException.class).isThrownBy(() -> new Converter(file));
    }

    @Test
    void shouldConvertFileToDataSetTest() throws IOException {
        File file = new File("src/test/resources/com/prolificdev/data/valid_test_data.txt");
        Converter converter = new Converter(file);

        assertThat(converter.getConvertedDataSet())
                .isNotEmpty()
                .hasSize(5)
                .contains(
                        new Point(1, 2),
                        new Point(2, 1),
                        new Point(12, 2),
                        new Point(2, 12),
                        new Point(12, 12));
    }

    @Test
    void shouldAddInvalidDataToSet() throws IOException {
        File file = new File("src/test/resources/com/prolificdev/data/invalid_test_data.txt");
        Converter converter = new Converter(file);

        assertThat(converter.getInvalidDataSet())
                .isNotEmpty()
                .hasSize(3)
                .contains("+, -", "a, b", "1. 2");
    }
}
