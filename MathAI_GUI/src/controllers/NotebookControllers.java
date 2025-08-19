package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

public class NotebookControllers extends HomeControllerabstract {

    @FXML private AnchorPane favoritesPage;
    @FXML private ToggleButton themeToggle;

    @FXML
    public void initialize() {
       if (themeToggle == null) {
            System.err.println("themeToggle is not injected! Check fx:id in Home.fxml.");
        } else if (favoritesPage == null) {
            System.err.println("homePage is not injected! Check fx:id in Home.fxml.");
        } else{
            docbutton.setOnAction(event -> {
                selectButton(docbutton);
                try {
                    changeScene("../view/Home.fxml", event);
                } catch (IOException e) {
                    System.err.println("Error loading Home.fxml: " + e.getMessage());
                }
            });
            notebutton.setOnAction(event -> {
                selectButton(notebutton);
                try {
                    changeScene("../view/Notebooks.fxml", event);
                } catch (IOException e) {
                    System.err.println("Error loading Notebooks.fxml: " + e.getMessage());
                }
            });
            favbutton.setOnAction(event -> {
                selectButton(favbutton);
                try {
                    changeScene("../view/Favorites.fxml", event);
                } catch (IOException e) {
                    System.err.println("Error loading Favorites.fxml: " + e.getMessage());
                }
            });
            trashbutton.setOnAction(event -> {
                selectButton(trashbutton);
                try {
                    changeScene("../view/Trash.fxml", event);
                } catch (IOException e) {
                    System.err.println("Error loading Trash.fxml: " + e.getMessage());
                }
            });
            
            selectButton(notebutton);
        }
    }

     /**
     * Handles the action of creating a new Notebook.
     */
    @FXML
    public void handleNewNotebook() {
        try {
            Node newNotebook = FXMLLoader.load(getClass().getResource("/view/NotebookItem.fxml"));
            documentContainer.getChildren().add(newNotebook);
        } catch (IOException e) {
            System.err.println("Error loading NotebookItem.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of creating a new Folder.
     */
    @FXML
    public void handleNewFolder() {
        try {
            Node newFolder = FXMLLoader.load(getClass().getResource("/view/FolderItem.fxml"));
            documentContainer.getChildren().add(newFolder);
        } catch (IOException e) {
            System.err.println("Error loading FolderItem.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}