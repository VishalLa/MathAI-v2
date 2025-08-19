package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TrashController extends Controllerabstract {

    @FXML private AnchorPane trashPage;

    @FXML
    public void initialize() {
        if (trashPage == null) {
            System.err.println("favoritesPage is not injected! Check fx:id in Home.fxml.");
        } else{
            selectButton(trashbutton);
            setupThemeBinding(trashPage);

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
            favbutton.setOnAction(event -> {
                selectButton(trashbutton);
                try {
                    changeScene("/view/Favorites.fxml", event);
                } catch (IOException e) {
                    System.err.println("Error loading Trash.fxml: " + e.getMessage());
                }
            });
            
            selectButton(trashbutton);
        }
    }
}