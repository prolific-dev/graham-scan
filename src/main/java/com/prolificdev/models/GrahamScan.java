package com.prolificdev.models;

import javafx.geometry.Point2D;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;


public class GrahamScan {
    Map<Point2D, Double> inner;
    Stack<Map.Entry<Point2D, Double>> undefined;
    Stack<Map.Entry<Point2D, Double>> outer;
    Point2D minPoint;

    public GrahamScan(Map<Point2D, Double> anglePointMap) {
        PreScan preScan = new PreScan(anglePointMap);
        this.undefined = undefinedMapToStack(preScan.getUndefined());
        this.outer = new Stack<>();
        this.inner = preScan.getInner();
        this.minPoint = preScan.getMinPoint();
        graham();
    }

    private void graham() {
        // Minpoint to Hull
        outer.push(undefined.pop());

        // while undefined is not empty
        while (!undefined.isEmpty()) {
            if (undefined.size() > 1) {
                // take the first point and check wether it is on the right or on the left side of a segmant,
                // which connects the next point with the last point from the "hull"
                Map.Entry<Point2D, Double> pointToCheck = undefined.pop();
                Map.Entry<Point2D, Double> nextPoint = undefined.peek();
                Map.Entry<Point2D, Double> lastPointFromHull = outer.peek();

                if (direction(lastPointFromHull.getKey(), nextPoint.getKey(), pointToCheck.getKey()) <= 0) {
                    outer.push(pointToCheck);
                } else {
                    inner.put(pointToCheck.getKey(), pointToCheck.getValue());
                    undefined.push(outer.pop());
                }
            } else {
                // take the first point and check wether it is on the right or on the left side of a segmant,
                // which connects the next point with the last point from the "hull"
                Map.Entry<Point2D, Double> pointToCheck = undefined.pop();
                Map.Entry<Point2D, Double> nextPoint = outer.lastElement();
                Map.Entry<Point2D, Double> lastPointFromHull = outer.peek();

                if (direction(lastPointFromHull.getKey(), nextPoint.getKey(), pointToCheck.getKey()) <= 0) {
                    outer.push(pointToCheck);
                } else {
                    inner.put(pointToCheck.getKey(), pointToCheck.getValue());
                }
            }
        }
    }

    public int direction(Point2D a, Point2D b, Point2D c) {
        double bx = b.getX() - a.getX();
        double by = b.getY() - a.getY();
        double cx = c.getX() - a.getX();
        double cy = c.getY() - a.getY();

        double crossproduct = bx * cy - by * cx;

        if (crossproduct > 0) {
            return 1; // right
        } else if (crossproduct < 0) {
            return -1; // left
        } else {
            return 0; // on line
        }
    }


    private Stack<Map.Entry<Point2D, Double>> undefinedMapToStack(Map<Point2D, Double> undefinedMap) {
        Set<Map.Entry<Point2D, Double>> set = undefinedMap.entrySet();
        Stack<Map.Entry<Point2D, Double>> stack = new Stack<>();

        set.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        lst -> {
                            Collections.reverse(lst);
                            return lst.stream();
                        }))
                .forEach(stack::push);

        return stack;
    }


    public Stack<Map.Entry<Point2D, Double>> getUndefined() {
        return this.undefined;
    }


    public Map<Point2D, Double> getInner() {
        return this.inner;
    }

    public Stack<Map.Entry<Point2D, Double>> getOuter() {
        return this.outer;
    }


}
