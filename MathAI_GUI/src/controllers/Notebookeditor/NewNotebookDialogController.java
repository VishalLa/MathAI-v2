package controllers.Notebookeditor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewNotebookDialogController {

    @FXML private TextField notebookNameField;
    @FXML private Button cancelButton;
    @FXML private Button createButton;
    @FXML private AnchorPane dialogRoot;
    @FXML private ToggleGroup gridTypeGroup;
    @FXML private RadioButton plainRadio;
    @FXML private RadioButton ruledRadio;
    @FXML private RadioButton gridRadio;
    @FXML private RadioButton dottedRadio;

    private Stage dialogStage;
    private boolean created = false;
    private Runnable onCancel;
    private Runnable onCreate;

    // Set stage from parent
    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    @FXML
    public void initialize() {
        // Default selection
        plainRadio.setSelected(true);
    }

    @FXML
    private void handleCancel() {
        created = false;
        if (onCancel != null) onCancel.run();
        if (dialogStage != null) dialogStage.close();
        dialogRoot.setVisible(false);
    }

    @FXML
    private void handleCreate() {
        created = true;

        String name = notebookNameField.getText();
        String gridType = getGridType();
        String pageStyle = "pagey";

        System.out.println("=== Notebook Created ===");
        System.out.println("Name: " + name);
        System.out.println("Grid: " + gridType);
        System.out.println("Page Style: " + pageStyle);

        if (onCreate != null) onCreate.run();
        if (dialogStage != null) dialogStage.close();
        dialogRoot.setVisible(false);
    }

    // Callbacks
    public void setOnCancel(Runnable callback) {
        this.onCancel = callback;
    }

    public void setOnCreate(Runnable callback) {
        this.onCreate = callback;
    }

    // Data getters
    public String getNotebookName() {
        return notebookNameField.getText();
    }

    public String getGridType() {
        RadioButton selected = (RadioButton) gridTypeGroup.getSelectedToggle();
        return selected != null ? selected.getText() : "Plain";
    }

    public String getPageStyle() {
        return "pagey"; // always default
    }

    public boolean isCreated() { 
        return created; 
    }
}
