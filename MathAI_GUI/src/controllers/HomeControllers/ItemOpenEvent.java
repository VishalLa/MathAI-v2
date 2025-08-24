package controllers.HomeControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemOpenEvent {

    public enum ItemType {
        NOTEBOOK, FOLDER, DOCUMENT
    }

    @SuppressWarnings("unused")
    public static void attachOpenHandler(Node itemNode, ItemType type, String itemId) {
        itemNode.setOnMouseClicked(event -> {
            try {
                switch (type) {
                    case NOTEBOOK -> changeScene("/view/NotebookEditor.fxml", itemNode);
                    case FOLDER -> changeScene("/view/FolderEditor.fxml", itemNode);
                    case DOCUMENT -> changeScene("/view/DocumentEditor.fxml", itemNode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void changeScene(String fxmlPath, Node sourceNode) throws IOException {
        FXMLLoader loader = new FXMLLoader(ItemOpenEvent.class.getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = (Stage) sourceNode.getScene().getWindow();

        // preserve state
        double width = stage.getWidth();
        double height = stage.getHeight();
        double x = stage.getX();
        double y = stage.getY();
        boolean maximized = stage.isMaximized();
        boolean fullScreen = stage.isFullScreen();

        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(root);
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
        }

        stage.setWidth(width);
        stage.setHeight(height);
        stage.setX(x);
        stage.setY(y);
        stage.setMaximized(maximized);
        stage.setFullScreen(fullScreen);

        stage.show();
        System.out.println("Entering the editors");
    }
}
