package gui;

import ai.Agent;
import ai.RandomAI;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import static gui.Main.X_DIM;
import static gui.Main.Y_DIM;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import tictactoe.Board;
import tictactoe.Board.*;

public class PlayController {

    @FXML private VBox vbox;
    @FXML private Canvas canvas;
    @FXML private GraphicsContext gc;

    private AgentType player_X;
    private AgentType player_O;
    private Board gameboard;
    private Agent bot;

    // Sets who is playing: HvH, HvB, or BvB, and the human's player.
    // Called by the StartController to pass in information.
    public void setOptions(AgentType x, AgentType o, int s) {
        player_X = x;
        player_O = o;

        gameboard = new Board(s);
        // We can't set these values in the Board
        // constructor, or in initialize(),
        // so we have to be sure to set them here.
        gameboard.setPlayer(Player.X, player_X);
        gameboard.setPlayer(Player.O, player_O);

        drawBoard();

        // Start the game off if a bot is X.
        if (player_X.equals(AgentType.BOT)) {
            nextMove();
        }
    }

    public void initialize() {
        // Start up a new game
        bot = new RandomAI();

        // This is where we'll draw the game as it progresses
        gc = canvas.getGraphicsContext2D();

        // draw the grid
        vbox.setStyle("-fx-background-color: white");
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        // Handle human moves
        canvas.setOnMouseClicked(e -> {
            // Make sure that the player is actually human before proceeding
            if (gameboard.whoHasTheTurn().equals(AgentType.HUMAN)) {
                int x = (int) (e.getX() / (X_DIM/gameboard.getSize()));
                int y = (int) (e.getY() / (Y_DIM/gameboard.getSize()));
                humanMove(x, y);
            }
        });
    }

    private void drawBoard() {
        int s = gameboard.getSize();

        for (int i = 1; i < s; i++) {
            gc.strokeLine(i*X_DIM/s, 0, i*X_DIM/s, Y_DIM);
            gc.strokeLine(0, i*Y_DIM/s, X_DIM, i*Y_DIM/s);
        }
    }

    // Takes a human move, draws it on the screen,
    // and applies it to the board. Then check if we
    // need to handle a bot move or the end of the game.
    // Called when the screen is clicked.
    private void humanMove(int x, int y) {
        if (gameboard.isLegalMove(x,y)) {
            drawMarker(x, y, gameboard.getTurn());
            gameboard.applyMove(x,y);
            // Handle the next move, if it's a bot move.
            if (gameboard.isOver()) {
                transitionToFinish();
            } else {
                nextMove();
            }
        }
    }

    // Applies a bot move, if necessary.
    private void nextMove() {
        // If the bot needs to make a move, then let it.
        if (gameboard.whoHasTheTurn().equals(AgentType.BOT)) {

            // Also use concurrency to let the UI remain responsive while
            // the bot is thinking.
            Service<int[]> thinking = new Service<int[]>() {
                @Override
                protected Task<int[]> createTask() {
                    return new Task<int[]>() {
                        protected int[] call() throws Exception {
                            Thread.sleep(200);
                            int[] move = bot.chooseMove(gameboard);
                            return move;
                        }
                    };
                }
            };

            thinking.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    int[] move = thinking.getValue();
                    drawMarker(move[0], move[1], gameboard.getTurn());
                    gameboard.applyMove(move[0], move[1]);

                    if (gameboard.isOver()) {
                        transitionToFinish();
                    } else {
                        nextMove();
                    }
                }
            });

            thinking.restart();
        }
    }

    // TODO: Make this general for any board size
    private void drawMarker(int x, int y, Player player) {
        int s = gameboard.getSize();
        if (player.equals(Player.X)) {
            int x_start1 = X_DIM / (s*3) + (x * X_DIM) / s;
            int y_start1 = Y_DIM / (s*3) + (y * Y_DIM) / s;
            int x_end1 = 2 * X_DIM / (s*3) + (x * X_DIM) / s;
            int y_end1 = 2 * Y_DIM / (s*3) + (y * Y_DIM) / s;
            int x_start2 = 2 * X_DIM / (s*3) + (x * X_DIM) / s;
            int y_start2 = Y_DIM / (s*3) + (y * Y_DIM) / s;
            int x_end2 = X_DIM / (s*3) + (x * X_DIM) / s;
            int y_end2 = 2 * Y_DIM / (s*3) + (y * Y_DIM) / s;
            gc.strokeLine(x_start1, y_start1, x_end1, y_end1);
            gc.strokeLine(x_start2, y_start2, x_end2, y_end2);
        } else {
            gc.strokeOval((x*X_DIM)/s + X_DIM/(s*3), (y*Y_DIM)/s + Y_DIM/(s*3), X_DIM/(s*3), Y_DIM/(s*3));
        }
    }

    private void transitionToFinish() {

        Service<Void> pause = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Thread.sleep(1000);
                        return null;
                    }
                };
            }
        };

        pause.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                // 1. Take a picture of the final board state, to use as a background to the finish scene.
                Image finalState = canvas.snapshot(new SnapshotParameters(), null);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Finish.fxml"));

                // 2. Let the finish controller know who won the game and what to use as a background.
                Parent finishParent;
                try {
                    finishParent = loader.load();
                } catch (Exception exception) {
                    finishParent = null;
                }
                FinishController finishController = loader.getController();
                finishController.setOptions(gameboard.findWinner(), finalState,
                        player_X, player_O, gameboard.getSize());

                // 3. Display the finish scene in the window.
                Scene finishScene = new Scene(finishParent);
                Stage window = (Stage) canvas.getScene().getWindow();
                window.setScene(finishScene);
            }
        });

        pause.restart();

    }

}
