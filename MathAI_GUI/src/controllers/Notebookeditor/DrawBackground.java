package controllers.Notebookeditor;

import javafx.scene.canvas.Canvas;

public class DrawBackground {

    public static void drawBackground(String pageType, Canvas drawingCanvas) {
        switch (pageType) {
            case "grid" -> drawGrid(drawingCanvas);
            case "dotted" -> drawDotted(drawingCanvas);
            case "ruled" -> drawRuled(drawingCanvas);
            case "plain" -> drawPlain(drawingCanvas);
        }
    }

    private static void drawGrid(Canvas canvas) {
        
    }

    private static void drawDotted(Canvas canvas) {
        
    }

    private static void drawRuled(Canvas canvas) {
        
    }

    private static void drawPlain(Canvas canvas) {
        
    }
}