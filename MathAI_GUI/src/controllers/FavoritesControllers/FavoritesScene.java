package controllers.FavoritesControllers;

import java.io.IOException;

import controllers.Controllerabstract;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class FavoritesScene extends Controllerabstract {

    @FXML private AnchorPane favoritesPage;

    @FXML
    public void initialize() {
        if (favoritesPage == null) {
            System.err.println("favoritesPage is not injected! Check fx:id in Home.fxml.");
        } else{
            selectButton(favbutton);
            setupThemeBinding(favoritesPage);

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
                    changeScene("/view/Notebook.fxml", event);
                } catch (IOException e) {
                    System.err.println("Error loading Notebooks.fxml: " + e.getMessage());
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
            
            selectButton(favbutton);
        }
    }
}