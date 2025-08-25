package controllers.Notebookeditor;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PageController {

    @FXML
    private Canvas drawingCanvas;

    @FXML
    private Pane pageRoot;

    private boolean erassing = false;

    private double penStrock = 5;
    private double eraserStrock = 3;
    private GraphicsContext gc;

    // Which type of page is this? (grid, dotted, ruled, plain)
    private String pageType = "grid";

    private static PageController pageController;

    public static PageController getpageController(){
        return pageController;
    }
    public static void setPageController(PageController controller){
        pageController = controller;
    }

    @FXML
    public void initialize() {
        drawingCanvas.widthProperty().bind(pageRoot.widthProperty());
        drawingCanvas.heightProperty().bind(pageRoot.heightProperty());

        gc = drawingCanvas.getGraphicsContext2D();
        setupDrawing();
        setPageType(pageType);

        setPageController(this);
    }

    public void setPageType(String type) {
        this.pageType = type.toLowerCase();
        drawBackground();

    }

    public void setTool(String tool){
        if ("pen".equalsIgnoreCase(tool)) {
            erassing = false;
        } else if ("eraser".equalsIgnoreCase(tool)) {
            erassing = true;
        }
    }

    public void setPenSize(double size) {
        this.penStrock = size;
    }

    public void setEraserSize(double size) {
        this.eraserStrock = size;
    }

    @SuppressWarnings("unused")
    public void setupDrawing() {
        drawingCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            gc.beginPath();
            gc.moveTo(e.getX(), e.getY());
            gc.stroke();
        });

        drawingCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (erassing) {
                gc.clearRect(e.getX() - eraserStrock / 2, e.getY() - eraserStrock / 2, eraserStrock, eraserStrock);
            } else {
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(penStrock);
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
        });

        drawingCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            gc.closePath();
        });
    }

    private void drawBackground() {
        switch (pageType) {
            case "grid" -> drawGrid(drawingCanvas, 20);
            case "dotted" -> drawDotted(drawingCanvas, 20);
            case "ruled" -> drawRuled(drawingCanvas, 25);
            case "plain" -> drawPlain(drawingCanvas);
        }
    }

    private void drawGrid(Canvas canvas, int size) {
        var gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);

        for (int x = 0; x < canvas.getWidth(); x += size) {
            gc.strokeLine(x, 0, x, canvas.getHeight());
        }
        for (int y = 0; y < canvas.getHeight(); y += size) {
            gc.strokeLine(0, y, canvas.getWidth(), y);
        }
    }

    private void drawDotted(Canvas canvas, int spacing) {
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GRAY);

        for (int x = 0; x < canvas.getWidth(); x += spacing) {
            for (int y = 0; y < canvas.getHeight(); y += spacing) {
                gc.fillOval(x, y, 1.5, 1.5);
            }
        }
    }

    private void drawRuled(Canvas canvas, int lineSpacing) {
        var gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.LIGHTBLUE);
        gc.setLineWidth(0.7);

        for (int y = lineSpacing; y < canvas.getHeight(); y += lineSpacing) {
            gc.strokeLine(0, y, canvas.getWidth(), y);
        }
    }

    private void drawPlain(Canvas canvas) {
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
