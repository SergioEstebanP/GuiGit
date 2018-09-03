package eventsHandler;

import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainHandler {

    public MainHandler () {
        super();
    }

    public void btnOK_Click(String projectPath, TextField txtCommitSentence, CheckBox chkGithub, CheckBox chkBitbucket) {

        String [] credentials = getCredentials();
        String gitProject = projectPath + "\\.git";
        File repoDir = new File(gitProject);
        if (chkGithub.isSelected()) {
            try {
                Git git = Git.open(repoDir);
                git.add().addFilepattern("*");
                CommitCommand commmit = git.commit();
                commmit.setMessage(txtCommitSentence.getText()).call();
                PushCommand pushCommand = git.push();
                pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(credentials[0], credentials[1]));
                pushCommand.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (chkBitbucket.isSelected()) { System.out.println("Not implemented yet");}
        if (chkBitbucket.isSelected() & chkGithub.isSelected()) {System.out.println("Not implemented yet"); }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upload Successful");
        alert.setHeaderText(null);
        alert.setContentText("Github repository updated successfully!");

        alert.showAndWait();
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
