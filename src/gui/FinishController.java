package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import tictactoe.Board.*;

public class FinishController {

    public VBox vbox;
    public Label label;
    public ImageView imgView;

    public void setOptions(Player won, Image image) {
        if (won == null) {
            label.setText("It's a tie!");
        } else {
            label.setText("Player " + won.toString() + " wins!");
        }
        BackgroundFill background_fill = new BackgroundFill(Color.PINK,
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        vbox.setBackground(background);
        vbox.setOpacity(0.9);
        imgView.setImage(image);
    }

}
