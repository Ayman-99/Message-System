package com.ayman.MessagesSystem.View.Login;

import static Main.Methods.alertDialog;
import com.ayman.MessagesSystem.Code.Dao.Dao;
import com.ayman.MessagesSystem.Code.Dao.UsersDao;
import com.ayman.MessagesSystem.Code.Type.sendMail;
import com.ayman.MessagesSystem.Code.VO.userVo;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import Main.messages;
import Main.Methods;
import com.ayman.MessagesSystem.Code.Dao.MessagesDao;
import com.ayman.MessagesSystem.Code.Type.Validation;
import com.ayman.MessagesSystem.Code.Type.actions;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class LoginFXMLController implements Initializable {

    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    @FXML
    private Pane pane4;
    @FXML
    private ImageView image;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    private List<userVo> list;

    private final Stage window = messages.window;
    public static String tempUsername;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = UsersDao.getInstance().loadAll();

        RotateTransition rt = new RotateTransition(Duration.seconds(35), image);
        rt.setByAngle(9 * 360);
        rt.play();

        pane1.setStyle("-fx-background-image: url(\"/com/ayman/MessagesSystem/View/Login/1.jpg\")");
        pane2.setStyle("-fx-background-image: url(\"/com/ayman/MessagesSystem/View/Login/2.jpg\")");
        pane3.setStyle("-fx-background-image: url(\"/com/ayman/MessagesSystem/View/Login/3.jpg\")");
        pane4.setStyle("-fx-background-image: url(\"/com/ayman/MessagesSystem/View/Login/4.jpg\")");
        backgroundAnimation();
    }

    private void backgroundAnimation() {

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), pane4);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> {

            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(3), pane3);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(3), pane2);
                fadeTransition2.setFromValue(1);
                fadeTransition2.setToValue(0);
                fadeTransition2.play();

                fadeTransition2.setOnFinished(event2 -> {

                    FadeTransition fadeTransition0 = new FadeTransition(Duration.seconds(3), pane2);
                    fadeTransition0.setToValue(1);
                    fadeTransition0.setFromValue(0);
                    fadeTransition0.play();

                    fadeTransition0.setOnFinished(event3 -> {

                        FadeTransition fadeTransition11 = new FadeTransition(Duration.seconds(3), pane3);
                        fadeTransition11.setToValue(1);
                        fadeTransition11.setFromValue(0);
                        fadeTransition11.play();

                        fadeTransition11.setOnFinished(event4 -> {

                            FadeTransition fadeTransition22 = new FadeTransition(Duration.seconds(3), pane4);
                            fadeTransition22.setToValue(1);
                            fadeTransition22.setFromValue(0);
                            fadeTransition22.play();

                            fadeTransition22.setOnFinished(event5 -> {
                                backgroundAnimation();
                            });

                        });

                    });

                });
            });

        });

    }

    @FXML
    private void login(ActionEvent event) throws IOException, SQLException {
        if (Validation.validation(username.getText(), password.getText())) {
            Methods.alertDialog("Fill the blanks!", "ALERT", "WARNING");
        } else {
            Map<String, String> map = new HashMap<String, String>();

            list.forEach((a) -> {
                map.put(a.getUsername().get(), a.getPassword().get());
            });

            if (map.containsKey(username.getText())) {
                String value = map.get(username.getText());
                if (value.equals(password.getText())) {
                    tempUsername = username.getText();
                    window.close();
                    if (actions.getUserType(tempUsername) == 1) {
                        AnchorPane root = FXMLLoader.load(getClass().getResource("/com/ayman/MessagesSystem/View/HomeScreen/forAdmins/controlPanel/controlPanel.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("Control Panel");
                        stage.setScene(new Scene(root));
                        stage.onCloseRequestProperty().set(e -> { 
                            try {
                            //to close connection when window is closed
                            MessagesDao.offCon();
                            UsersDao.offConn();
                            } catch (SQLException ex) {
                                Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        stage.show();
                    } else {
                        actions.setOnline(username.getText(), "1");
                        AnchorPane root = FXMLLoader.load(getClass().getResource("/com/ayman/MessagesSystem/View/HomeScreen/forUsers/Home.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("Chat");
                        stage.setOnCloseRequest(e -> { //to prevent use from using close icon
                            e.consume();
                            alertDialog("Please use exit button", "ALERT", "WARNING");
                        });
                        stage.setScene(new Scene(root));
                        stage.show();
                    }

                } else {
                    alertDialog("Wrong password. Try again!", "ALERT", "WARNING");
                }
            } else {
                alertDialog("Wrong info. Try again!", "ALERT", "WARNING");
            }
        }
    }

    @FXML
    private void recoverPass(ActionEvent event) throws ClassNotFoundException, SQLException {
        String email = JOptionPane.showInputDialog(null, "Please enter your email:");
        String sql = "SELECT `PASSWORD`,`USERNAME` FROM `users` WHERE `EMAIL`=?";
        PreparedStatement ps = (Dao.getConn()).prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (email.equalsIgnoreCase("") || email.equalsIgnoreCase(null)) {
            alertDialog("ENTER VALID EMAIL", "ALERT", "WARNING");
        } else if (rs.next()) {
            sendMail.sendMessage(rs.getString("PASSWORD"), rs.getString("USERNAME"), email);
            Notifications note = Notifications.create()
                    .title("Password Recovery note")
                    .text("Check your email for your password!")
                    .graphic(new Rectangle(20, 20))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_LEFT);
            note.showConfirm();
        }
    }
}
