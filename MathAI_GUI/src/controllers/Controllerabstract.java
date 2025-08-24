package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import utils.StorageManager;

public abstract class Controllerabstract {

    @FXML protected Button docbutton;
    @FXML protected Button notebutton;
    @FXML protected Button favbutton;
    @FXML protected Button trashbutton;
    @FXML protected TilePane documentContainer;

    protected Button lastSelectedButton;

    protected void selectButton(Button button){
        if (lastSelectedButton != null){
            lastSelectedButton.getStyleClass().remove("selected-button");
        }
        button.getStyleClass().add("selected-button");
        lastSelectedButton = button;
    }

    protected void changeScene(String fxmlPath, ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Save the current state
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

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("⚠️ Failed to load FXML: " + fxmlPath);
        }
    }

    @SuppressWarnings("unused")
    protected void setupThemeBinding(Node rootNode) {
        utils.ThemeManager.darkModeProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                if (!rootNode.getStyleClass().contains("dark")) {
                    rootNode.getStyleClass().add("dark");
                }
            } else {
                rootNode.getStyleClass().remove("dark");
            }
        });

        // Apply current theme immediately
        if (utils.ThemeManager.isDarkMode()) {
            if (!rootNode.getStyleClass().contains("dark")) {
                rootNode.getStyleClass().add("dark");
            }
        }
    }

    protected <T> Node createItemNode(
        String fxmlPath,
        Class<T> controllerClass,
        Map<String, String> item,
        TriConsumer<T, Map<String,String>, Node> setupController
    ) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Node node = loader.load();
        T controller = loader.getController();
        setupController.accept(controller, item, node);
        return node;
    }

    protected <T> void loadItemsFromStorage(
            String type,
            Pane container,
            String fxmlPath,
            Class <T> controllerClass,
            TriConsumer<T, Map<String,String>, Node> setupCallback
    ) throws IOException {
        container.getChildren().clear();

        Map<String, List<Map<String, String>>> index = StorageManager.loadIndex();
        List<Map<String, String>> items = index.get(type);

        if (items == null || items.isEmpty()) return;

        for (Map<String, String> item : items) {
            Node itemNode = createItemNode(fxmlPath, controllerClass, item, setupCallback);
            container.getChildren().add(itemNode);
            System.out.println("Added node: " + itemNode +
                    " size=" + itemNode.prefWidth(-1) + "x" + itemNode.prefHeight(-1));
        }
    }

}
