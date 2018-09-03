import eventsHandler.MainHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.regex.Pattern;

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
        Label chooser = new Label("Project directory: ");
        buttonChooser = new Button("Select Intellij Project");

        HBox paneChooser = new HBox(15, chooser, buttonChooser);

        // Create the info label with the project name
        Label lblSelected = new Label("Project selected: ");
        Label lblChoosen = new Label();
        HBox paneSelected = new HBox(50, lblSelected, lblChoosen);

        Label lblCommit = new Label("Commit message: ");
        lblCommit.setPrefWidth(50);
        txtCommitSentence.setPrefColumnCount(20);
        txtCommitSentence.setPromptText("Enter commit text");
        txtCommitSentence.setMaxWidth(Double.MAX_VALUE);
        HBox paneCommit = new HBox(40, lblCommit, txtCommitSentence);

        // Create the version control selectors
        Label lblGithub = new Label("SCV selector:");
        chkBitbucket = new CheckBox("Bitbucket");
        chkGithub = new CheckBox("Github");

        //VBox paneGit = new VBox(10, lblGithub, chkGithub, chkBitbucket);
        HBox paneGit = new HBox(60, lblGithub, chkGithub, chkBitbucket);

        // Create data pane
        VBox paneData = new VBox(20, paneChooser, paneSelected, paneCommit, paneGit);
        VBox paneCenter = new VBox(20, paneData);
        paneCenter.setPadding(new Insets(0,10, 0, 10));

        // Create the bottom pane to confirm
        ProgressBar pb = new ProgressBar(0);
        Button btnOK = new Button("OK");
        btnOK.setPrefWidth(80);

        Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(80);
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
        primaryStage.setResizable(false);
        primaryStage.show();

        // EVENTS
        buttonChooser.setOnAction(event -> {
            File file = directoryChooser.showDialog(stage);
            if (file != null) {
                projectPath = file.toString();
                String separador = Pattern.quote("\\");
                String [] projectName = projectPath.split(separador);
                lblChoosen.setText(projectName[projectName.length-1]);
            }
        });

        btnOK.setOnAction(e -> handler.btnOK_Click(pb, paneBottom, paneMain, primaryStage, projectPath, txtCommitSentence, chkGithub, chkBitbucket) );

        btnCancel.setOnAction(e -> handler.btnCancel_Click() );
    }
}
