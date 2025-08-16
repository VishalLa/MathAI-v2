package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.TilePane;
import java.io.IOException;

public class HomeController {

    @FXML
    private Button docbutton;
    @FXML
    private Button notebutton;
    @FXML
    private Button favbutton;
    @FXML
    private Button trashbutton;
    @FXML
    private MenuButton newMenuButton;
    @FXML
    private TilePane documentContainer;

    private Button lastSelectedButton;

    @FXML
    public void initialize() {
        // Set up action listeners for the sidebar buttons
        docbutton.setOnAction(event -> selectButton(docbutton));
        notebutton.setOnAction(event -> selectButton(notebutton));
        favbutton.setOnAction(event -> selectButton(favbutton));
        trashbutton.setOnAction(event -> selectButton(trashbutton));

        // Initially set the "Documents" button as selected
        selectButton(docbutton);
    }

    /**
     * Handles the action of creating a new Document.
     */
    @FXML
    public void handleNewDocument() {
        try {
            Node newDocument = FXMLLoader.load(getClass().getResource("../view/DocumentItem.fxml"));
            documentContainer.getChildren().add(newDocument);
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of creating a new Notebook.
     */
    @FXML
    public void handleNewNotebook() {
        try {
            Node newNotebook = FXMLLoader.load(getClass().getResource("../view/NotebookItem.fxml"));
            documentContainer.getChildren().add(newNotebook);
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of creating a new Folder.
     */
    @FXML
    public void handleNewFolder() {
        try {
            Node newFolder = FXMLLoader.load(getClass().getResource("../view/FolderItem.fxml"));
            documentContainer.getChildren().add(newFolder);
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Changes the style of the clicked button to indicate it is selected
     * and deselects the previously selected button.
     * @param button The button that was just clicked.
     */
    private void selectButton(Button button) {
        // Remove the selected class from the last selected button
        if (lastSelectedButton != null) {
            lastSelectedButton.getStyleClass().remove("selected-button");
        }
        
        // Add the selected class to the current button
        button.getStyleClass().add("selected-button");
        lastSelectedButton = button;
    }
}
