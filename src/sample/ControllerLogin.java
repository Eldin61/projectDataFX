package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {

    @FXML
    private TextField fUser;

    @FXML
    private PasswordField fPass;

    @FXML
    private AnchorPane logo;

    @FXML
    private Button btnlogin;

    @FXML
    private AnchorPane content;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login();
    }
    private void login(){
        btnlogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = fUser.getText();
                String password = fPass.getText();
                SQLconnector s = new SQLconnector();
                try {
                    if(s.checkCred(username, password)){
                        Main m = new Main();
                        m.test();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
