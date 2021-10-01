package com.prolificdev.models;

public class Point {
    private final double x;
    private final double y;

    protected Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected double getX() {
        return x;
    }

    protected double getY() {
        return y;
    }
}
