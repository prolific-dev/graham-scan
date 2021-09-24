package model;

import javafx.geometry.Point2D;

import java.util.*;
import java.util.stream.Collectors;

public class PreScan {
    Map<Point2D, Double> undefined;
    Map<Point2D, Double> inner;
    Stack<Map.Entry<Point2D, Double>> outer;
    Point2D minPoint;

    List<Map.Entry<Point2D, Double>> tmpEntriesToRemove;
    List<Map.Entry<Point2D, Double>> allEntriesToRemove;
    Iterator<Map.Entry<Point2D, Double>> iterator;

    public PreScan(Map<Point2D, Double> anglePointMap) {
        this.inner = new LinkedHashMap<>();
        this.outer = new Stack<>();
        this.undefined = sortByAngleAndPreFeedInner(anglePointMap);
    }

    private Map<Point2D, Double> sortByAngleAndPreFeedInner(Map<Point2D, Double> map) {
        tmpEntriesToRemove = new ArrayList<>();
        allEntriesToRemove = new ArrayList<>();
        iterator = map.entrySet().iterator();
        minPoint = iterator.next().getKey();

        while (iterator.hasNext()) {
            Map.Entry<Point2D, Double> current = iterator.next();
            // entriesToRemove is empty
            if (!tmpEntriesToRemove.isEmpty()) { // entriesToRemove is not empty
                double currentAngle = current.getValue();
                double entryAngle = tmpEntriesToRemove.get(0).getValue();
                // does current belong to double group in entriesToRemove, if yes:
                if (currentAngle != entryAngle) { // if no:
                    if (tmpEntriesToRemove.size() > 1) { // check entriesToRemove contains more than 1 elements
                        proceedTmpEntriesToRemove();
                    }
                    tmpEntriesToRemove.clear();
                }
            }
            tmpEntriesToRemove.add(current);
        }
        addRedundantToInner();

        return getCleanedUndefined(map);
    }

    private void proceedTmpEntriesToRemove() {
        Map.Entry<Point2D, Double> max = null; // max distance point of this group
        for (Map.Entry<Point2D, Double> entry : tmpEntriesToRemove) {
            if (max == null) { // set max distance point of group if not set yet
                max = entry;
            } else { // find out max distance point of group and add the rest to allEntriesToRemove
                double entryDistance = minPoint.distance(entry.getKey());
                double maxDistance = minPoint.distance(max.getKey());
                if (entryDistance > maxDistance) {
                    allEntriesToRemove.add(max);
                    max = entry;
                } else {
                    allEntriesToRemove.add(entry);
                }
            }
        }
    }
    
    private void addRedundantToInner() {
        for (Map.Entry<Point2D, Double> entry : this.allEntriesToRemove)
            inner.put(entry.getKey(), entry.getValue());
    }

    private Map<Point2D, Double> getCleanedUndefined(Map<Point2D, Double> map) {
        return map.entrySet()
                .stream()
                .filter(x -> !this.allEntriesToRemove.contains(x))
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    public Map<Point2D, Double> getUndefined() {
        return this.undefined;
    }

    public Map<Point2D, Double> getInner() {
        return this.inner;
    }

    public Stack<Map.Entry<Point2D, Double>> getOuter() {
        return this.outer;
    }

    public Point2D getMinPoint() {
        return this.minPoint;
    }
}
