package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;

public class ControllerAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passField;

    @FXML
    private RadioButton analystCheck;

    @FXML
    private TextField userField;

    @FXML
    private RadioButton adminCheck;

    @FXML
    private ImageView logOut;

    @FXML
    private TextField familyField;

    @FXML
    private Button btnAddAcc;

    @FXML
    private TextField firstField;

    @FXML
    void initialize() {
        assert passField != null : "fx:id=\"passField\" was not injected: check your FXML file 'Admin.fxml'.";
        assert analystCheck != null : "fx:id=\"analystCheck\" was not injected: check your FXML file 'Admin.fxml'.";
        assert userField != null : "fx:id=\"userField\" was not injected: check your FXML file 'Admin.fxml'.";
        assert adminCheck != null : "fx:id=\"adminCheck\" was not injected: check your FXML file 'Admin.fxml'.";
        assert logOut != null : "fx:id=\"logOut\" was not injected: check your FXML file 'Admin.fxml'.";
        assert familyField != null : "fx:id=\"familyField\" was not injected: check your FXML file 'Admin.fxml'.";
        assert btnAddAcc != null : "fx:id=\"btnAddAcc\" was not injected: check your FXML file 'Admin.fxml'.";
        assert firstField != null : "fx:id=\"firstField\" was not injected: check your FXML file 'Admin.fxml'.";
        checkRadio();
        logOut();
    }
    String authorityLevel;
    String username;
    String password;
    String firstname;
    String familyname;
    private void checkRadio(){
        ToggleGroup group = new ToggleGroup();

        adminCheck.setToggleGroup(group);
        analystCheck.setToggleGroup(group);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                authorityLevel = chk.getText();
            }
        });
        btnAddAcc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SQLconnector s = new SQLconnector();
                username = userField.getText();
                password = passField.getText();
                firstname = firstField.getText();
                familyname = familyField.getText();
                s.addAccount(username, password, firstname, familyname, authorityLevel);
            }
        });
    }
    private void logOut(){
        logOut.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int reply = JOptionPane.showConfirmDialog(null, "Do you want to log out?");
                if (reply == JOptionPane.YES_OPTION)
                {
                    Admin a = new Admin();
                    a.logOut();
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
