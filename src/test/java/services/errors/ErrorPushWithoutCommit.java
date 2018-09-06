package services.errors;

import javafx.scene.control.Alert;

public class ErrorPushWithoutCommit {
    public static void execute () {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Git commands error");
        alert.setHeaderText("Push without commit");
        alert.setContentText("You must add files to the stage area and commit them in order to push files to the repository.");
        alert.showAndWait();
    }
}
