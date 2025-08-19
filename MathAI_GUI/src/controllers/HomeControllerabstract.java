package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public abstract class HomeControllerabstract {

    @FXML protected ToggleButton themeToggle;
    @FXML protected Button docbutton;
    @FXML protected Button notebutton;
    @FXML protected Button favbutton;
    @FXML protected Button trashbutton;
    @FXML protected TilePane documentContainer;

    protected Button lastSelectedButton;

    protected void selectButton(Button button){
        if (lastSelectedButton != null){
            lastSelectedButton.getStyleClass().remove("selected-button");
        }
        button.getStyleClass().add("selected-button");
        lastSelectedButton = button;
    }

    /**
     * A generic method to load a new FXML scene and set it on the current stage.
     * This prevents code duplication for navigation.
     * @param fxmlFile The path to the FXML file to load.
     * @param event The ActionEvent that triggered this method.
     * @throws IOException if the FXML file cannot be loaded.
     */
    protected void changeScene(String fxmlFile, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
