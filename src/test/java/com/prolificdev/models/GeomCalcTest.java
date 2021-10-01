package com.prolificdev.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeomCalcTest {

    @Test
    public void shouldDoubleUpItself() {
        GeomCalc geomCalc = new GeomCalc();

        Assertions.assertEquals(2, geomCalc.testMethod(1));
    }
}
