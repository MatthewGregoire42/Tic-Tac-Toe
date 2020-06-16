package gui;

import ai.RandomAI;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import tictactoe.Board;
import javafx.scene.layout.VBox;

public class Main extends Application {

    public static final int X_DIM = 600;
    public static final int Y_DIM = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader start_loader = new FXMLLoader(getClass().getResource("Start.fxml"));
        VBox vbox_start = start_loader.<VBox>load();
        Scene scene = new Scene(vbox_start, X_DIM, Y_DIM);

        primaryStage.getIcons().add(new Image("gui/icon.png"));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}