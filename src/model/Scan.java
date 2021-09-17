package model;

import javafx.geometry.Point2D;

import java.util.*;
import java.util.stream.Collectors;


public class Scan {
    Map<Point2D, Double> undefined;
    Map<Point2D, Double> inner;
    Stack<Map.Entry<Point2D, Double>> outer;

    public Scan(Map<Point2D, Double> anglePointMap) {
        this.inner = new LinkedHashMap<>();
        this.outer = new Stack<>();
        this.undefined = sortByAngleAndPreFeedInner(anglePointMap);
    }
    
    private Map<Point2D, Double> sortByAngleAndPreFeedInner(Map<Point2D, Double> map) {
        Iterator<Map.Entry<Point2D, Double>> iterator = map.entrySet().iterator();
        List<Map.Entry<Point2D, Double>> entriesToRemove = new ArrayList<>();
        List<Map.Entry<Point2D, Double>> allEntriesToRemove = new ArrayList<>();
        Point2D minPoint = iterator.next().getKey();
        
        while (iterator.hasNext()) {
            Map.Entry<Point2D, Double> current = iterator.next();
            // entriesToRemove is empty
            if (entriesToRemove.isEmpty()) {
                entriesToRemove.add(current);
            } else { // entriesToRemove is not empty
                double currentAngle = current.getValue();
                double entryAngle = entriesToRemove.get(0).getValue();
                // does current belong to double group in entriesToRemove, if yes:
                if (currentAngle == entryAngle) {
                    entriesToRemove.add(current);
                } else { // if no:
                    if (entriesToRemove.size() > 1) { // check entriesToRemove contains more than 1 elements
                        Map.Entry<Point2D, Double> max = null; // max distance point of this group
                        for (Map.Entry<Point2D, Double> entry : entriesToRemove) {
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
                    entriesToRemove.clear();
                    entriesToRemove.add(current);
                }
            }
        }

        for (Map.Entry<Point2D, Double> entry : allEntriesToRemove) {
            inner.put(entry.getKey(), entry.getValue());
        }

        return map.entrySet()
                .stream()
                .filter(x -> !allEntriesToRemove.contains(x))
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    private Stack<Map.Entry<Point2D, Double>> graham() {
        return null;
    }

    /*
    private void addMapToStack(Map<Point2D, Double> anglePointMap) {
        Set<Map.Entry<Point2D, Double>> set = anglePointMap.entrySet();

        set.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        lst -> {
                            Collections.reverse(lst);
                            return lst.stream();
                        }))
                .forEach(entry -> this.undefined.push(entry));
    }
     */

    public Map<Point2D, Double> getUndefined() {
        return this.undefined;
    }

    public Map<Point2D, Double> getInner() {
        return this.inner;
    }

    public Stack<Map.Entry<Point2D, Double>> getOuter() {
        return this.outer;
    }


}
