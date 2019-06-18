package com.ayman.MessagesSystem.View.Register;

import Main.messages;
import Main.Methods;
import com.ayman.MessagesSystem.Code.Dao.UsersDao;
import com.ayman.MessagesSystem.Code.Type.Validation;
import com.ayman.MessagesSystem.Code.VO.userVo;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterFXMLController implements Initializable {

    @FXML
    private JFXTextField usernameTF;
    @FXML
    private JFXPasswordField passwordTF;
    @FXML
    private JFXPasswordField rePasswordTF;
    @FXML
    private JFXTextField emailTF;

    private Stage rootStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rootStage = messages.window;
    }

    @FXML
    private void loadLogin(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/com/ayman/MessagesSystem/View/Login/LoginFXML.fxml"));
        rootStage.setScene(new Scene(root));
        rootStage.show();
    }

    @FXML
    private void register(ActionEvent event) {
        userVo user = new userVo(usernameTF.getText(), passwordTF.getText(), emailTF.getText());
        if (Validation.validation(usernameTF.getText(), passwordTF.getText(), rePasswordTF.getText(), emailTF.getText()) || Validation.validationForPass(passwordTF.getText())) {
            Methods.alertDialog("Fill the blanks! and password length must be > 5", "ALERT", "WARNING");
        } else {
            if (passwordTF.getText().equals(rePasswordTF.getText())) {
                if (UsersDao.getInstance().insert(user)) {
                    Methods.alertDialog("You've successfully registered. You can login!", "ALERT", "INFORMATION");
                }
            } else {
                Methods.alertDialog("Please make sure from your password", "ALERT", "WARNING");
            }
        }
    }

}
