package com.ayman.MessagesSystem.View.HomeScreen.forUsers;

import Main.Methods;
import com.ayman.MessagesSystem.Code.Dao.MessagesDao;
import com.ayman.MessagesSystem.Code.Dao.UsersDao;
import com.ayman.MessagesSystem.Code.Type.actions;
import com.ayman.MessagesSystem.Code.VO.messagesVo;
import com.ayman.MessagesSystem.View.Login.LoginFXMLController;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class HomeController implements Initializable {

    @FXML
    private ListView<String> onlineUsers;
    @FXML
    public JFXTextArea chatWindow;
    @FXML
    private JFXTextField inputText;

    int count;

    MessagesDao message = new MessagesDao();
    ObservableList oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            startCountDown();
            loadMessages();
            loadOnline();
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendMessage() throws ClassNotFoundException, SQLException {
        if (inputText.getText().isEmpty()) {
            Methods.alertDialog("Write something ffs", "ALERT", "INFORMATION");
            chatWindow.setDisable(false);
        } else if (inputText.getText().isEmpty()) {
            Methods.alertDialog("Write something ffs", "ALERT", "INFORMATION");
        } else {
            message.insert(inputText.getText(), LoginFXMLController.tempUsername);
            chatWindow.appendText(LoginFXMLController.tempUsername + ": " + inputText.getText() + "\n");
            chatWindow.setDisable(false);
            inputText.clear();
            loadMessages();
        }
    }

    @FXML
    private void preventWrite(MouseEvent event) {
        Methods.alertDialog("YOU CAN'T WRITE HERE. Click on send message to unlock it", "ALERT", "WARNING");
        chatWindow.setDisable(true);
    }

    public void loadOnline() throws SQLException, ClassNotFoundException {
        oblist.clear();
        onlineUsers.getItems().clear();
        oblist.addAll(MessagesDao.getNames());
        onlineUsers.getItems().addAll(oblist);
    }

    public void loadMessages() throws ClassNotFoundException, SQLException {
        chatWindow.clear();
        List<messagesVo> list = MessagesDao.getMessage();
        list.forEach(e -> {
            chatWindow.appendText(MessagesDao.getUsername(e.getFrom_user().get()) + ": " + e.getMessage_body().get() + "\n");
        });
    }

    //For automatically loading messages
    public void startCountDown() {
        try {
            count = 5;
            KeyFrame key = new KeyFrame(
                    Duration.millis(1000), e -> updateText());
            Timeline time = new Timeline(key);
            time.setCycleCount(count);
            time.play();
        } catch (Exception e) {

        }
    }

    public void updateText() {
        count--;
        if (count == 0) {
            try {
                loadMessages();
                loadOnline();
                startCountDown();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @FXML
    private void sendMessage(KeyEvent event) throws ClassNotFoundException, SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        actions.setOnline(LoginFXMLController.tempUsername, "0");
        actions.deleteMessages(UsersDao.getId(LoginFXMLController.tempUsername));
        System.exit(0);
    }

}
