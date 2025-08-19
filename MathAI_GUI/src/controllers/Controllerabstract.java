package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public abstract class Controllerabstract {

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

    protected void changeScene(String fxmlPath, ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("⚠️ Failed to load FXML: " + fxmlPath);
        }
    }

    @SuppressWarnings("unused")
    protected void setupThemeBinding(Node rootNode) {
        utils.ThemeManager.darkModeProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                if (!rootNode.getStyleClass().contains("dark")) {
                    rootNode.getStyleClass().add("dark");
                }
            } else {
                rootNode.getStyleClass().remove("dark");
            }
        });

        // Apply current theme immediately
        if (utils.ThemeManager.isDarkMode()) {
            if (!rootNode.getStyleClass().contains("dark")) {
                rootNode.getStyleClass().add("dark");
            }
        }
    }
}
