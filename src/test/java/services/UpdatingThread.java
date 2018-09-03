package services;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class UpdatingThread  implements Runnable{
    public static boolean CONTINUE = true;
    public ProgressBar progressBar;
    public HBox bPane;
    public BorderPane mPane;
    public Stage mainStage;

    public UpdatingThread(ProgressBar pb, HBox bPane, BorderPane mPane, Stage mainStage) {
        this.progressBar = pb;
        this.bPane = bPane;
        this.mPane = mPane;
        this.mainStage = mainStage;
    }

    public void stopThread () {
        CONTINUE = false;
    }

    @Override
    public void run() {
        System.out.println(CONTINUE);
        float p = 0f;
        try {
            while (CONTINUE) {
                progressBar.setProgress(p+=0.1);
                mainStage.show();
                Thread.sleep(10);
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
