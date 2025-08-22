package controllers.HomeControllers.Notebookeditor;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DrawingController {
    @FXML private Canvas drawingCanvas;
    @FXML private VBox rightSidebar;
    @FXML private VBox aiPanel;
    @FXML private Button aiToggleButton;
    @FXML private Slider sizeSlider;
    @FXML private ColorPicker colorPicker;
    @FXML private ListView<String> premiumList;
    
    private GraphicsContext gc;
    private boolean isAIPanelVisible = true;
    
    public void initialize() {
        // Setup canvas
        gc = drawingCanvas.getGraphicsContext2D();
        gc.setLineWidth(sizeSlider.getValue());
        gc.setStroke(colorPicker.getValue());
        
        // Setup listeners
        sizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> 
            gc.setLineWidth(newVal.doubleValue()));
            
        colorPicker.valueProperty().addListener((obs, oldVal, newVal) -> 
            gc.setStroke(newVal));
            
        // Populate premium list
        premiumList.getItems().addAll("Process", "Zim", "Emma", "SufixJ", "Product → Class");
        
        // Setup drawing canvas
        setupCanvas();
    }
    
    private void setupCanvas() {
        drawingCanvas.setOnMousePressed(e -> {
            gc.beginPath();
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });
        
        drawingCanvas.setOnMouseDragged(e -> {
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });
    }
    
    @FXML
    private void toggleAIPanel() {
        isAIPanelVisible = !isAIPanelVisible;
        aiPanel.setVisible(isAIPanelVisible);
        aiPanel.setManaged(isAIPanelVisible);
        aiToggleButton.setText(isAIPanelVisible ? "AI Prediction ▼" : "AI Prediction ▲");
    }
    
    // Tool handlers
    @FXML
    private void handlePen() {
        gc.setStroke(colorPicker.getValue());
    }
    
    @FXML
    private void handleErase() {
        gc.setStroke(Color.WHITE);
    }
    
    @FXML
    private void handleSelection() {
        // Selection tool implementation
    }
    
    @FXML
    private void handleUndo() {
        // Undo implementation
    }
    
    @FXML
    private void handleRedo() {
        // Redo implementation
    }
    
    @FXML
    private void handleClear() {
        gc.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
    }
    
    // Mouse event handlers
    @FXML
    private void handleMousePressed(javafx.scene.input.MouseEvent event) {
        gc.beginPath();
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();
    }
    
    @FXML
    private void handleMouseDragged(javafx.scene.input.MouseEvent event) {
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();
    }
    
    @FXML
    private void handleMouseReleased(javafx.scene.input.MouseEvent event) {
        gc.closePath();
    }
}