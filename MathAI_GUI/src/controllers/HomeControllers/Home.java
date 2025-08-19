package controllers.HomeControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

import controllers.HomeControllerabstract;

/**
 * Controller class for the Home.fxml view. It manages the main dashboard,
 * including sidebar navigation and adding new items (documents, notebooks, folders).
 */
public class Home extends HomeControllerabstract {

    // FXML injected UI components
    @FXML private AnchorPane homePage;
    @FXML private MenuButton newMenuButton;

    @FXML
    public void initialize() {
        if (themeToggle == null) {
            System.err.println("themeToggle is not injected! Check fx:id in Home.fxml.");
        } else if (homePage == null) {
            System.err.println("homePage is not injected! Check fx:id in Home.fxml.");
        } else {
            // Set up action listeners for the sidebar buttons to change scenes
            docbutton.setOnAction(event -> {
                selectButton(docbutton);
                try {
                    changeScene("/view/Home.fxml", event);
                } catch (IOException e) {
                    System.err.println("Error loading Home.fxml: " + e.getMessage());
                }
            });
            notebutton.setOnAction(event -> {
                selectButton(notebutton);
                try {
                    changeScene("/view/Notebooks.fxml", event);
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

            // Initially set the "Documents" button as selected
            selectButton(docbutton);

            // Add a listener to handle theme changes.
            themeToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    homePage.getStyleClass().add("dark");
                } else {
                    homePage.getStyleClass().remove("dark");
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
