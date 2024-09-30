package org.simulator.ecosimulator;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.simulator.ecosimulator.model.*;


public class SimulationView extends Application {
    private Environment environment;
    private static final int CELL_SIZE = 10;

    public SimulationView() {
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize the environment
        environment = new Environment(15, 15); // Set desired width and height

        // Add initial agents to the environment
        environment.addAgent(new Rabbit(new Position(10, 10), environment));
        // environment.addAgent(new Fox(new Position(5, 5), environment));
        // Add more agents as needed

        // Set up the canvas and graphics context
        Canvas canvas = new Canvas(environment.getWidth() * CELL_SIZE, environment.getHeight() * CELL_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Animation loop using Timeline
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), e -> {
            environment.update();
            render(gc);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Set up the scene and stage
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Ecosystem Simulation");
        primaryStage.show();
    }

    private void render(GraphicsContext gc) {
        // Clear canvas
        gc.clearRect(0, 0, environment.getWidth() * CELL_SIZE, environment.getHeight() * CELL_SIZE);

        // Draw grass patches
        gc.setFill(Color.GREEN);
        for (int x = 0; x < environment.getWidth(); x++) {
            for (int y = 0; y < environment.getHeight(); y++) {
                Grass grass = environment.getGrassAt(x, y);
                if (!grass.isEaten()) {
                    gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        gc.setFill(Color.BROWN);
        for (int x = 0; x < environment.getWidth(); x++) {
            for (int y = 0; y < environment.getHeight(); y++) {
                Grass grass = environment.getGrassAt(x, y);
                if (grass.isEaten()) {
                    gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        // Draw agents
        for (Agent agent : environment.getAgents()) {
            if (agent instanceof Rabbit) {
                gc.setFill(Color.WHITE);
            } else if (agent instanceof Fox) {
                gc.setFill(Color.ORANGE);
            }
            Position pos = agent.getPosition();
            double padding = CELL_SIZE * 0.2;
            gc.fillOval(pos.x * CELL_SIZE + padding / 2, pos.y * CELL_SIZE + padding / 2, CELL_SIZE - padding, CELL_SIZE - padding);
        }
    }


}
