package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    public static Stage parentWindow;


    @Override
    public void start(Stage primaryStage) throws Exception {
        parentWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    public void test(){
        parentWindow.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
