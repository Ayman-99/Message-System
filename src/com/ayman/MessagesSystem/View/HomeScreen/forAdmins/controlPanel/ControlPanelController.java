package com.ayman.MessagesSystem.View.HomeScreen.forAdmins.controlPanel;

import Main.Methods;
import static Main.Methods.alertDialog;
import com.ayman.MessagesSystem.Code.Dao.MessagesDao;
import com.ayman.MessagesSystem.Code.Dao.UsersDao;
import com.ayman.MessagesSystem.Code.Type.actions;
import com.ayman.MessagesSystem.View.Login.LoginFXMLController;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControlPanelController implements Initializable {

    private Stage stage;

    private JFXButton[] buttons;
    @FXML
    private AnchorPane imagePane1;
    @FXML
    private AnchorPane imagePane2;
    @FXML
    private JFXButton b1;
    @FXML
    private JFXButton b2;
    @FXML
    private JFXButton b3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXButton[] btns = {b1, b2, b3};
        buttons = btns;
        btns = null;
        stage = new Stage();
        imagePane1.setStyle("-fx-background-image: url(com/ayman/MessagesSystem/View/HomeScreen/forAdmins/controlPanel/1.jpg);");
        imagePane2.setStyle("-fx-background-image: url(com/ayman/MessagesSystem/View/HomeScreen/forAdmins/controlPanel/2.jpg);");

        Animation();
    }

    public void Animation() {

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), imagePane2);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(3), imagePane1);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {

                FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(3), imagePane2);
                fadeTransition2.setFromValue(1);
                fadeTransition2.setToValue(0);
                fadeTransition2.play();

                fadeTransition2.setOnFinished(event2 -> {

                    FadeTransition fadeTransition00 = new FadeTransition(Duration.seconds(3), imagePane2);
                    fadeTransition00.setFromValue(0);
                    fadeTransition00.setToValue(1);
                    fadeTransition00.play();

                    fadeTransition00.setOnFinished(event3 -> {
                        FadeTransition fadeTransition11 = new FadeTransition(Duration.seconds(3), imagePane1);
                        fadeTransition11.setFromValue(0);
                        fadeTransition11.setToValue(1);
                        fadeTransition11.play();

                        fadeTransition11.setOnFinished(event5 -> {
                            Animation();
                        });
                    });

                });
            });

        });
    }

    @FXML
    private void closeProgram(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void loadRed(ActionEvent event) {
        for (JFXButton button : buttons) {
            button.setStyle("-fx-background-color: RED;");
        }
    }

    @FXML
    private void loadGreen(ActionEvent event) {
        for (JFXButton button : buttons) {
            button.setStyle("-fx-background-color: GREEN;");
        }
    }

    @FXML
    private void loadYellow(ActionEvent event) {
        for (JFXButton button : buttons) {
            button.setStyle("-fx-background-color: YELLOW;");
        }
    }

    @FXML
    private void loadBlue(ActionEvent event) {
        for (JFXButton button : buttons) {
            button.setStyle("-fx-background-color: BLUE;");
        }
    }

    @FXML
    private void about(ActionEvent event) {
        Methods.alertDialog("Use this screen to control your message system", "Alert", "INFORMATION");
    }

    @FXML
    private void loadUsers(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/com/ayman/MessagesSystem/View/HomeScreen/forAdmins/usersTable/usersTable.fxml"));
        stage.setTitle("Users table");
        stage.setScene(new Scene(root));

        stage.onShownProperty().set(e -> {
            try {
                UsersDao.onConn();
            } catch (SQLException ex) {
                Logger.getLogger(ControlPanelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        stage.show();
    }

    @FXML
    private void loadMessages(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/com/ayman/MessagesSystem/View/HomeScreen/forAdmins/messagesTable/messagesTable.fxml"));
        stage.setTitle("Messages table");
        stage.setScene(new Scene(root));

        stage.onShownProperty().set(e -> {
            try {
                MessagesDao.onConn();
            } catch (SQLException ex) {
                Logger.getLogger(ControlPanelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        stage.show();
    }

    @FXML
    private void loadChat(ActionEvent event) throws IOException {
        actions.setOnline(LoginFXMLController.tempUsername, "1");
        AnchorPane root = FXMLLoader.load(getClass().getResource("/com/ayman/MessagesSystem/View/HomeScreen/forUsers/Home.fxml"));
        stage.setTitle("Chat");

        stage.setScene(new Scene(root));
        stage.show();
    }

}
