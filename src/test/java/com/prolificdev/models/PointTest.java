package com.prolificdev.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("PointTest")
public class PointTest {

    @Test
    void shouldGetSpecificCoordinates() {
        Point point = new Point(2, 1);

        // x-coordinate
        assertThat(point.getX()).isEqualTo(2.0);
        // y-coordinate
        assertThat(point.getY()).isEqualTo(1.0);
    }

    @Test
    void shouldCalcuateTheAngleBetweenTwoPoints() {
        Point point = new Point(2, 2);

        assertThat(point.angle(new Point(2, 4))).isEqualTo(0);
        assertThat(point.angle(new Point(0, 2))).isEqualTo(90);
        assertThat(point.angle(new Point(2, 0))).isEqualTo(180);
        assertThat(point.angle(new Point(4, 2))).isEqualTo(270);
    }

    @Test
    void shouldCalculateTheDistanceBetweenTwoPoints() {
        Point point = new Point(0, 0);

        assertThat(point.distance(new Point(1, 2))).isEqualTo(Math.sqrt(5));
        assertThat(new Point(1, 2).distance(point)).isEqualTo(Math.sqrt(5));
    }

    @Test
    void shouldBeEqual() {
        Point point = new Point(1, 2);

        // testing @Overwrite equals()
        assertThat(point).isEqualTo(new Point(1, 2));
        assertThat(point).isNotEqualTo(new Point(2, 1));

        // return false because not instanceOf Point
        Object randomObject = new double[]{1, 2};
        assertThat(point.equals(randomObject)).isFalse();
    }

    @Test
    void shouldBeHashCode() {
        Point point = new Point(1, 1);
        int hash = Double.valueOf(1).hashCode() * 31 + Double.valueOf(1).hashCode();

        // testing @Overwrite hashCode()
        assertThat(point.hashCode()).isEqualTo(hash);
    }
}
