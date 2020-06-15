package gui;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

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
    }

    // What to do when the user presses the "play" button.
    public void pressPlay() {
        RadioButton who_is = (RadioButton) who.getSelectedToggle();
        RadioButton player_is = (RadioButton) player.getSelectedToggle();


    }

    // What to do when the user presses the "about" button.
    public void pressAbout() {

    }

}
