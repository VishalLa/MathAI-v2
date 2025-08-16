package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TrashController {

    @FXML
    private VBox sidebar;
    @FXML
    private Button homeButton;
    @FXML
    private Button favoritesButton;
    @FXML
    private Button trashButton;

    @FXML
    public void initialize() {
        // You can add logic here specific to the Trash view
        // For example, loading the trash items from a data source
        System.out.println("Trash view initialized.");
    }
}
