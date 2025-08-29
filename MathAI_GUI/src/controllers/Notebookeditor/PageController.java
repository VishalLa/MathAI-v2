package controllers.Notebookeditor;


import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PageController {

    @FXML
    private Canvas drawingCanvas;

    @FXML
    private Pane pageRoot;

    private boolean erassing = false;
    private boolean selecting = false;
    
    private double selectStartX, selectStartY;
    private Rectangle selectionRect;

    private double penStrock = 5;
    private double eraserStrock = 3;
    private GraphicsContext gc;
    private Color penColor;

    private final Stack<WritableImage> undoStack = new Stack<>();
    private final Stack<WritableImage> redoStack = new Stack<>();

    private String pageType = "plane";

    private static PageController pageController;

    public static PageController getpageController() {
        return pageController;
    }
    public void setPageController(PageController controller) {
        pageController = controller;
    }
    public Canvas getDrawingCanvas() {
        return this.drawingCanvas;
    }

    @FXML
    public void initialize() {
        gc = drawingCanvas.getGraphicsContext2D();
        setupDrawing();
        setPageType(pageType);
        setPageController(this);
        selectArea();
    }

    public void selectArea() {
        selectionRect = new Rectangle();
        selectionRect.setStroke(Color.BLUE);
        selectionRect.setStrokeWidth(1.5);
        selectionRect.setFill(Color.TRANSPARENT);
        selectionRect.getStrokeDashArray().addAll(5.0, 5.0);
        selectionRect.setVisible(false); // Initially hidden

        if (drawingCanvas.getParent() instanceof Pane) {
            Pane parentPane = (Pane) drawingCanvas.getParent();
            parentPane.getChildren().add(selectionRect);
            selectionRect.toFront();
        }
    }

    private void hideSelectionRect() {
        if (selectionRect != null) {
            selectionRect.setVisible(false);
            selectionRect.setWidth(0);
            selectionRect.setHeight(0);
        }
    }

    public void setPageType(String type) {
        this.pageType = type.toLowerCase();
        DrawBackground.drawBackground(type, drawingCanvas);
    }

    public void setTool(String tool){
        if ("pen".equalsIgnoreCase(tool)) {
            erassing = false;
            selecting = false;
            hideSelectionRect();
        } else if ("eraser".equalsIgnoreCase(tool)) {
            erassing = true;
            selecting = false;
            hideSelectionRect();
        } else if ("select".equalsIgnoreCase(tool)) {
            erassing = false;
            selecting = true;
        }
    }

    public void setPenSize(double size) {
        this.penStrock = size;
    }

    public void setEraserSize(double size) {
        this.eraserStrock = size;
    }

    public void setPenColor(Color penColor) {
        this.penColor = penColor;
    }

    public void clearPage() {
        gc.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
    }

    private void saveState() {
        if (undoStack.size() >= 15) {
            undoStack.remove(0);
        }

        WritableImage snapshot = new WritableImage(
            (int) drawingCanvas.getWidth(),
            (int) drawingCanvas.getHeight()
        );

        drawingCanvas.snapshot(new SnapshotParameters(), snapshot);
        undoStack.push(snapshot);
        redoStack.clear();
    }

    @SuppressWarnings("unused")
    public void setupDrawing() {
        drawingCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            saveState();

            if (selecting) {
                selectStartX = e.getX();
                selectStartY = e.getY();
                selectionRect.setX(selectStartX);
                selectionRect.setY(selectStartY);
                selectionRect.setWidth(0);
                selectionRect.setHeight(0);
                selectionRect.setVisible(true);
            } else {
                hideSelectionRect();
                gc.beginPath();
                gc.moveTo(e.getX(), e.getY());
                gc.stroke();
            }
        });

        drawingCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (selecting) {
                double mouseX = Math.max(0, Math.min(e.getX(), drawingCanvas.getWidth()));
                double mouseY = Math.max(0, Math.min(e.getY(), drawingCanvas.getHeight()));

                double width = Math.abs(mouseX - selectStartX);
                double height = Math.abs(mouseY - selectStartY);

                selectionRect.setX(Math.min(mouseX, selectStartX));
                selectionRect.setY(Math.min(mouseY, selectStartY));
                selectionRect.setWidth(width);
                selectionRect.setHeight(height);
            } else if (erassing) {
                hideSelectionRect();
                gc.clearRect(
                    e.getX() - eraserStrock / 2,
                    e.getY() - eraserStrock / 2,
                    eraserStrock,
                    eraserStrock
                );
            } else {
                hideSelectionRect();
                gc.setStroke(penColor);
                gc.setLineWidth(penStrock);
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
        });

        drawingCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            if (selecting) {
                System.out.println("Selected area: " +
                    selectionRect.getX() + ", " + selectionRect.getY() +
                    " size: " + selectionRect.getWidth() + "x" + selectionRect.getHeight());
                
                WritableImage cropped = snapshotSelection();
                if (cropped != null){
                    System.out.println("Selection snapshot created: "
                    + cropped.getWidth() + "x" + cropped.getHeight());
                    saveSelectedImage(cropped);
                }
            } else {
                gc.closePath();
            }
        });
    }

    private void saveSelectedImage(WritableImage image) {
        if (image == null) {
            System.err.println("Cannot save null image.");
            return;
        }

        File outputDir = new File("selected_images");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        String filename = "selection_" + System.currentTimeMillis() + ".png";
        File outputFile = new File(outputDir, filename);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
            System.out.println("Image saved successfully to: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public WritableImage snapshotSelection() {
        if (!selectionRect.isVisible() || selectionRect.getWidth() <= 0 || selectionRect.getHeight() <= 0) {
            return null;
        }

        WritableImage fullSnapshot = new WritableImage(
            (int) drawingCanvas.getWidth(),
            (int) drawingCanvas.getHeight()
        );
        drawingCanvas.snapshot(null, fullSnapshot);

        PixelReader reader = fullSnapshot.getPixelReader();
        if (reader == null) return null;

        return new WritableImage(
            reader,
            (int) selectionRect.getX(),
            (int) selectionRect.getY(),
            (int) selectionRect.getWidth(),
            (int) selectionRect.getHeight()
        );
    }

    private WritableImage copyCanvas() {
        WritableImage snapshot = new WritableImage((int) drawingCanvas.getWidth(),
            (int) drawingCanvas.getHeight());
        drawingCanvas.snapshot(new SnapshotParameters(), snapshot);
        return snapshot;
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            WritableImage prev = undoStack.pop();
            redoStack.push(copyCanvas());
            gc.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
            gc.drawImage(prev, 0, 0);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            WritableImage next = redoStack.pop();
            undoStack.push(copyCanvas());
            gc.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
            gc.drawImage(next, 0, 0);
        }
    }
}
