package com.prolificdev.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChartControllerTest {

    @Test
    void shouldNotBeInitializedOnFirstAppear() {
        ChartController chartController = new ChartController();

        assertThat(chartController.getFile()).isNull();
        assertThat(chartController.getInnerSeries().getName()).isNull();
        assertThat(chartController.getInnerSeries().getData()).isEmpty();
        assertThat(chartController.getConvexHullSeries().getName()).isNull();
        assertThat(chartController.getConvexHullSeries().getData()).isEmpty();
        assertThat(chartController.getLineChart()).isNull();
    }
}
