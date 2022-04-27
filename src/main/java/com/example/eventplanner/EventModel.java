package com.example.eventplanner;

import genericclasses.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventModel {
    public SimpleIntegerProperty ID;
    public SimpleStringProperty EventName,description;
    public SimpleStringProperty StartDate;
    public SimpleStringProperty EndDate;
    public SimpleListProperty<User> userlist;
    public SimpleIntegerProperty Participants;
    public EventModel(Integer id, String nume,String description, LocalDateTime startdate,LocalDateTime enddate, ArrayList<User> userlist, Integer limit){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.ID=new SimpleIntegerProperty(id);
        this.EventName=new SimpleStringProperty(nume);
        this.description=new SimpleStringProperty(description);
        this.StartDate=new SimpleStringProperty(startdate.format(formatter).toString());
        this.EndDate=new SimpleStringProperty(enddate.format(formatter).toString());
        this.userlist=new SimpleListProperty<User>(FXCollections.observableList(userlist));
        this.Participants=new SimpleIntegerProperty(limit);
    }

    public int getID() {
        return ID.get();
    }

    public SimpleIntegerProperty idProperty() {
        return ID;
    }

    public void setid(int id) {
        this.ID.set(id);
    }

    public String getNume() {
        return EventName.get();
    }

    public SimpleStringProperty numeProperty() {
        return EventName;
    }

    public void setNume(String nume) {
        this.EventName.set(nume);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getStartdate() {
        return StartDate.get();
    }

    public SimpleStringProperty startdateProperty() {
        return StartDate;
    }

    public void setStartdate(String startdate) {
        this.StartDate.set(startdate);
    }

    public String getEnddate() {
        return EndDate.get();
    }

    public SimpleStringProperty enddateProperty() {
        return EndDate;
    }

    public void setEnddate(String enddate) {
        this.EndDate.set(enddate);
    }

    public ObservableList<User> getUserlist() {
        return userlist.get();
    }

    public SimpleListProperty<User> userlistProperty() {
        return userlist;
    }

    public void setUserlist(ObservableList<User> userlist) {
        this.userlist.set(userlist);
    }

    public int getLimit() {
        return Participants.get();
    }

    public SimpleIntegerProperty limitProperty() {
        return Participants;
    }

    public void setLimit(int limit) {
        this.Participants.set(limit);
    }
}
