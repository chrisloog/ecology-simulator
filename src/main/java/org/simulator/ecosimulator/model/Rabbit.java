package org.simulator.ecosimulator.model;

public class Rabbit extends Agent {

    private static final int SEARCH_RANGE = 3;

    public Rabbit(Position position, Environment env) {
        super(position, env);
        stamina = 10;
        maxFoodPoints = 8;
    }

    @Override
    public void update() {
        if (stamina <= 0) {
            rest();
        } else {
            move();
            if (needsFood()) {
                eat();
            }
            reproduce();
        }
    }

    private void move() {
        Position target;

        if (needsFood()) {
            target = findNearestTarget(SEARCH_RANGE, this::isFoodAt);
        } else {
            target = env.getRandomPosition();
        }

        moveTowards(target);

        stamina--;
        foodPoints -= 0.25F;
    }

    private void eat() {
        env.grassGrid[this.position.x][this.position.y].setEaten(true);
        foodPoints++;
    }

    private void reproduce() {
        if (foodPoints >= 5) {
            env.addAgent(new Rabbit(
                    new Position(this.position.x + 1, this.position.y), env)
            );

            foodPoints = 0;
        }
    }

    private boolean isFoodAt(Position pos) {
        return !env.grassGrid[pos.x][pos.y].isEaten();
    }

    private void rest() {
        stamina = 10;
        foodPoints--;
        System.out.println("Resting...");
    }

}
