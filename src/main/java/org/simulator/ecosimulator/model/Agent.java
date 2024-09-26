package org.simulator.ecosimulator.model;

import java.util.function.Predicate;

public abstract class Agent {
    public Position position;
    protected Environment env;
    protected int foodPoints;

    public Agent(Position position, Environment env) {
        this.position = position;
        this.env = env;
    }

    public abstract void update();

    protected void moveTowards(Position target) {
        int dx = Integer.compare(target.x, this.position.x);
        int dy = Integer.compare(target.y, this.position.y);

        this.position.x += dx;
        this.position.y += dy;
    }

    protected Position findNearestTarget(int searchRange, Predicate<Position> isTarget) {
        Position nearest = this.position;
        int minDistance = Integer.MAX_VALUE;

        for (int dx = -searchRange; dx <= searchRange; dx++) {
            for (int dy = -searchRange; dy <= searchRange; dy++) {
                int newX = this.position.x + dx;
                int newY = this.position.y + dy;
                Position newPos = new Position(newX, newY);

                if (!env.isWithinBounds(newX, newY) || !isTarget.test(newPos)) {
                    continue;
                }

                int distance = Math.abs(dx) + Math.abs(dy);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = newPos;
                }
            }
        }
        return nearest;
    }

    public Position getPosition() { return position; }

}

