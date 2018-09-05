import eventsHandler.MainHandler;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.regex.Pattern;

public class InitialPage extends Application {

    public static void main (String [] args) {
        launch(args);
    }

    // Variables
    private Stage stage;
    private MainHandler handler = new MainHandler();

    // Variables and objects
    private MenuBar upperMenuBar = new MenuBar();
    private Menu toolsMenu = new Menu("Tools");
    private Menu settingsMenu = new Menu("Settings");
    private Menu helpMenu = new Menu("Help");
    private MenuItem openRepo = new MenuItem("Open repository");
    private MenuItem createRepository = new MenuItem("Create repository");

    private Label repositoryName = new Label("");
    private Label repoFiles = new Label("Repository Files: ");
    TreeView<String> folder = new TreeView<>();

    private Button pushButton = new Button("Push");
    private Button pullButton = new Button("Pull");
    private Button pullRequestButton = new Button("PullR");
    private Button branchButton = new Button("Branch");
    private Button mergeButton = new Button("Merge");
    private Button fetchButton = new Button("Fetch");
    private Button commitButton = new Button("Add and Commit");
    private Button seeFileButton = new Button("See File");

    private TextArea commitMessage = new TextArea();
    private TextArea logConsole = new TextArea();

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        double buttonHeight = 100;
        double buttonWidth = 30;
        pullButton.setPrefSize(buttonHeight, buttonWidth);
        pullRequestButton.setPrefSize(buttonHeight, buttonWidth);
        pushButton.setPrefSize(buttonHeight, buttonWidth);
        mergeButton.setPrefSize(buttonHeight, buttonWidth);
        branchButton.setPrefSize(buttonHeight, buttonWidth);
        fetchButton.setPrefSize(buttonHeight, buttonWidth);

        folder.setPrefSize(200, 20);
        commitMessage.setPrefSize(100, 50);
        logConsole.setPrefHeight(160);

        upperMenuBar.setPrefWidth(600);

        // Create the menu bar and add all the components
        upperMenuBar.getMenus().add(toolsMenu);
        upperMenuBar.getMenus().add(settingsMenu);
        upperMenuBar.getMenus().add(helpMenu);

        toolsMenu.getItems().add(openRepo);
        toolsMenu.getItems().add(createRepository);

        HBox topPane = new HBox(upperMenuBar);
        BorderPane mainPanel = new BorderPane();
        mainPanel.setTop(topPane);

        repositoryName.setText("Default");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(repositoryName, 0, 0);
        grid.setColumnSpan(repositoryName, 3);
        grid.setHalignment(repositoryName, HPos.CENTER);
        grid.add(pullButton, 0, 1);
        grid.add(pullRequestButton, 1, 1);
        grid.add(pushButton, 2, 1);
        grid.add(mergeButton, 0, 2);
        grid.add(fetchButton, 1, 2);
        grid.add(branchButton, 2, 2);
        grid.add(commitMessage, 0, 3);
        grid.setColumnSpan(commitMessage, 3);
        grid.add(commitButton, 0, 4);
        grid.add(seeFileButton, 2, 4);
        grid.setHalignment(seeFileButton, HPos.RIGHT);
        grid.add(logConsole, 0, 5);
        grid.setColumnSpan(logConsole, 6);
        grid.add(repoFiles, 3, 0);
        grid.setColumnSpan(repoFiles, 3);
        grid.setHalignment(repoFiles, HPos.CENTER);
        grid.add(folder, 3, 1, 1, 4);
        mainPanel.setCenter(grid);

        // events
        openRepo.setOnAction(e -> MainHandler.openRepositoryAndSetTreeView(folder, primaryStage));
        seeFileButton.setOnAction(e -> MainHandler.seeFile(folder, logConsole));

        Scene scene = new Scene(mainPanel);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Git Gui");
        primaryStage.setResizable(true);
        primaryStage.show();

    }
}
