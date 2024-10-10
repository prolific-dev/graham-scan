package org.prolific.dev.model.math;

import org.prolific.dev.util.Observable;

import java.util.List;
import java.util.Set;

import static org.prolific.dev.model.math.GrahamScan.scan;

public class DataSet extends Observable {

    private Set<Point> points;
    private List<Point> convex_hull;

    public DataSet() {}

    public DataSet(Set<Point> points) {
        this.setData(points);
    }

    public void setData(Set<Point> points) {
        this.points = points;
        this.convex_hull = scan(this.points);
    }

    public Set<Point> getData() { return this.points; }
    public List<Point> getConvexHull() { return this.convex_hull; }
}
