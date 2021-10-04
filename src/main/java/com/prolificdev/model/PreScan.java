package com.prolificdev.model;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PreScan {
    Map<Point, Double> undefinedPointMap;
    List<Point> innerPointList;

    public PreScan(File file) throws IOException {
        this.undefinedPointMap = sortPointMapByAngle(new PreCalculation(file).getPointAngleMap());
        this.innerPointList = preFeedInnerPointMap(this.undefinedPointMap);
        this.undefinedPointMap = removeInnerPointsFromUndefined();
    }

    private Map<Point, Double> removeInnerPointsFromUndefined() {
        return this.undefinedPointMap
                .entrySet()
                .stream()
                .filter(x -> !this.innerPointList.contains(x.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    private List<Point> preFeedInnerPointMap(Map<Point, Double> pointAngleMap) {
        List<Point> finalEntriesToRemove = new ArrayList<>();
        List<Map.Entry<Point, Double>> tmpEntriesToRemove = new ArrayList<>();
        Iterator<Map.Entry<Point, Double>> iterator = pointAngleMap.entrySet().iterator();
        Point startingPoint = iterator.next().getKey();

        while (iterator.hasNext()) {
            Map.Entry<Point, Double> currentPoint = iterator.next();
            double currentAngle = currentPoint.getValue();

            if (!tmpEntriesToRemove.isEmpty()) {
                double entryGroupAngle = getCurrentEntryGroupAngle(tmpEntriesToRemove);

                if (currentAngle != entryGroupAngle) {
                    if (tmpEntriesToRemove.size() > 1) {
                        finalEntriesToRemove = sortOutTmpEntriesToFinalEntries(
                                startingPoint, finalEntriesToRemove, tmpEntriesToRemove);
                    }
                    tmpEntriesToRemove.clear();
                }
            }
            tmpEntriesToRemove.add(currentPoint);
        }
        return finalEntriesToRemove;
    }

    private List<Point> sortOutTmpEntriesToFinalEntries(Point startingPoint, List<Point> finalEntriesToRemove,
                                                        List<Map.Entry<Point, Double>> tmpEntriesToRemove) {

        Map.Entry<Point, Double> max = null;

        for (Map.Entry<Point, Double> entry : tmpEntriesToRemove) {
            if (max == null) {
                max = entry;
            } else {
                double entryDistance = entry.getKey().distance(startingPoint);
                double maxDistance = max.getKey().distance(startingPoint);
                if (entryDistance > maxDistance) {
                    finalEntriesToRemove.add(max.getKey());
                    max = entry;
                } else {
                    finalEntriesToRemove.add(entry.getKey());
                }
            }
        }
        return finalEntriesToRemove;
    }

    private double getCurrentEntryGroupAngle(List<Map.Entry<Point, Double>> tmpEntriesToRemove) {
        return tmpEntriesToRemove.get(0).getValue();
    }

    private Map<Point, Double> sortPointMapByAngle(Map<Point, Double> pointAngleMap) {
        return pointAngleMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<Point, Double> getUndefinedPointMap() {
        return this.undefinedPointMap;
    }

    public List<Point> getInnerPointList() {
        return this.innerPointList;
    }
}
