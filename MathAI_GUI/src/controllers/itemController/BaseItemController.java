package controllers.itemController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public abstract class BaseItemController {

    @FXML protected Label titleLabel;
    @FXML protected HBox root;

    protected String itemId;
    protected Runnable onOpen;
    protected boolean favorite;

    @SuppressWarnings("unused")
    public void setData(String id, String title, Runnable onOpenAction) {
        this.itemId = id;
        this.titleLabel.setText(title);
        this.onOpen = onOpenAction;

        // System.out.println("Attach click for " + title + " id=" + id);
        root.setPickOnBounds(true);
        root.setMouseTransparent(false);
        root.setDisable(false);

        root.setOnMouseClicked(event -> {
            // System.out.println("Tile clicked: " + itemId);
            if (onOpen != null) onOpen.run();
        });
    }

    public String getItemId() {
        return itemId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
