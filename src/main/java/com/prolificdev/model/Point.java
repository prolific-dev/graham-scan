package com.prolificdev.model;

public class Point {
    private static final int THREESIXTY = 360;

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

    public double angle(Point point) {
        double theta, angle;
        theta = Math.atan2(this.getY() - point.getY(), this.getX() - point.getX());
        theta += Math.PI / 2.0;
        angle = Math.toDegrees(theta);
        if (angle < 0)
            angle += THREESIXTY;
        return angle;
    }

    public double distance(Point point) {
        return Math.sqrt(Math.pow(point.getX() - this.x, 2) + Math.pow(point.getY() - this.y, 2));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point)
            return this.x == ((Point) obj).getX() && this.y == ((Point) obj).getY();
        return false;
    }

    @Override
    public int hashCode() {
        return Double.valueOf(this.x).hashCode() * 31 + Double.valueOf(this.y).hashCode();
    }

    public int location(Point p1, Point p2) {
        double x1 = p2.getX() - p1.getX();
        double y1 = p2.getY() - p1.getY();
        double x2 = this.getX() - p1.getX();
        double y2 = this.getY() - p1.getY();

        double crossproduct = x1 * y2 - y1 * x2;

        if (crossproduct > 0) {
            return 1; // located right of vector
        } else if (crossproduct < 0) {
            return -1; // located left of vector
        } else {
            return 0; // located on top of vector
        }
    }
}
