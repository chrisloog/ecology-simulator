package org.simulator.ecosimulator.model;

public class Grass {
    private boolean isEaten;

    public Grass() {
        this.isEaten = false;
    }

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean eaten) {
        this.isEaten = eaten;
    }
}
