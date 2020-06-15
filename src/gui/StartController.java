package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class StartController {

    public ToggleGroup who = new ToggleGroup();
    public ToggleGroup player = new ToggleGroup();
    public RadioButton hvh_button = new RadioButton();
    public RadioButton hvb_button = new RadioButton();
    public RadioButton bvb_button = new RadioButton();
    public RadioButton x_button = new RadioButton();
    public RadioButton o_button = new RadioButton();

    public void initialize() {
        hvh_button.setToggleGroup(who);
        hvb_button.setToggleGroup(who);
        bvb_button.setToggleGroup(who);
        x_button.setToggleGroup(player);
        o_button.setToggleGroup(player);

        who.selectToggle(hvb_button);
        player.selectToggle(x_button);
    }

    // What to do when the user presses the "play" button.
    public void pressPlay(ActionEvent e) throws Exception {
        RadioButton who_is_button = (RadioButton) who.getSelectedToggle();
        RadioButton player_is_button = (RadioButton) player.getSelectedToggle();

        String who_is, player_is;

        if (who_is_button.equals(hvh_button)) {
            who_is = "HvH";
        } else if (who_is_button.equals(hvb_button)) {
            who_is = "HvB";
        } else {
            who_is = "BvB";
        }

        if (player_is_button.equals(x_button)) {
            player_is = "X";
        } else {
            player_is = "O";
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Play.fxml"));
        Parent playParent = loader.load();

        PlayController playController = loader.getController();
        playController.setOptions(who_is, player_is);

        Scene playScene = new Scene(playParent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(playScene);
    }

    // What to do when the user presses the "about" button.
    public void pressAbout() {

    }

}
