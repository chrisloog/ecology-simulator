package org.simulator.ecosimulator.model;

public class Rabbit extends Agent {

    private static final int SEARCH_RANGE = 3;

    public Rabbit(Position position, Environment env) {
        super(position, env);
    }

    @Override
    public void update() {
        move();
        eat();
        reproduce();
    }

    private void move() {
        Position target = findNearestTarget(SEARCH_RANGE, this::isFoodAt);
        moveTowards(target);
    }

    private void eat() {
        env.grassGrid[this.position.x][this.position.y].setEaten(true);
        foodPoints++;
    }

    private void reproduce() {
        if (foodPoints >= 10) {
            env.addAgent(new Rabbit(
                    new Position(this.position.x + 1, this.position.y)
                    , env)
            );

            foodPoints = 0;
        }
    }

    private boolean isFoodAt(Position pos) {
        return !env.grassGrid[pos.x][pos.y].isEaten();
    }

}
