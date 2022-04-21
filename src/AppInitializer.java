import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/New Page.fxml"))));
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Fruit Market System (Version 1.0.2 )");
        primaryStage.show();
    }

}
