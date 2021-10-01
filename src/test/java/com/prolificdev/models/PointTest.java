package com.prolificdev.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PointTest")
public class PointTest {
    private final Point point = new Point(2, 1);

    @Test
    void coordinatesAssertions() {
        Assertions.assertEquals(2.0, point.getX());
        Assertions.assertEquals(1.0, point.getY());
    }
}
