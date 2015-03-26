package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;

public class ControllerAnalyst {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label welcome;
    @FXML
    private Button cnt;
    @FXML
    private Tab newtab;
    @FXML
    private ImageView logOut;

    @FXML
    void initialize() {
        assert welcome != null : "fx:id=\"welcome\" was not injected: check your FXML file 'Analyst.fxml'.";
        logOut();
    }
    private void logOut(){
        logOut.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int reply = JOptionPane.showConfirmDialog(null, "Do you want to log out?");
                if (reply == JOptionPane.YES_OPTION)
                {
                    UserInterface u = new UserInterface();
                    u.logOut();
                    Main m = new Main();
                    try {
                        m.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}