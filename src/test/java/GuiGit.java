import eventsHandler.MainHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class GuiGit extends Application {

    public static void main (String [] args) {
        launch(args);
    }

    // Variables
    private Stage stage;
    private MainHandler handler = new MainHandler();
    String projectPath;

    // File chooser
    DirectoryChooser directoryChooser = new DirectoryChooser();
    Button buttonChooser;

    // names project and commit sentence fields
    final TextField txtProjectName = new TextField();
    final TextField txtCommitSentence = new TextField();

    // checkbox to select github, bitbucket or both
    CheckBox chkGithub;
    CheckBox chkBitbucket;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        // Create the top name
        Text textHeading = new Text("Git");
        textHeading.setFont(new Font(20));
        HBox paneTop = new HBox(textHeading);
        paneTop.setPadding(new Insets(20, 10,20,10));

        // Create the data fields
        Label chooser = new Label("Project directory:");
        buttonChooser = new Button("Select Intellij Project");
        buttonChooser.setOnAction(event -> {
            File file = directoryChooser.showDialog(stage);
            if (file != null)
                projectPath = file.toString();
        });
        HBox paneChooser = new HBox(15, chooser, buttonChooser);

        Label lblCommit = new Label("Commit text: ");
        lblCommit.setPrefWidth(100);
        txtCommitSentence.setPrefColumnCount(20);
        txtCommitSentence.setPromptText("Enter the sentence to commit the files");
        txtCommitSentence.setMaxWidth(Double.MAX_VALUE);
        HBox paneCommit = new HBox(lblCommit, txtCommitSentence);

        // Create the version control selectors
        Label lblGithub = new Label("SCV selector:");
        chkBitbucket = new CheckBox("Bitbucket");
        chkGithub = new CheckBox("Github");
        //VBox paneGit = new VBox(10, lblGithub, chkGithub, chkBitbucket);
        HBox paneGit = new HBox(60, lblGithub, chkGithub, chkBitbucket);

        // Create data pane
        VBox paneData = new VBox(10, paneChooser, paneCommit, paneGit);
        VBox paneCenter = new VBox(20, paneData);
        paneCenter.setPadding(new Insets(0,10, 0, 10));

        // Create the bottom pane to confirm
        Button btnOK = new Button("OK");
        btnOK.setPrefWidth(80);
        btnOK.setOnAction(e -> handler.btnOK_Click(projectPath, txtCommitSentence, chkGithub, chkBitbucket) );

        Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(80);
        btnCancel.setOnAction(e -> handler.btnCancel_Click() );
        Region spacer = new Region();

        HBox paneBottom = new HBox(10, spacer, btnOK, btnCancel);
        paneBottom.setHgrow(spacer, Priority.ALWAYS);
        paneBottom.setPadding(new Insets(50, 10, 20, 10));

        // Finish the scene
        BorderPane paneMain = new BorderPane();
        paneMain.setTop(paneTop);
        paneMain.setCenter(paneCenter);
        paneMain.setBottom(paneBottom);

        Scene scene = new Scene(paneMain);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Git custom");
        primaryStage.show();
    }
}
