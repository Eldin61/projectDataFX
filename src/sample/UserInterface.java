package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by Eldin on 26-3-2015.
 */
public class UserInterface extends Application{
    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Analyst.fxml"));
        primaryStage.setTitle("Analyst");
        primaryStage.setScene(new Scene(root, 1096, 802));
        primaryStage.setResizable(false);
        primaryStage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });
    }
    public void logOut(){
        stage.close();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
