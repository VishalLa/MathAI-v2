package controllers.Notebookeditor;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

public class PageSettingsController {

    @FXML private Button blankTemplateBtn;
    @FXML private Button ruledTemplateBtn;
    @FXML private Button gridTemplateBtn;
    @FXML private Button dottedTemplateBtn;

    @SuppressWarnings("unused")
    @FXML
    public void initialize() {

        // Click handlers
        blankTemplateBtn.setOnAction(e -> applyTemplate("plain"));
        ruledTemplateBtn.setOnAction(e -> applyTemplate("ruled"));
        gridTemplateBtn.setOnAction(e -> applyTemplate("grid"));
        dottedTemplateBtn.setOnAction(e -> applyTemplate("dotted"));
    }

    private void applyTemplate(String templateName) {
        PageController currentPageController = PageController.getpageController();

        if (currentPageController != null) {
            Canvas currentCanvas = currentPageController.getDrawingCanvas();

            if (currentCanvas != null) {
                System.out.println("Template selected: " + templateName);
                DrawBackground.drawBackground(templateName, currentCanvas);
            } else {
                System.err.println("Drawing canvas not found.");
            }
        } else {
            System.err.println("PageController instance not set.");
        }
    }
}
