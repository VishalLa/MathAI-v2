package controllers.HomeControllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import utils.StorageManager;
import utils.ThemeManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import controllers.Controllerabstract;
import controllers.itemController.DocumentItemController;
import controllers.itemController.FolderItemController;
import controllers.itemController.NotebookItemController;

public class HomeScene extends Controllerabstract {

    @FXML private AnchorPane homePage;
    @FXML private MenuButton newMenuButton;
    @FXML private ToggleButton themeToggle;
    @FXML private TilePane dcoumentContainer;
    @FXML private TilePane notebookContainer;
    @FXML private TilePane folderContainer;

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

            try {
                // Load documents
                loadItemsFromStorage(
                    "documents",
                    documentContainer,
                    "/view/DocumentItem.fxml",
                    DocumentItemController.class,
                    (controller, item) -> controller.setData(
                        item.get("id"),
                        item.get("title"),
                        () -> System.out.println("Open document: " + item.get("id"))
                    )
                );

                // Load notebooks
                loadItemsFromStorage(
                    "notebooks",
                    notebookContainer,
                    "/view/NotebookItem.fxml",
                    NotebookItemController.class,
                    (controller, item) -> controller.setData(
                        item.get("id"),
                        item.get("title"),
                        () -> System.out.println("Open notebook: " + item.get("id"))
                    )
                );

                // Load folders
                loadItemsFromStorage(
                    "folders",
                    folderContainer,
                    "/view/FolderItem.fxml",
                    FolderItemController.class,
                    (controller, item) -> controller.setData(
                        item.get("id"),
                        item.get("title"),
                        () -> System.out.println("Open folder: " + item.get("id"))
                    )
                );

            } catch (IOException e) {
                e.printStackTrace();
            }
            
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
            String title = "Untitiled Notebook";

            // Save to storage 
            StorageManager.saveNotebook(title, null);

            // Load index
            Map<String, List<Map<String, String>>> index = StorageManager.loadIndex();
            List<Map<String, String>> notebooks = index.get("notebooks");

            if (notebooks == null || notebooks.isEmpty()){
                System.err.println("No notebooks found in index!");
                return;
            }

            Map<String, String> newnotebook = notebooks.get(notebooks.size() - 1);

            Node itemNode = createItemNode(
                "/view/NotebookItem.fxml",
                NotebookItemController.class,
                newnotebook,
                (controller, item) -> controller.setData(item.get("id"), item.get("title"), () -> {
                    System.out.println("Opneing notebook: " + item.get("title"));
                })
            );

            notebookContainer.getChildren().add(itemNode);

        } catch (IOException e) {
            System.err.println("Error loading NotebookItem.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Handles the action of creating a new Document.
     */
    @FXML
    public void handleNewDocument() {
        try {
            String title = "Untitled Document";

            // Save to storage
            StorageManager.saveDocument(title, "");

            // Load index 
            Map<String, List<Map<String, String>>> index = StorageManager.loadIndex();
            List<Map<String, String>> docs = index.get("documents");

            if (docs == null || docs.isEmpty()) {
                System.err.println("No documents found in index!");
                return;
            }

            // Get the newly created doc (last entry)
            Map<String, String> newDoc = docs.get(docs.size() - 1);

            Node itemNode = createItemNode(
                "/view/DocumentItem.fxml",
                DocumentItemController.class,
                newDoc,
                (controller, item) -> controller.setData(item.get("id"), item.get("title"), () -> {
                    // TODO: open document editor
                    System.out.println("Opening document: " + item.get("title"));
                })
            );

            documentContainer.getChildren().add(itemNode);
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
            String title = "Untitled Folder";
            
            // Save to storage
            StorageManager.saveFolder(title, null);

            // Load index
            Map<String, List<Map<String, String>>> index = StorageManager.loadIndex();
            List<Map<String, String>> folder = index.get("folders");

            if (folder == null || folder.isEmpty()) {
                System.err.println("No folder found in index!");
                return;
            }

            // Get the newly created folder (last entry)
            Map <String, String> newFolder = folder.get(folder.size()-1);

            // Load FXML for new item
            Node itemNode = createItemNode(
                "/view/FolderItem.fxml",
                FolderItemController.class,
                newFolder,
                (controller, item) -> controller.setData(item.get("id"), item.get("title"), () -> {
                    openFolder(item.get("id"));
                })
            );

            folderContainer.getChildren().add(itemNode);
        } catch (IOException e) {
            System.err.println("Error loading FolderItem.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openFolder(String folderId) {
        // For now, just log. Later: navigate to a FolderView scene, or filter docs by folderId.
        System.out.println(">> openFolder called for id=" + folderId);
        // TODO: changeScene("/view/FolderView.fxml", ... ) or load folder contents into a container
    }
}
