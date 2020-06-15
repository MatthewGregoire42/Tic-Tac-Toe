package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController {

    public Button back;

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
