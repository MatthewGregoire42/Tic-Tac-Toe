package gui;

import ai.RandomAI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import tictactoe.Board;

import java.util.Random;

public class Main extends Application {

    public static final int X_DIM = 600;
    public static final int Y_DIM = 600;

    @Override
    public void start(Stage primaryStage) {

        Board ticTacToe = new Board(3);
        RandomAI bot = new RandomAI();

        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(X_DIM, Y_DIM);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);

        canvas.setOnMouseClicked(e -> {
            int x = (int) (e.getX() / (X_DIM/3));
            int y = (int) (e.getY() / (Y_DIM/3));

            drawX(gc, x, y);
            ticTacToe.applyMove(x, y);

            int[] bot_move = bot.chooseMove(ticTacToe);
            ticTacToe.applyMove(bot_move[0], bot_move[1]);
            drawO(gc, bot_move[0], bot_move[1]);
        });

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.strokeLine(X_DIM/3, 0, X_DIM/3, Y_DIM);
        gc.strokeLine(2*X_DIM/3, 0, 2*X_DIM/3, Y_DIM);
        gc.strokeLine(0, Y_DIM/3, X_DIM, Y_DIM/3);
        gc.strokeLine(0, 2*Y_DIM/3, X_DIM, 2*Y_DIM/3);
    }

    private void drawX(GraphicsContext gc, int x, int y) {
        int x_start1 = X_DIM/9 + (x*X_DIM)/3;
        int y_start1 = Y_DIM/9 + (y*Y_DIM)/3;
        int x_end1 = 2*X_DIM/9 + (x*X_DIM)/3;
        int y_end1 = 2*Y_DIM/9 + (y*Y_DIM)/3;
        int x_start2 = 2*X_DIM/9 + (x*X_DIM)/3;
        int y_start2 = Y_DIM/9 + (y*Y_DIM)/3;
        int x_end2 = X_DIM/9 + (x*X_DIM)/3;
        int y_end2 = 2*Y_DIM/9 + (y*Y_DIM)/3;
        gc.strokeLine(x_start1, y_start1, x_end1, y_end1);
        gc.strokeLine(x_start2, y_start2, x_end2, y_end2);
    }

    private void drawO(GraphicsContext gc, int x, int y) {
        gc.strokeOval((x*X_DIM)/3 + X_DIM/9, (y*Y_DIM)/3 + Y_DIM/9, X_DIM/9, Y_DIM/9);
    }

//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        // Sets window title
//        primaryStage.setTitle("Hello World");
//        StackPane layout = new StackPane();
//
//
//        layout.getChildren().add(new Button("Click here"));
//
//        primaryStage.setScene(new Scene(layout, 300, 300));
//        primaryStage.show();
//    }


    public static void main(String[] args) {
        launch(args);
    }
}