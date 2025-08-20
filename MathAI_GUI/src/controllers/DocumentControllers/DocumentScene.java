package controllers.DocumentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import utils.ThemeManager;

import java.io.IOException;

import controllers.Controllerabstract;
import controllers.FolderItemController;

public class DocumentScene extends Controllerabstract {

    @FXML private AnchorPane homePage;
    @FXML private MenuButton newMenuButton;
    @FXML private ToggleButton themeToggle;

    @FXML
    public void initialize() {
        if (themeToggle == null) {
            System.err.println("themeToggle is not injected! Check fx:id in Home.fxml.");
        } else if (homePage == null) {
            System.err.println("homePage is not injected! Check fx:id in Home.fxml.");
        } else {
            selectButton(docbutton);

            themeToggle.selectedProperty().bindBidirectional(ThemeManager.darkModeProperty());
            setupThemeBinding(homePage);

            notebutton.setOnAction(event -> {
                selectButton(notebutton);
                try {
                    changeScene("/view/Notebook.fxml", event);
                } catch (IOException e) {
                    System.err.println("Error loading Notebooks.fxml: " + e.getMessage());
                }
            });
            favbutton.setOnAction(event -> {
                selectButton(favbutton);
                try {
                    changeScene("/view/Favorites.fxml", event);
                } catch (IOException e) {
                    System.err.println("Error loading Favorites.fxml: " + e.getMessage());
                }
            });
            trashbutton.setOnAction(event -> {
                selectButton(trashbutton);
                try {
                    changeScene("/view/Trash.fxml", event);
                } catch (IOException e) {
                    System.err.println("Error loading Trash.fxml: " + e.getMessage());
                }
            });
        }
    }

    /**
     * Handles the action of creating a new Document.
     */
    @FXML
    public void handleNewDocument() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DocumentItem.fxml"));
            HBox documentItem = loader.load();

            // get controller
            DocumentItemController controller = loader.getController();

            // Generate unique ID for this document
            String id = java.util.UUID.randomUUID().toString();
            String title = "Document" + (documentContainer.getChildren().size() + 1);

            controller.setData(id, title, () -> {
                System.out.println("📖 Opening document: " + id);
                // TODO: load DocumentEditor.fxml here
            });

            documentContainer.getChildren().add(documentItem);
        } catch (IOException e) {
            System.err.println("Error loading DocumentItem.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of creating a new Folder.
     */
    @FXML
    public void handleNewFolder() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FolderItem.fxml"));
            HBox folderItem = loader.load();

            // get controller
            FolderItemController controller = loader.getController();

            // Generate unique ID for this folder
            String id = java.util.UUID.randomUUID().toString();
            String title = "Folder" + (documentContainer.getChildren().size() + 1);

            controller.setData(id , title, () -> {
                System.out.println("📖 Opening Folder: " + id);
                // TODO: load Folder.fxml here
            });

            documentContainer.getChildren().add(folderItem);
        } catch (IOException e) {
            System.err.println("Error loading FolderItem.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
