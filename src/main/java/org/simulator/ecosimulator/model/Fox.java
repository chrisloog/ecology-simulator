package org.simulator.ecosimulator.model;

import java.util.List;

public class Fox extends Agent {

    private static final int SEARCH_RANGE = 3;

    public Fox(Position position, Environment env) {
        super(position, env);
    }

    @Override
    public void update() {
        move();
        hunt();
        reproduce();
    }

    private void move() {
        Position target = findNearestTarget(SEARCH_RANGE, this::isFoodAt);
        moveTowards(target);
    }

    private void hunt() {
        List<Agent> agentsAtPosition = env.getAgentsAtPosition(this.position);

        for (Agent agent : agentsAtPosition) {
            if (agent instanceof Rabbit) {
                env.removeAgent(agent);
                break;
            }
        }

        foodPoints++;
    }

    private void reproduce() {
        if (foodPoints >= 5) {
            env.addAgent(new Fox(
                    new Position(this.position.x + 1, this.position.y),
                    env)
            );

            foodPoints = 0;
        }
    }

    private boolean isFoodAt(Position pos) {
        return env.getAgentsAtPosition(pos).stream()
                .anyMatch(agent -> agent instanceof Rabbit);
    }
}