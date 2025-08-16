package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Correctly load the FXML file using an FXMLLoader instance.
            // This ensures the controller is properly linked.
            URL fxmlUrl = getClass().getResource("../view/Trash.fxml");
            if (fxmlUrl == null) {
                System.err.println("FXML file not found! Check the path.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            // The controller is now automatically created and linked to the FXML.
            // You can get a reference to it if you need to.
            // HomeController controller = loader.getController();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../styles/Homestyle.css").toExternalForm());
            
            primaryStage.setTitle("My Application");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
