package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class TrashController {

    @FXML private AnchorPane trashPage;
    @FXML private ToggleButton themeToggle;

    @FXML
    public void initialize() {
        
    }

    @FXML
    private void goHome(ActionEvent event) {
        // TODO: Load Home.fxml
    }

    @FXML
    private void goFavorites(ActionEvent event) {
        // TODO: Load Favorites.fxml
    }

    @FXML
    private void handleNewDocument(ActionEvent event) {
        // TODO: Create new document logic
    }

    @FXML
    private void handleNewNotebook(ActionEvent event) {
        // TODO: Create new notebook logic
    }

    @FXML
    private void handleNewFolder(ActionEvent event) {
        // TODO: Create new folder logic
    }

    @FXML
    private void emptyTrash(ActionEvent event) {
        // TODO: Empty trash logic
    }
    
    @FXML
    private void selectedProperty(){

    }
}