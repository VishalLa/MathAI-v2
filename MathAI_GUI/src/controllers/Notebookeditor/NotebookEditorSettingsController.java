package controllers.Notebookeditor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NotebookEditorSettingsController {

    @FXML private Button blankTemplateBtn;
    @FXML private Button linedTemplateBtn;
    @FXML private Button gridTemplateBtn;
    @FXML private Button dottedTemplateBtn;

    @FXML private ImageView blankTemplateImg;
    @FXML private ImageView linedTemplateImg;
    @FXML private ImageView gridTemplateImg;
    @FXML private ImageView dottedTemplateImg;

    @SuppressWarnings("unused")
    @FXML
    public void initialize() {
        // Load template images
        blankTemplateImg.setImage(new Image(getClass().getResource("/templates/blank.png").toExternalForm()));
        linedTemplateImg.setImage(new Image(getClass().getResource("/templates/lined.png").toExternalForm()));
        gridTemplateImg.setImage(new Image(getClass().getResource("/templates/grid.png").toExternalForm()));
        dottedTemplateImg.setImage(new Image(getClass().getResource("/templates/dotted.png").toExternalForm()));

        // Click handlers
        blankTemplateBtn.setOnAction(e -> applyTemplate("Blank"));
        linedTemplateBtn.setOnAction(e -> applyTemplate("Lined"));
        gridTemplateBtn.setOnAction(e -> applyTemplate("Grid"));
        dottedTemplateBtn.setOnAction(e -> applyTemplate("Dotted"));
    }

    private void applyTemplate(String templateName) {
        System.out.println("Template selected: " + templateName);
        // TODO: replace with actual template switching logic
    }
}
