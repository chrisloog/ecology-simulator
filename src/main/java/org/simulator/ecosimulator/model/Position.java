package org.simulator.ecosimulator.model;

public class Position {
    public int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("Position X is %s and Position Y is %s", x, y);
    }
}

