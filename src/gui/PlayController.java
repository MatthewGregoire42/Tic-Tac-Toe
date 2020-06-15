package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static gui.Main.X_DIM;
import static gui.Main.Y_DIM;
import tictactoe.Board;

public class PlayController {

    private String player_X;
    private String player_O;
    private Board gameboard;

    public Canvas canvas = new Canvas();
    public GraphicsContext gc;

    // Sets who is playing: HvH, HvB, or BvB, and the human's player.
    public void setOptions(String who_is, String player_is) {
        if (who_is.equals("HvH")) {
            player_X = "human";
            player_O = "human";
        } else if (who_is.equals("HvB")) {
            if (player_is.equals("X")) {
                player_X = "human";
                player_O = "bot";
            } else {
                player_X = "bot";
                player_O = "human";
            }
        } else {
            player_X = "bot";
            player_O = "bot";
        }
    }

    public void initialize() {
        // Start up a new game
        gameboard = new Board(3);

        gc = canvas.getGraphicsContext2D();

        // draw the grid
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.strokeLine(X_DIM/3, 0, X_DIM/3, Y_DIM);
        gc.strokeLine(2*X_DIM/3, 0, 2*X_DIM/3, Y_DIM);
        gc.strokeLine(0, Y_DIM/3, X_DIM, Y_DIM/3);
        gc.strokeLine(0, 2*Y_DIM/3, X_DIM, 2*Y_DIM/3);
    }

    private void humanMove(int x, int y) {
        if (gameboard.isLegalMove(x,y)) {
            drawMarker(x, y, gameboard.getTurn());
            gameboard.applyMove(x,y);
        }
    }

    private void drawMarker(int x, int y, Board.Player player) {
        if (player.equals(Board.Player.X)) {
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

}
