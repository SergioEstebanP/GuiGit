package services;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.regex.Pattern;


public class DirectorySelector {
    public static void execute(TreeView<String> folder, Stage primaryStage, Label name) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(System.getProperty("user.home")+"\\Documents\\"));
        File choice = dc.showDialog(primaryStage);
        if (choice == null || !choice.isDirectory()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Could not open directory");
            alert.setContentText("The file is invalid.");

            alert.showAndWait();
        } else {
            Global.filePath = choice.toString();
            Global.repoDir = Global.filePath+"\\.git";
            folder.setRoot(getNodesForDirectory(choice));
            Global.repoOpen = true;
            Global.repoName = getReponame(Global.filePath);
            name.setText(Global.repoName);
        }
    }

    private static String getReponame(String filePath) {
        String [] aux = filePath.split(Pattern.quote("\\"));
        return aux[aux.length-1];
    }

    public static TreeItem<String> getNodesForDirectory(File directory) { //Returns a TreeItem representation of the specified directory
        TreeItem<String> root = new TreeItem<String>(directory.getName());
        for (File f : directory.listFiles()) {
            if (f.isDirectory()) { //Then we call the function recursively
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<String>(f.getName()));
            }
        }
        return root;
    }
}
