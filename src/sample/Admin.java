package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Eldin on 26-3-2015.
 */
public class Admin extends Application{
    public static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        primaryStage.setTitle("Admin");
        primaryStage.setScene(new Scene(root, 968, 720));
        primaryStage.show();
    }
    public void logOut(){
        stage.close();
    }
}
