package com.example.eventplanner;

import genericclasses.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NotificationModel {
        public SimpleIntegerProperty ID;
        protected SimpleStringProperty notifName;
        protected SimpleStringProperty notifDesc;
        protected SimpleStringProperty user_from;
        protected SimpleIntegerProperty status;
        protected SimpleIntegerProperty idEvent;

        private User user_acc;
        public NotificationModel(int id,String name,String desc,User from,int status, int idEvent){
            this.ID=new SimpleIntegerProperty(id);
            this.notifName=new SimpleStringProperty(name);
            this.notifDesc=new SimpleStringProperty(desc);
            this.user_acc=from;
            this.user_from=new SimpleStringProperty(from.getUsername());
            this.status=new SimpleIntegerProperty(status);
            this.idEvent=new SimpleIntegerProperty(idEvent);
        }

    public int getIdEvent() {
        return idEvent.get();
    }

    public SimpleIntegerProperty idEventProperty() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent.set(idEvent);
    }

    public int getID() {
        return ID.get();
    }

    public SimpleIntegerProperty IDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getNotifName() {
        return notifName.get();
    }

    public SimpleStringProperty notifNameProperty() {
        return notifName;
    }

    public void setNotifName(String notifName) {
        this.notifName.set(notifName);
    }

    public String getNotifDesc() {
        return notifDesc.get();
    }

    public SimpleStringProperty notifDescProperty() {
        return notifDesc;
    }

    public void setNotifDesc(String notifDesc) {
        this.notifDesc.set(notifDesc);
    }

    public String getUser_from() {
        return user_from.get();
    }

    public SimpleStringProperty user_fromProperty() {
        return user_from;
    }

    public void setUser_from(String user_from) {
        this.user_from.set(user_from);
    }

    public int getStatus() {
        return status.get();
    }

    public SimpleIntegerProperty statusProperty() {
        return status;
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public User getUser_acc() {
        return user_acc;
    }

    public void setUser_acc(User user_acc) {
        this.user_acc = user_acc;
    }
}
