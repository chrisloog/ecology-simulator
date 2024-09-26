package org.simulator.ecosimulator.controller;

import org.simulator.ecosimulator.model.Environment;
import org.simulator.ecosimulator.model.Position;
import org.simulator.ecosimulator.model.Rabbit;

public class SimulationController {

    private Environment environment;

    public SimulationController(Environment environment) {
        this.environment = environment;
    }

    public void start() {
        environment.addAgent(new Rabbit(new Position(1, 1), environment));
        while (!environment.isExtinct) {
            environment.update();
            System.out.println("Elapsed Days: " + environment.getElapsedDays());
            System.out.println(environment.agents.size());
            render();
        }
    }

    private void render() {

    }
}
