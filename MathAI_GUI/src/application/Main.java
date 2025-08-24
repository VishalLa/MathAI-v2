package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.StorageManager;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            StorageManager.initialize();

            Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
            
            Scene scene = new Scene(root);

            primaryStage.setTitle("MathAI");
            
            primaryStage.setScene(scene);
            
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
