package org.simulator.ecosimulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Environment {

    private int width, height;

    public List<Agent> agents;
    private List<Agent> newAgents;

    public Grass[][] grassGrid;

    public Boolean isExtinct = false;

    private int elapsedHours = 0;

    public Environment(int width, int height) {
        this.width = width;
        this.height = height;
        agents = new ArrayList<>();
        newAgents = new ArrayList<>();
        grassGrid = new Grass[width][height];

        initializeGrid();
    }

    public void addAgent(Agent agent) {
        newAgents.add(agent);
    }

    public void removeAgent(Agent agent) {
        agents.remove(agent);
    }

    public int getElapsedHours() {
        return elapsedHours;
    }

    public int getElapsedDays() {
        return elapsedHours / 24;
    }

    public void update() {
        for (Agent agent : agents) {
            agent.update();
            System.out.println(agent.position.toString());
        }

        agents.addAll(newAgents);
        newAgents.clear();

        // TODO: regrow grass after specific time
        // regrowGrass();

        if (agents.isEmpty()) {
            this.isExtinct = true;
        }

        elapsedHours++;
    }

    private void initializeGrid() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grassGrid[i][j] = new Grass();
            }
        }
    }

    private void regrowGrass() {
        for (Grass[] rowOfGrass : grassGrid) {
            for (Grass grassPatch : rowOfGrass) {
                if (grassPatch.isEaten()) {
                    grassPatch.setEaten(false);
                }
            }
        }
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public List<Agent> getAgentsAtPosition(Position position) {
        return agents.stream()
                .filter(agent -> agent.position.equals(position))
                .collect(Collectors.toList());
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Grass getGrassAt(int x, int y) { return grassGrid[x][y]; }
    public List<Agent> getAgents() { return agents; }

}
