package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;

public class random {

    @FXML private Button docbutton;
    @FXML private Button notebutton;
    @FXML private Button favbutton;
    @FXML private Button trashbutton;
    @FXML private Button newbutton;
    @FXML private Button settingbutton;
    @FXML private Button accbutton;

    @FXML private TilePane documentContainer;

    @FXML
    private void initialize() {
        System.out.println("Home view initialized");
    }

    @FXML
    private void handleDocuments() {
        System.out.println("Documents clicked");
    }

    @FXML
    private void handleNotebooks() {
        System.out.println("Notebooks clicked");
    }

    @FXML
    private void handleFavorites() {
        System.out.println("Favorites clicked");
    }

    @FXML
    private void handleTrash() {
        System.out.println("Trash clicked");
    }

    @FXML
    private void handleNew() {
        System.out.println("+ New clicked");
    }

    @FXML
    private void handleSettings() {
        System.out.println("Settings clicked");
    }

    @FXML
    private void handleAccount() {
        System.out.println("Account clicked");
    }
    
}
