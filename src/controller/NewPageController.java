package controller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewPageController {
    public JFXProgressBar ProgressBar;
    public AnchorPane newPage;

    public void initialize() {
        new ShowSplashScreen().start();
    }

    class ShowSplashScreen extends Thread {
        public void run() {
            try {
                for (int i = 0; i <= 10; i++) {
                    double x = i * (0.1);
                    ProgressBar.setProgress(x);


                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../view/FirstPageUi.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(NewPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    newPage.getScene().getWindow().hide();
                });
            } catch (Exception ex) {
                Logger.getLogger(NewPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
