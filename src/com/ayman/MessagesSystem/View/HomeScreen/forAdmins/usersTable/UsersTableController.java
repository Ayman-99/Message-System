package com.ayman.MessagesSystem.View.HomeScreen.forAdmins.usersTable;

import Main.Methods;
import com.ayman.MessagesSystem.Code.Dao.UsersDao;
import com.ayman.MessagesSystem.Code.Type.Validation;
import com.ayman.MessagesSystem.Code.VO.userVo;
import com.jfoenix.controls.JFXComboBox;
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

public class UsersTableController implements Initializable {

    ObservableList<userVo> list;
    @FXML
    private TreeTableColumn<userVo, String> usernameCol;
    @FXML
    private JFXTextField usernameTF;
    @FXML
    private JFXTextField passwordTF;
    @FXML
    private JFXTextField searchTF;
    @FXML
    private JFXTreeTableView<userVo> treeTableView;
    @FXML
    private TreeTableColumn<userVo, String> passwordCol;
    @FXML
    private TreeTableColumn<userVo, Integer> idCol;
    @FXML
    private JFXTextField emailTF;
    @FXML
    private TreeTableColumn<userVo, String> emailCol;
    @FXML
    private JFXTextField idTF;
    @FXML
    private Label numOfUsers;
    @FXML
    private Label numOfOnline;
    @FXML
    private Label numOfOffline;
    @FXML
    private JFXComboBox<String> typeCombo;
    @FXML
    private TreeTableColumn<userVo, Integer> typeCol;
    @FXML
    private TreeTableColumn<userVo, Integer> statusCol;
    @FXML
    private JFXTextField statusTF;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeCombo.getItems().addAll("Admin", "User");
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(UsersTableController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsersTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        searchTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableView.setPredicate(new Predicate<TreeItem<userVo>>() {
                    @Override
                    public boolean test(TreeItem<userVo> modelTreeItem) {
                        return modelTreeItem.getValue().getUsername().getValue().contains(newValue) | modelTreeItem.getValue().getPassword().getValue().contains(newValue);
                    }
                });
            }
        });
        treeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                showDetails(newValue);
            } catch (NullPointerException ex) {
            }
        });
    }

    public void loadNums() throws SQLException, ClassNotFoundException {
        numOfUsers.setText(String.valueOf(UsersDao.getNumOfUsers()));
        numOfOnline.setText(String.valueOf(UsersDao.getNumOfOnline()));
        numOfOffline.setText(String.valueOf(UsersDao.getNumOfOffline()));
    }

    @FXML
    void addAction(ActionEvent event) throws SQLException, Exception {
        userVo uv = new userVo(usernameTF.getText(), passwordTF.getText(), emailTF.getText(), typeCombo.getSelectionModel().getSelectedIndex() + 1, String.valueOf(0));
        if (Validation.validation(usernameTF.getText(), passwordTF.getText(), emailTF.getText())) {
            Methods.alertDialog("Please fill blanks", "INFORMATION", "INFORMATION");
        } else {
            UsersDao.userInsertFromTable(uv);
            uv.setId(UsersDao.getId(usernameTF.getText()));
            list.addAll(uv);
            clearFields();
            loadNums();
            Methods.alertDialog("User Added", "INFORMATION", "INFORMATION");
        }
    }

    @FXML
    void deleteAction(ActionEvent event) throws SQLException, ClassNotFoundException, Exception {
        if (Validation.validation(usernameTF.getText(), passwordTF.getText(), emailTF.getText())) {
            Methods.alertDialog("Please fill blanks", "INFORMATION", "INFORMATION");
        } else {
            if (UsersDao.getInstance().delete(Integer.parseInt(idTF.getText()))) {
                Methods.alertDialog("User Deleted", "INFORMATION", "INFORMATION");
                int index = treeTableView.getSelectionModel().getSelectedIndex();
                list.remove(index);
            }
            clearFields();
            loadNums();
            clearFields();
        }
    }

    public void showDetails(TreeItem<userVo> treeItem) {
        idTF.setText(String.valueOf(treeItem.getValue().getId().get()));
        usernameTF.setText(treeItem.getValue().getUsername().get());
        passwordTF.setText(treeItem.getValue().getPassword().get());
        emailTF.setText(treeItem.getValue().getEmail().get());
        typeCombo.getSelectionModel().select(treeItem.getValue().getUserType().getId() == 1 ? 0 : 1) ;//combo index start from 0, so 0 for admin 1 for user
        statusTF.setText((treeItem.getValue().getOnline().get() == 1) ? "Online" : "Offline");
    }

    public void clearFields() throws SQLException, ClassNotFoundException {
        usernameTF.setText("");
        passwordTF.setText("");
        emailTF.setText("");
        idTF.setText("");
        typeCombo.getSelectionModel().clearSelection();
        statusTF.setText("");
        UsersDao.resetNum();
    }

    @FXML
    void clearAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        clearFields();
    }

    @FXML
    void editAction(ActionEvent event) throws SQLException, ClassNotFoundException, Exception {
        if (Validation.validation(usernameTF.getText(), passwordTF.getText(), emailTF.getText())) {
            Methods.alertDialog("Please fill blanks", "INFORMATION", "INFORMATION");
        } else {
            TreeItem<userVo> treeItem = treeTableView.getSelectionModel().getSelectedItem();
            userVo uv = new userVo(usernameTF.getText(), passwordTF.getText(), emailTF.getText(), typeCombo.getSelectionModel().getSelectedIndex() + 1, String.valueOf(0));
            uv.setId(UsersDao.getId(usernameTF.getText()));
            treeItem.setValue(UsersDao.getInstance().update(uv, Integer.parseInt(idTF.getText())));
            Methods.alertDialog("User Updated", "INFORMATION", "INFORMATION");
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
            Logger.getLogger(UsersTableController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsersTableController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<userVo, Integer> param) -> param.getValue().getValue().getId().asObject());

        usernameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<userVo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<userVo, String> param) {
                return param.getValue().getValue().getUsername();
            }
        });

        passwordCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<userVo, String> param) -> param.getValue().getValue().getPassword());

        emailCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<userVo, String> param) -> param.getValue().getValue().getEmail());

        typeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<userVo, Integer> param) -> param.getValue().getValue().getUserTypeInt().asObject());

        statusCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<userVo, Integer> param) -> param.getValue().getValue().getOnline().asObject());

        list = FXCollections.observableArrayList();

        TreeItem<userVo> root = new RecursiveTreeItem<userVo>(list, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        try {
            list.addAll(UsersDao.getInstance().loadAll());
        } catch (Exception ex) {
            Logger.getLogger(UsersTableController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
