package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import tictactoe.Board.*;

public class FinishController {

    @FXML private Label label;
    @FXML private ImageView imgView;

    private AgentType player_X;
    private AgentType player_O;
    private int s;
    private String bot;

    // The Finish screen needs to know who won and the final board state to set as
    // the background. It also needs to know who played as X and O and what size the
    // board was, in case the user wants to play again with the same settings.
    public void setOptions(Player won, Image image, AgentType x, AgentType o, int size, String botType) {

        player_X = x;
        player_O = o;
        s = size;
        bot = botType;

        if (won == null) {
            label.setText("It's a tie!");
        } else {
            label.setText("Player " + won.toString() + " wins!");
        }
        imgView.setImage(image);
    }

    @FXML private void backToPlay(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Play.fxml"));
        Parent playParent = loader.load();

        PlayController playController = loader.getController();

        Scene playScene = new Scene(playParent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(playScene);

        playController.setOptions(player_X, player_O, s, bot);
    }

    @FXML private void backToStart(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Start.fxml"));
        Parent startParent = loader.load();

        StartController startController = loader.getController();

        Scene startScene = new Scene(startParent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(startScene);
    }
}
