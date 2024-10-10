package org.prolific.dev.model.math;

import java.util.Objects;

public class Point implements Comparable<Point> {
    public double x;
    public double y;
    public double angleP0;
    public double distanceP0;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.angleP0 = 0.0;
        this.distanceP0 = 0.0;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
        this.angleP0 = 0.0;
        this.distanceP0 = 0.0;
    }

    @Override
    public String toString() {
        return this.x + ", " + this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point p = (Point) o;
        return Double.compare(p.x, x) == 0 && Double.compare(p.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Point p) {
        int cmp = Double.compare(this.angleP0, p.angleP0);
        if (cmp != 0)
            return cmp;
        else
            return Double.compare(this.distanceP0, p.distanceP0);
    }
}
