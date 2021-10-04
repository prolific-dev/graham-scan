package com.prolificdev.model;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class GrahamScan {
    List<Point> innerPoints;
    Stack<Map.Entry<Point, Double>> undefinedPointStack;
    Stack<Map.Entry<Point, Double>> convexHullStack;

    public GrahamScan(File file) throws IOException {
        PreScan preScan = new PreScan(file);
        this.undefinedPointStack = pushPreScanDataToStack(preScan.getUndefinedPointMap());
        this.innerPoints = preScan.getInnerPointList();
        this.convexHullStack = new Stack<>();
        graham();
    }

    private void graham() {
        this.convexHullStack.push(this.undefinedPointStack.pop());

        while (!undefinedPointStack.isEmpty()) {
            if (undefinedPointStack.size() > 1) {
                Map.Entry<Point, Double> pointToCheck = undefinedPointStack.pop();
                Map.Entry<Point, Double> nextPoint = undefinedPointStack.peek();
                Map.Entry<Point, Double> lastPointFromHull = convexHullStack.peek();

                if (locatedOnConvexHull(pointToCheck, lastPointFromHull, nextPoint)) {
                    convexHullStack.push(pointToCheck);
                } else {
                    innerPoints.add(pointToCheck.getKey());
                    undefinedPointStack.push(convexHullStack.pop());
                }
            } else {
                Map.Entry<Point, Double> pointToCheck = undefinedPointStack.pop();
                Map.Entry<Point, Double> nextPoint = convexHullStack.lastElement();
                Map.Entry<Point, Double> lastPointFromHull = convexHullStack.peek();

                if (locatedOnConvexHull(pointToCheck, lastPointFromHull, nextPoint)) {
                    convexHullStack.push(pointToCheck);
                } else {
                    innerPoints.add(pointToCheck.getKey());
                }
            }
        }
    }

    private boolean locatedOnConvexHull(Map.Entry<Point, Double> pointToCheck, Map.Entry<Point, Double> lastPointFromHull, Map.Entry<Point, Double> nextPoint) {
        return pointToCheck.getKey().location(lastPointFromHull.getKey(), nextPoint.getKey()) <= 0;
    }


    private Stack<Map.Entry<Point, Double>> pushPreScanDataToStack(Map<Point, Double> undefinedPointMap) {
        Stack<Map.Entry<Point, Double>> stack = new Stack<>();
        undefinedPointMap.entrySet()
                .stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), lst -> {
                    Collections.reverse(lst);
                    return lst.stream();
                }))
                .forEach(stack::push);
        return stack;
    }

    public Stack<Map.Entry<Point, Double>> getUndefinedPointStack() {
        return this.undefinedPointStack;
    }

    public List<Point> getConvexHull() {
        return this.convexHullStack
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Point> getInnerPoints() {
        return innerPoints;
    }
}
