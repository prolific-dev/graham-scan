package org.prolific.dev.util;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    List<Observer> subscribers = new ArrayList<>();

    public void add(Observer o) {
        subscribers.add(o);
    }

    public void remove(Observer o) {
        subscribers.remove(o);
    }

    public void notifyObservers() {
        for (Observer o : subscribers) {
            o.update();
        }
    }
}
