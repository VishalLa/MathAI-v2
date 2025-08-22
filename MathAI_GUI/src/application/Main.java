package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.StorageManager;

import java.io.IOException;

public class Main extends Application {

    /**
     * @param primaryStage The primary stage for this application, onto which
     * the application scene can be set. The stage is created
     * by the platform.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            StorageManager.initialize();

            // Load the FXML file for the main application view.
            Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
            
            // Create a new scene with the loaded root node.
            Scene scene = new Scene(root);

            // Set the title for the main window.
            primaryStage.setTitle("MathAI");
            
            // Set the scene on the primary stage.
            primaryStage.setScene(scene);
            
            // Display the stage.
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
