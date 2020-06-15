package gui;

import ai.RandomAI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;

import static gui.Main.X_DIM;
import static gui.Main.Y_DIM;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import tictactoe.Board;
import tictactoe.Board.*;

public class PlayController {

    private AgentType player_X;
    private AgentType player_O;
    private Board gameboard;

    public Canvas canvas = new Canvas();
    public GraphicsContext gc;

    // Sets who is playing: HvH, HvB, or BvB, and the human's player.
    // Called by the StartController to pass in information.
    public void setOptions(String who_is, String player_is) {
        if (who_is.equals("HvH")) {
            player_X = AgentType.HUMAN;
            player_O = AgentType.HUMAN;
        } else if (who_is.equals("HvB")) {
            if (player_is.equals("X")) {
                player_X = AgentType.HUMAN;
                player_O = AgentType.BOT;
            } else {
                player_X = AgentType.BOT;
                player_O = AgentType.HUMAN;
            }
        } else {
            player_X = AgentType.BOT;
            player_O = AgentType.BOT;
        }
        // We can't set these values in the Board
        // constructor, or in initialize(),
        // so we have to be sure to set them here.
        gameboard.setPlayer(Player.X, player_X);
        gameboard.setPlayer(Player.O, player_O);

        // Start the game off if a bot is X.
        if (player_X.equals(AgentType.BOT)) {
            nextMove();
        }
    }

    public void initialize() {
        // Start up a new game
        gameboard = new Board(3);

        // This is where we'll draw the game as it progresses
        gc = canvas.getGraphicsContext2D();

        // draw the grid
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.strokeLine(X_DIM/3, 0, X_DIM/3, Y_DIM);
        gc.strokeLine(2*X_DIM/3, 0, 2*X_DIM/3, Y_DIM);
        gc.strokeLine(0, Y_DIM/3, X_DIM, Y_DIM/3);
        gc.strokeLine(0, 2*Y_DIM/3, X_DIM, 2*Y_DIM/3);

        // Handle human moves
        canvas.setOnMouseClicked(e -> {
            // Make sure that the player is actually human before proceeding
            if (gameboard.whoHasTheTurn().equals(AgentType.HUMAN)) {
                int x = (int) (e.getX() / (X_DIM/3));
                int y = (int) (e.getY() / (Y_DIM/3));
                humanMove(x, y);
            }
        });
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
        if (gameboard.isOver()) {
            transitionToFinish();
        } else {
            // If the bot needs to make a move, then let it.
            if (gameboard.whoHasTheTurn().equals(AgentType.BOT)) {
                int[] move = RandomAI.chooseMove(gameboard);
                drawMarker(move[0], move[1], gameboard.getTurn());
                gameboard.applyMove(move[0], move[1]);
                nextMove();
            }
        }
    }

    // TODO: Make this general for any board size
    private void drawMarker(int x, int y, Player player) {
        if (player.equals(Player.X)) {
            int x_start1 = X_DIM / 9 + (x * X_DIM) / 3;
            int y_start1 = Y_DIM / 9 + (y * Y_DIM) / 3;
            int x_end1 = 2 * X_DIM / 9 + (x * X_DIM) / 3;
            int y_end1 = 2 * Y_DIM / 9 + (y * Y_DIM) / 3;
            int x_start2 = 2 * X_DIM / 9 + (x * X_DIM) / 3;
            int y_start2 = Y_DIM / 9 + (y * Y_DIM) / 3;
            int x_end2 = X_DIM / 9 + (x * X_DIM) / 3;
            int y_end2 = 2 * Y_DIM / 9 + (y * Y_DIM) / 3;
            gc.strokeLine(x_start1, y_start1, x_end1, y_end1);
            gc.strokeLine(x_start2, y_start2, x_end2, y_end2);
        } else {
            gc.strokeOval((x*X_DIM)/3 + X_DIM/9, (y*Y_DIM)/3 + Y_DIM/9, X_DIM/9, Y_DIM/9);
        }
    }

    private void transitionToFinish() {
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
        finishController.setOptions(gameboard.findWinner(), finalState);

        // 3. Display the finish scene in the window.
        Scene finishScene = new Scene(finishParent);
        Stage window = (Stage) canvas.getScene().getWindow();
        window.setScene(finishScene);

    }

}
