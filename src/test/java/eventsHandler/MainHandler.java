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
import services.DirectorySelector;
import services.errors.ErrorLoginScene;
import services.Global;
import services.PasswordDialog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class MainHandler {


    public MainHandler() {
        super();
    }

    public static void openRepositoryAndSetTreeView(TreeView<String> folder, Stage primaryStage) {
        DirectorySelector.execute(folder, primaryStage);
    }



    public static void seeFile(TreeView<String> folder, TextArea print) {
        String[] aux = folder.getSelectionModel().getSelectedItem().toString().split(":");
        String auxiliarPath = Global.filePath + "\\" + aux[1].substring(1, aux[1].length() - 2);
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(auxiliarPath)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(data);
    }

    public static void addAndCommit(TextArea logConsole, TextArea commitMessage, TreeView<String> tree, Stage stage) {
        if (Global.registered) {
            if (Global.username.equals("")) {
                if (new File("sergi2312@gmail.com.credentials").exists()) {
                    File credentials = new File("sergi2312@gmail.com.credentials");
                    try {
                        Scanner in = new Scanner(credentials);
                        Global.username = in.nextLine();
                        Global.password = in.nextLine();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!Global.repoOpen) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Repository not selected!");
                alert.setContentText("You must select one repository to perform the actions.");
                alert.showAndWait();
                DirectorySelector.execute(tree, stage);
            } else {
                try {
                    Global.git = Git.open(new File(Global.repoDir));
                    Global.git.add().addFilepattern("*");
                    Global.filesAdded = true;
                    CommitCommand commmit =Global.git.commit();
                    commmit.setMessage(commitMessage.getText()).call();
                    logConsole.setText("Add files and commit, ready to push!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }else {
            MainHandler.switchToErrorLoginScene();
        }

    }

    public static void push(TextArea logConsole, TextArea commitMessage) {
        if (Global.filesAdded) {
            try {
                PushCommand pushCommand = Global.git.push();
                pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(Global.username, Global.password));
                pushCommand.call();
                commitMessage.clear();
                logConsole.setText("Push executed succesfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Global.filesAdded = false;
    }

    public static void switchToCredentialsScene() {
        PasswordDialog.execute();
    }

    public static void switchToErrorLoginScene () {
        ErrorLoginScene.execute();
        PasswordDialog.execute();
    }


    public void btnOK_Click(ProgressBar pb, HBox bPane, BorderPane mPane, Stage mainStage, String projectPath, TextField txtCommitSentence, CheckBox chkGithub, CheckBox chkBitbucket) {

        /*Runnable thread = new UpdatingThread(pb, bPane, mPane, mainStage);
        new Thread(thread).start();*/

        String[] credentials = getCredentials();
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
            if (chkBitbucket.isSelected()) {
                System.out.println("Not implemented yet");
            }
            if (chkBitbucket.isSelected() & chkGithub.isSelected()) {
                System.out.println("Not implemented yet");
            }

            /*((UpdatingThread) thread).stopThread();*/

        }

    }

    public void btnCancel_Click() {
        System.exit(0);
    }

    public String[] getCredentials() {
        String[] aux = new String[2];
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
