package eventsHandler;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainHandler {
    private static String filePath;

    public MainHandler () {
        super();
    }

    public static void openRepositoryAndSetTreeView(TreeView<String> folder, Stage primaryStage) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(System.getProperty("user.home")));
        File choice = dc.showDialog(primaryStage);
        if(choice == null || ! choice.isDirectory()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Could not open directory");
            alert.setContentText("The file is invalid.");

            alert.showAndWait();
        } else {
            MainHandler.filePath = choice.toString();
            folder.setRoot(getNodesForDirectory(choice));
        }

    }

    public static TreeItem<String> getNodesForDirectory(File directory) { //Returns a TreeItem representation of the specified directory
        TreeItem<String> root = new TreeItem<String>(directory.getName());
        for(File f : directory.listFiles()) {
            System.out.println("Loading " + f.getName());
            if(f.isDirectory()) { //Then we call the function recursively
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<String>(f.getName()));
            }
        }
        return root;
    }

    public static void seeFile (TreeView<String> folder, TextArea print) {
        String [] aux = folder.getSelectionModel().getSelectedItem().toString().split(":");
        MainHandler.filePath = MainHandler.filePath + "\\" + aux[1].substring(1, aux[1].length()-2);
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(MainHandler.filePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(data);


    }


    public void btnOK_Click(ProgressBar pb, HBox bPane, BorderPane mPane, Stage mainStage, String projectPath, TextField txtCommitSentence, CheckBox chkGithub, CheckBox chkBitbucket) {

        /*Runnable thread = new UpdatingThread(pb, bPane, mPane, mainStage);
        new Thread(thread).start();*/

        String [] credentials = getCredentials();
        String gitProject = projectPath + "\\.git";
        File repoDir = new File(gitProject);
        if (!chkGithub.isSelected() & !chkBitbucket.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Check de SCV");
            alert.setContentText("You must select at least one SCV");

            alert.showAndWait();
        } else {
            if (chkGithub.isSelected()) {
                try {
                    Git git = Git.open(repoDir);
                    git.add().addFilepattern("*");
                    CommitCommand commmit = git.commit();
                    commmit.setMessage(txtCommitSentence.getText()).call();
                    PushCommand pushCommand = git.push();
                    pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(credentials[0], credentials[1]));
                    pushCommand.call();
                    System.out.println("pushing");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (chkBitbucket.isSelected()) { System.out.println("Not implemented yet");}
            if (chkBitbucket.isSelected() & chkGithub.isSelected()) {System.out.println("Not implemented yet"); }

            /*((UpdatingThread) thread).stopThread();*/
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upload Successful");
            alert.setHeaderText(null);
            alert.setContentText("Github repository updated successfully!");

            alert.showAndWait();
        }

    }

    public void btnCancel_Click() {
        System.exit(0);
    }
    public String [] getCredentials () {
        String [] aux = new String[2];
        try {
            BufferedReader file = new BufferedReader(new FileReader("github.credentials"));
            aux[0] = file.readLine();
            aux[1] = file.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aux;
    }

}
