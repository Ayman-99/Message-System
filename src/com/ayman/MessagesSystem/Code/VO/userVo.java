package com.ayman.MessagesSystem.Code.VO;

import com.ayman.MessagesSystem.Code.Type.UsersType;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class userVo extends RecursiveTreeObject<userVo>{
    
    IntegerProperty id;
    StringProperty username;
    StringProperty password;
    StringProperty email;
    UsersType userType;
    IntegerProperty userTypeInt;
    IntegerProperty online;
    
    public userVo(){
        
    }
    public userVo(String username, String password, String email, int Type, String online){
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.userTypeInt = new SimpleIntegerProperty(Type);
        this.online = new SimpleIntegerProperty(Integer.parseInt(online));       
    }
    
    public userVo(int id, String username, String password, String email, int Type, String online){
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.userType = UsersType.getUserTypeById(Type);
        this.userTypeInt = new SimpleIntegerProperty(Type);
        this.online = new SimpleIntegerProperty(Integer.parseInt(online));       
    }
    
    public userVo(String username, String password, String email){
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);  
    }
    
    public IntegerProperty getId() {
        return id;
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public StringProperty getUsername() {
        return username;
    }

    public void setUsername(StringProperty username) {
        this.username = username;
    }

    public StringProperty getPassword() {
        return password;
    }

    public void setPassword(StringProperty password) {
        this.password = password;
    }

    public StringProperty getEmail() {
        return email;
    }

    public void setEmail(StringProperty email) {
        this.email = email;
    }

    public UsersType getUserType() {
        return userType;
    }

    public void setUserType(UsersType userType) {
        this.userType = userType;
    }

    public IntegerProperty getOnline() {
        return online;
    }

    public void setOnline(IntegerProperty online) {
        this.online = online;
    }
    public String getUserTypeInString(int id) {
        return (UsersType.getUserTypeById(id)).getType();
    }

    public IntegerProperty getUserTypeInt() {
        return userTypeInt;
    }

    public void setUserTypeInt(IntegerProperty userTypeInt) {
        this.userTypeInt = userTypeInt;
    }
    
    
}
