package services.errors;

import javafx.scene.control.Alert;

public class ErrorLoginScene {
    public static void execute () {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login error");
        alert.setHeaderText("Authentication error");
        alert.setContentText("You must be login in github to perform operations");
        alert.showAndWait();
    }
}
