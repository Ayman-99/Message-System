package com.ayman.MessagesSystem.Code.VO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class messagesVo extends RecursiveTreeObject<messagesVo>{
    IntegerProperty id;
    IntegerProperty from_user;
    StringProperty message_body;
    userVo user;
    
    public messagesVo(int id, int from, String message){
        this.id = new SimpleIntegerProperty(id);
        this.from_user = new SimpleIntegerProperty(from);
        this.message_body = new SimpleStringProperty(message);
    }
    public messagesVo(String message){
        this.from_user = new SimpleIntegerProperty(1);
        this.message_body = new SimpleStringProperty(message);
    }

    public IntegerProperty getFrom_user() {
        return from_user;
    }

    public void setFrom_user(IntegerProperty from_user) {
        this.from_user = from_user;
    }

    public StringProperty getMessage_body() {
        return message_body;
    }

    public void setMessage_body(StringProperty message_body) {
        this.message_body = message_body;
    }

    public IntegerProperty getId() {
        return id;
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }
    
}
