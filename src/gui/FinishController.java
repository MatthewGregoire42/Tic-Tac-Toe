package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tictactoe.Board.*;

public class FinishController {

    public VBox vbox;
    public Label label;
    public ImageView imgView;

    private AgentType player_X;
    private AgentType player_O;

    public void setOptions(Player won, Image image, AgentType x, AgentType o) {

        player_X = x;
        player_O = o;

        if (won == null) {
            label.setText("It's a tie!");
        } else {
            label.setText("Player " + won.toString() + " wins!");
        }
        BackgroundFill background_fill = new BackgroundFill(Color.PINK,
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        vbox.setBackground(background);
        vbox.setOpacity(0.98);
        imgView.setImage(image);
    }

    public void backToPlay(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Play.fxml"));
        Parent playParent = loader.load();

        PlayController playController = loader.getController();

        Scene playScene = new Scene(playParent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(playScene);

        playController.setOptions(player_X, player_O);
    }

    public void backToStart(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Start.fxml"));
        Parent startParent = loader.load();

        StartController startController = loader.getController();

        Scene startScene = new Scene(startParent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(startScene);
    }
}
