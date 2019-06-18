package com.ayman.MessagesSystem.View.HomeScreen.forAdmins.messagesTable;

import Main.Methods;
import com.ayman.MessagesSystem.Code.Dao.MessagesDao;
import com.ayman.MessagesSystem.Code.Dao.UsersDao;
import com.ayman.MessagesSystem.Code.Type.Validation;
import com.ayman.MessagesSystem.Code.VO.messagesVo;
import com.ayman.MessagesSystem.Code.VO.userVo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class messagesTableController implements Initializable {

    ObservableList<messagesVo> list;
    @FXML
    private JFXTextField searchTF;
    @FXML
    private JFXTreeTableView<messagesVo> treeTableView;
    @FXML
    private TreeTableColumn<messagesVo, Integer> idCol;
    @FXML
    private JFXTextField idTF;
    @FXML
    private Label numOfOnline;
    @FXML
    private Label numOfOffline;
    @FXML
    private JFXTextArea messageTA;
    @FXML
    private JFXTextField fromTF;
    @FXML
    private Label numOfMessages;
    @FXML
    private TreeTableColumn<messagesVo, Integer> fromCol;
    @FXML
    private TreeTableColumn<messagesVo, String> messageCol;

    private int fromId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(messagesTableController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(messagesTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        searchTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableView.setPredicate(new Predicate<TreeItem<messagesVo>>() {
                    @Override
                    public boolean test(TreeItem<messagesVo> modelTreeItem) {
                        return modelTreeItem.getValue().getMessage_body().getValue().contains(newValue) | modelTreeItem.getValue().getMessage_body().getValue().contains(newValue);
                    }
                });
            }
        });
        treeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                showDetails(newValue);
            } catch (NullPointerException ex) {
            } catch (SQLException ex) {
                Logger.getLogger(messagesTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void loadNums() throws SQLException, ClassNotFoundException {
        numOfMessages.setText(String.valueOf(MessagesDao.getNumOfMessages()));
        numOfOnline.setText(String.valueOf(UsersDao.getNumOfOnline()));
        numOfOffline.setText(String.valueOf(UsersDao.getNumOfOffline()));
    }

    @FXML
    void deleteAction(ActionEvent event) throws SQLException, ClassNotFoundException, Exception {
        if (Validation.validation(messageTA.getText())) {
            Methods.alertDialog("Please fill blanks", "INFORMATION", "INFORMATION");
        } else {
            MessagesDao.deleteMessage(Integer.parseInt(idTF.getText()));
            Methods.alertDialog("Message Deleted", "INFORMATION", "INFORMATION");
            int index = treeTableView.getSelectionModel().getSelectedIndex();
            list.remove(index);
            clearFields();
            loadNums();
        }
    }

    public void showDetails(TreeItem<messagesVo> treeItem) throws SQLException {
        idTF.setText(String.valueOf(treeItem.getValue().getId().get()));
        fromTF.setText(MessagesDao.getNameById(treeItem.getValue().getFrom_user().get()));
        fromId = treeItem.getValue().getFrom_user().get();
        messageTA.setText(treeItem.getValue().getMessage_body().get());
    }

    public void clearFields() throws SQLException, ClassNotFoundException {
        idTF.setText("");
        fromTF.setText("");
        messageTA.setText("");
        UsersDao.resetNum();
        MessagesDao.resetNum();
    }

    void clearAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        clearFields();
    }

    @FXML
    void editAction(ActionEvent event) throws SQLException, ClassNotFoundException, Exception {
        if (Validation.validation(messageTA.getText())) {
            Methods.alertDialog("Please fill blanks", "INFORMATION", "INFORMATION");
        } else {
            messagesVo mv = new messagesVo(Integer.parseInt(idTF.getText()), fromId, messageTA.getText());
            TreeItem<messagesVo> treeItem = treeTableView.getSelectionModel().getSelectedItem();
            treeItem.setValue(MessagesDao.updateMessage(mv));
            Methods.alertDialog("Message Edited", "INFORMATION", "INFORMATION");
            clearFields();
            loadNums();
        }
    }

    @FXML
    private void loadData() throws SQLException, ClassNotFoundException {
        clearFields();
        try {
            clearFields();
            loadNums();

        } catch (SQLException ex) {
            Logger.getLogger(messagesTableController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(messagesTableController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<messagesVo, Integer> param) -> param.getValue().getValue().getId().asObject());

        fromCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<messagesVo, Integer> param) -> param.getValue().getValue().getFrom_user().asObject());

        messageCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<messagesVo, String> param) -> param.getValue().getValue().getMessage_body());

        list = FXCollections.observableArrayList();

        TreeItem<messagesVo> root = new RecursiveTreeItem<messagesVo>(list, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        try {
            list.addAll(MessagesDao.getMessage());

        } catch (Exception ex) {
            Logger.getLogger(messagesTableController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void sendAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        messagesVo uv = new messagesVo(messageTA.getText());
        if (Validation.validation(messageTA.getText())) {
            Methods.alertDialog("Please fill blanks", "INFORMATION", "INFORMATION");
        } else {
            MessagesDao.insertGloablMessage(messageTA.getText());
            uv.setId(MessagesDao.getMessageId(messageTA.getText()));
            list.addAll(uv);
            clearFields();
            loadNums();
        }
    }

    @FXML
    private void resetAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        MessagesDao.resetMessages();
        Methods.alertDialog("All messages have been deleted and Auto increment reset", "INFORMATION", "INFORMATION");
        clearFields();
        loadNums();
        loadData();
    }

}
