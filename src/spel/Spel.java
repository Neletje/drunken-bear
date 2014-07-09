/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spel;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 *
 * @author Ellen
 */
public class Spel implements Observable {

    private static final Spel spel = new Spel();

    public static Spel getInstance() {
        return spel;
    }

    private List<InvalidationListener> lijst;
    private Status status;

    public Spel() {
        lijst = new ArrayList<>();
        status = Status.START;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        lijst.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        lijst.remove(listener);
    }

    private void invalidate() {
        for (InvalidationListener l : lijst) {
            l.invalidated(spel);
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status != this.status) {
            this.status = status;
            invalidate();

        }
    }

}
