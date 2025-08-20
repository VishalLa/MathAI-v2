package controllers.NotebookControllers;

import java.io.IOException;

import controllers.Controllerabstract;
import controllers.FolderItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import utils.ThemeManager;

public class NotebookScene extends Controllerabstract {

    @FXML private AnchorPane notebookPage;
    @FXML private MenuButton newMenuButton;
    @FXML private ToggleButton themeToggle;

    @FXML
    public void initialize() {
       if (themeToggle == null) {
            System.err.println("themeToggle is not injected! Check fx:id in Home.fxml.");
        } else if (notebookPage == null) {
            System.err.println("homePage is not injected! Check fx:id in Home.fxml.");
        } else{
            selectButton(notebutton);
            
            themeToggle.selectedProperty().bindBidirectional(ThemeManager.darkModeProperty());
            setupThemeBinding(notebookPage);

            docbutton.setOnAction(event -> {
                selectButton(docbutton);
                try {
                    changeScene("/view/Home.fxml", event);
                } catch (IOException e) {
                    System.err.println("Error loading Home.fxml: " + e.getMessage());
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
     * Handles the action of creating a new Notebook.
     */
    @FXML
    public void handleNewNotebook() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NotebookItem.fxml"));
            HBox notebookItem = loader.load();

            // get controller
            NotebookItemController controller = loader.getController();

            // Generate unique ID for this notebook 
            String id = java.util.UUID.randomUUID().toString();
            String title = "Notebook" + (documentContainer.getChildren().size() + 1);

            controller.setData(id, title, () -> {
                System.out.println("📖 Opening notebook: " + id);
                // TODO: load NotebookEditor.fxml here
            });
            
            documentContainer.getChildren().add(notebookItem);
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