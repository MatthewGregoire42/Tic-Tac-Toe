package gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import tictactoe.Board.*;

public class StartController {

    @FXML private ToggleGroup who = new ToggleGroup();
    @FXML private ToggleButton hvh_button = new RadioButton();
    @FXML private ToggleButton hvb_button = new RadioButton();
    @FXML private ToggleButton bvb_button = new RadioButton();

    @FXML private ToggleGroup player = new ToggleGroup();
    @FXML private ToggleButton x_button = new RadioButton();
    @FXML private ToggleButton o_button = new RadioButton();

    @FXML private ToggleGroup board_size = new ToggleGroup();
    @FXML private ToggleButton three = new RadioButton();
    @FXML private ToggleButton four = new RadioButton();
    @FXML private ToggleButton five = new RadioButton();

    @FXML private ToggleGroup whichBot = new ToggleGroup();
    @FXML private ToggleButton random = new RadioButton();
    @FXML private ToggleButton minimax = new RadioButton();

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

        random.setToggleGroup(whichBot);
        minimax.setToggleGroup(whichBot);

        who.selectToggle(hvb_button);
        player.selectToggle(x_button);
        board_size.selectToggle(three);
        whichBot.selectToggle(minimax);

        BooleanProperty disableLarge = new SimpleBooleanProperty(false);
        disableLarge.bind(minimax.selectedProperty().and(
                hvb_button.selectedProperty().or(bvb_button.selectedProperty())));

        // Only allow 3x3 for minimax. But we also want to allow 3x3 when minimax
        // is not selected, so we add a listener instead of using a direct binding.
        four.setDisable(true);
        five.setDisable(true);
        minimax.selectedProperty().addListener((observable, oldValue, isSelected) -> {
            if (isSelected) {
                four.setDisable(true);
                five.setDisable(true);
                three.setSelected(true);
            } else {
                four.setDisable(false);
                five.setDisable(false);
            }
        });

        BooleanProperty disablePlayer = new SimpleBooleanProperty(false);
        disablePlayer.bind(hvh_button.selectedProperty().or(bvb_button.selectedProperty()));

        // The Player button isn't relevant when the user isn't doing human vs. bot.
        x_button.disableProperty().bind(disablePlayer);
        o_button.disableProperty().bind(disablePlayer);

        // The Bot button isn't relevant when the user selects human vs. human.
        random.disableProperty().bind(hvh_button.selectedProperty());
        minimax.disableProperty().bind(hvh_button.selectedProperty());
    }

    // What to do when the user presses the "play" button.
    @FXML private void pressPlay(ActionEvent e) throws Exception {
        ToggleButton who_is_button = (ToggleButton) who.getSelectedToggle();
        ToggleButton player_is_button = (ToggleButton) player.getSelectedToggle();
        ToggleButton board_size_button = (ToggleButton) board_size.getSelectedToggle();
        ToggleButton bot_button = (ToggleButton) whichBot.getSelectedToggle();

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

        int s;
        if (board_size_button.equals(three)) {
            s = 3;
        } else if (board_size_button.equals(four)) {
            s = 4;
        } else {
            s = 5;
        }

        String bot;
        if (bot_button.equals(random)) {
            bot = "random";
        } else {
            bot = "minimax";
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Play.fxml"));
        Parent playParent = loader.load();

        PlayController playController = loader.getController();

        Scene playScene = new Scene(playParent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(playScene);

        playController.setOptions(player_X, player_O, s, bot);
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
