package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import tictactoe.Board;
import tictactoe.Board.*;

public class StartController {

    @FXML private ToggleGroup who = new ToggleGroup();
    @FXML private RadioButton hvh_button = new RadioButton();
    @FXML private RadioButton hvb_button = new RadioButton();
    @FXML private RadioButton bvb_button = new RadioButton();

    @FXML private ToggleGroup player = new ToggleGroup();
    @FXML private RadioButton x_button = new RadioButton();
    @FXML private RadioButton o_button = new RadioButton();

    @FXML private ToggleGroup board_size = new ToggleGroup();
    @FXML private RadioButton three = new RadioButton();
    @FXML private RadioButton four = new RadioButton();
    @FXML private RadioButton five = new RadioButton();

    private AgentType player_X;
    private AgentType player_O;

    @FXML private void initialize() {
        hvh_button.setToggleGroup(who);
        hvb_button.setToggleGroup(who);
        bvb_button.setToggleGroup(who);

        x_button.setToggleGroup(player);
        o_button.setToggleGroup(player);

        three.setToggleGroup(board_size);
        four.setToggleGroup(board_size);
        five.setToggleGroup(board_size);

        who.selectToggle(hvb_button);
        player.selectToggle(x_button);
        board_size.selectToggle(three);
    }

    // What to do when the user presses the "play" button.
    @FXML private void pressPlay(ActionEvent e) throws Exception {
        RadioButton who_is_button = (RadioButton) who.getSelectedToggle();
        RadioButton player_is_button = (RadioButton) player.getSelectedToggle();
        RadioButton board_size_button = (RadioButton) board_size.getSelectedToggle();

        if (who_is_button.equals(hvh_button)) {
            player_X = AgentType.HUMAN;
            player_O = AgentType.HUMAN;
        } else if (who_is_button.equals(hvb_button)) {
            if (player_is_button.equals(x_button)) {
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

        int s = 3;

        if (board_size_button.equals(three)) {
            s = 3;
        } else if (board_size_button.equals(four)) {
            s = 4;
        } else {
            s = 5;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Play.fxml"));
        Parent playParent = loader.load();

        PlayController playController = loader.getController();

        Scene playScene = new Scene(playParent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(playScene);

        playController.setOptions(player_X, player_O, s);
    }

    // Switch to "About" scene when the user presses the "About" button.
    @FXML private void pressAbout(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("About.fxml"));
        Parent aboutParent = loader.load();

        AboutController aboutController = loader.getController();

        Scene aboutScene = new Scene(aboutParent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(aboutScene);
    }

}
