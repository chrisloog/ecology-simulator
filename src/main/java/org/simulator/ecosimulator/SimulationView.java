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
        environment = new Environment(30, 30);

        environment.addAgent(new Rabbit(new Position(30, 28), environment));
        // environment.addAgent(new Fox(new Position(1, 1), environment));

        Canvas canvas = new Canvas(environment.getWidth() * CELL_SIZE, environment.getHeight() * CELL_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            environment.update();
            render(gc);
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Ecosystem Simulation");
        primaryStage.show();
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, environment.getWidth() * CELL_SIZE, environment.getHeight() * CELL_SIZE);

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
