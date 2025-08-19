package controllers.HomeControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import utils.ThemeManager;

import java.io.IOException;

import controllers.Controllerabstract;

/**
 * Controller class for the Home.fxml view. It manages the main dashboard,
 * including sidebar navigation and adding new items (documents, notebooks, folders).
 */
public class Home extends Controllerabstract {

    // FXML injected UI components
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
            // Initially set the "Documents" button as selected
            selectButton(docbutton);

            // set Theme
            themeToggle.selectedProperty().bindBidirectional(ThemeManager.darkModeProperty());
            setupThemeBinding(homePage);

            // Set up action listeners for the sidebar buttons to change scenes
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
            Node newDocument = FXMLLoader.load(getClass().getResource("/view/DocumentItem.fxml"));
            documentContainer.getChildren().add(newDocument);
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
            Node newFolder = FXMLLoader.load(getClass().getResource("/view/FolderItem.fxml"));
            documentContainer.getChildren().add(newFolder);
        } catch (IOException e) {
            System.err.println("Error loading FolderItem.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
