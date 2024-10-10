package org.prolific.dev.model.math;

import java.util.*;

public class GrahamScan {
    private GrahamScan(){}
    private static final double SHIFT_DEGREE = 90.0;
    private static final int MIN_POINTS = 3;

    private static List<Point> removeCollinear(List<Point> sorted) {
        List<Point> filtered = new ArrayList<>();
        Point curr;
        Point next;

        filtered.add(sorted.get(0));

        for (int i = 1; i < sorted.size() - 1; i++) {
            curr = sorted.get(i);
            next = sorted.get(i + 1);

            if (curr.angleP0 != next.angleP0)
                filtered.add(curr);
        }

        filtered.add(sorted.get(sorted.size() - 1));

        return filtered;
    }

    private static List<Point> sort(Set<Point> points) {
        Point p0 = null;
        List<Point> sorted = new ArrayList<>(points);

        for (Point p : points) {

            if (p0 == null) {
                p0 = p;
                continue;
            }

            if (p0.y < p.y)
                continue;

            if (p0.y == p.y && p0.x < p.x)
                continue;

            p0 = p;
        }

        for (Point p : sorted) {
            p.angleP0 = Math.atan2(p.y - p0.y, p.x - p0.x);
            p.angleP0 = Math.toDegrees(p.angleP0) + SHIFT_DEGREE;
            p.distanceP0 = Math.sqrt(Math.pow(p.x - p0.x, 2) + Math.pow(p.y - p0.y, 2));
        }

        Collections.sort(sorted);

        return removeCollinear(sorted);
    }

    private static boolean pointIsLeft(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x) > 0;
    }

    protected static List<Point> scan(Set<Point> points) {
        Stack<Point> hull = new Stack<>();
        List<Point> sorted = sort(points);
        int n = sorted.size();
        int i;

        if (n < MIN_POINTS)
            return null;

        hull.push(sorted.get(0));
        hull.push(sorted.get(1));

        i = 2;

        while (i < n) {
            Point si;
            Point pt1;
            Point pt2;

            si = sorted.get(i);

            if (hull.size() == 2)
            {
                hull.push(si);
                i++;
                continue;
            }

            pt1 = hull.peek();
            pt2 = hull.get(hull.size() - 2);

            if (pointIsLeft(pt2, pt1, si))
            {
                hull.push(si);
                i++;
                continue;
            }

            hull.pop();
        }

        return new ArrayList<>(hull);
    }
}
