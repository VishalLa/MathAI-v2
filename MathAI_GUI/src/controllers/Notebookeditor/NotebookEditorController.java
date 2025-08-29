package controllers.Notebookeditor;

import java.io.IOException;

import controllers.Controllerabstract;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NotebookEditorController extends Controllerabstract {

    @FXML private AnchorPane notebookEditor;
    @FXML private VBox pagesContainer;
    @FXML private Button penButton;
    @FXML private Button eraserButton;
    @FXML private Button selectButton;
    @FXML private Button clearButton;
    @FXML private Button goHomeButton;
    @FXML private Button aiPredictButton;
    @FXML private Button colorbutton;
    @FXML private Button undoButton;
    @FXML private Button redoButton;
    @FXML private Button addPageButton;
    @FXML private Slider penSizeSlider;
    @FXML private Slider eraserSizeSlider;

    @FXML private ScrollPane scrollpane;

    private String template = "Plain";

    private PageController PageController;
    private GraphicsContext gc;

    private double zoomFactor = 1.0;
    private double translateX = 0;
    private double translateY = 0;
    private double startX, startY;
    private Color pencolor;


    @SuppressWarnings("unused")
    @FXML 
    public void initialize() {
        try {
            selectButton(penButton);

            goHomeButton.setOnAction(event -> {
                try{
                    changeScene("/view/Home.fxml", event);
                } catch (IOException e) {
                    System.err.println("Error loading Home.fxml when going back: " + e.getMessage());
                }
            });

            penButton.setOnAction(e -> {
                selectButton(penButton);
                PageController page = controllers.Notebookeditor.PageController.getpageController();
                if (page != null) {
                    page.setTool("pen");
                }
            });

            eraserButton.setOnAction(e -> {
                selectButton(eraserButton);
                PageController page = controllers.Notebookeditor.PageController.getpageController();
                if (page != null) {
                    page.setTool("eraser");
                }
            });

            selectButton.setOnAction(e -> {
                selectButton(selectButton);
                PageController page = controllers.Notebookeditor.PageController.getpageController();
                if (page != null) {
                    page.setTool("select");
                }
            });

            ColorPicker colorPicker = new ColorPicker(Color.BLACK);
            colorPicker.setVisible(false);
            notebookEditor.getChildren().add(colorPicker);

            colorbutton.setOnAction(e -> {
                colorPicker.show();
            });

            colorPicker.setOnAction(e -> {
                pencolor = colorPicker.getValue();
                colorbutton.setStyle("-fx-background-color: " + toWeb(pencolor));
                PageController page = controllers.Notebookeditor.PageController.getpageController();
                if (page != null) {
                    page.setPenColor(pencolor);
                }
            });

            clearButton.setOnAction(e -> {
                PageController page = controllers.Notebookeditor.PageController.getpageController();
                if (page != null) {
                    page.clearPage();
                }
            });

            undoButton.setOnAction(e -> {
                PageController page = controllers.Notebookeditor.PageController.getpageController();
                if (page != null) page.undo();
            });

            redoButton.setOnAction(e -> {
                PageController page = controllers.Notebookeditor.PageController.getpageController();
                if (page != null) page.redo();
            });

            penSizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                PageController page = controllers.Notebookeditor.PageController.getpageController();
                if (page != null) {
                    page.setPenSize(newVal.doubleValue());
                }
            });

            eraserSizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                PageController page = controllers.Notebookeditor.PageController.getpageController();
                if (page != null) {
                    page.setEraserSize(newVal.doubleValue());
                }
            });

            addPageButton.setOnAction(event -> {
                try {
                    pagesContainer.getChildren().add(loadPage("/view/Page.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            pagesContainer.getChildren().add(loadPage("/view/Page.fxml"));

            handleZoomMovement();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toWeb(Color c) {
        return String.format("#%02X%02X%02X",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
    }

    private void changePenColor() {
        ColorPicker colorPicker = new ColorPicker(Color.RED);
        colorPicker.setVisible(false);
    }

    private void handleZoomMovement() {
        // Wrapper groups for clean zoom + pan separation
        Group contentGroup = new Group(pagesContainer);
        Group zoomGroup = new Group(contentGroup);
        scrollpane.setContent(zoomGroup);

        // Mouse pressed - record starting drag pos
        zoomGroup.setOnMousePressed(event -> {
            if (event.isMiddleButtonDown()) {
                startX = event.getSceneX();
                startY = event.getSceneY();
            }
        });

        // Mouse dragged - compute delta in scene coords
        zoomGroup.setOnMouseDragged(event -> {
            if (event.isMiddleButtonDown()) {
                double dx = event.getSceneX() - startX;
                double dy = event.getSceneY() - startY;

                // apply translation to the OUTER group
                zoomGroup.setTranslateX(zoomGroup.getTranslateX() + dx);
                zoomGroup.setTranslateY(zoomGroup.getTranslateY() + dy);

                // update for next step
                startX = event.getSceneX();
                startY = event.getSceneY();
            }
        });

        // Zoom with Ctrl + Scroll
        notebookEditor.setOnScroll(this::handleZoom);
    }
    
    private void handleZoom(ScrollEvent event) {
        if (event.isControlDown()) {
            double zoomDelta = event.getDeltaY() > 0 ? 1.1 : 0.9;
            zoomFactor *= zoomDelta;

            double pivotX = pagesContainer.getWidth() / 2;
            double pivotY = pagesContainer.getHeight() / 2;
            
            Scale scale = new Scale(zoomFactor, zoomFactor, pivotX, pivotY);
            pagesContainer.getTransforms().setAll(scale);

            event.consume();
        }
    }

    @SuppressWarnings("unused")
    @FXML
    private void openSettings(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PageSettings.fxml"));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(root));

            popupStage.initStyle(StageStyle.UNDECORATED);

            popupStage.setResizable(false);
            popupStage.setWidth(250);
            popupStage.setHeight(500);

            Stage mainStage = (Stage) notebookEditor.getScene().getWindow();
            popupStage.setX(mainStage.getX() + mainStage.getWidth() - popupStage.getWidth() - 90);
            popupStage.setY(mainStage.getY() + 85);
            popupStage.initOwner(mainStage);

            popupStage.initModality(Modality.NONE);

            // to exit the setting window
            popupStage.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
                if (!isFocused) {
                    popupStage.close();
                }
            });

            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private enum TemplatesType {
        PLAIN, GRID, DOTTED, RULED
    }

    private Node loadPage(String fxmlPath) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Node node = loader.load();
        PageController = loader.getController();
        return node;
    }

    private void addPage(TemplatesType template){
        try {

            Node page = loadPage("/view/Page.fxml");
            pagesContainer.getChildren().add(page);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
