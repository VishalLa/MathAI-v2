package utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ThemeManager {
    // Global dark mode property
    private static final BooleanProperty darkMode = new SimpleBooleanProperty(false);

    public static BooleanProperty darkModeProperty() {
        return darkMode;
    }

    public static boolean isDarkMode() {
        return darkMode.get();
    }

    public static void setDarkMode(boolean enabled) {
        darkMode.set(enabled);
    }
}
