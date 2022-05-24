package com.example.eventplanner;

import genericclasses.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventModel {
    public SimpleIntegerProperty ID;
    public SimpleStringProperty EventName,description, location;
    public SimpleStringProperty StartDate;
    public SimpleStringProperty EndDate;
    public SimpleListProperty<User> userlist;
    public SimpleIntegerProperty Participants;
    public SimpleIntegerProperty creator;
    public SimpleIntegerProperty status;

    protected LocalDateTime startDatePrivate,endDatePrivate;
    public EventModel(Integer id, String nume,String description, LocalDateTime startdate,LocalDateTime enddate, ArrayList<User> userlist, Integer limit, Integer creator, String location, Integer status){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.ID=new SimpleIntegerProperty(id);
        this.EventName=new SimpleStringProperty(nume);
        this.description=new SimpleStringProperty(description);
        this.location=new SimpleStringProperty(location);
        this.StartDate=new SimpleStringProperty(startdate.format(formatter));
        this.EndDate=new SimpleStringProperty(enddate.format(formatter));
        this.userlist=new SimpleListProperty<User>(FXCollections.observableList(userlist));
        this.Participants=new SimpleIntegerProperty(limit);
        this.startDatePrivate=startdate;
        this.endDatePrivate=enddate;
        this.creator=new SimpleIntegerProperty(creator);
        this.status=new SimpleIntegerProperty(status);
        System.out.println(startDatePrivate);
    }
    public EventModel(Integer id, String nume,String description, LocalDateTime startdate,LocalDateTime enddate, ArrayList<User> userlist, Integer limit, Integer creator, String location){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.ID=new SimpleIntegerProperty(id);
        this.EventName=new SimpleStringProperty(nume);
        this.description=new SimpleStringProperty(description);
        this.location=new SimpleStringProperty(location);
        this.StartDate=new SimpleStringProperty(startdate.format(formatter));
        this.EndDate=new SimpleStringProperty(enddate.format(formatter));
        this.userlist=new SimpleListProperty<User>(FXCollections.observableList(userlist));
        this.Participants=new SimpleIntegerProperty(limit);
        this.startDatePrivate=startdate;
        this.endDatePrivate=enddate;
        this.creator=new SimpleIntegerProperty(creator);
        System.out.println(startDatePrivate);
    }

    public EventModel(Integer id, String nume,String description, LocalDateTime startdate,LocalDateTime enddate, ArrayList<User> userlist, Integer limit, String location){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.ID=new SimpleIntegerProperty(id);
        this.EventName=new SimpleStringProperty(nume);
        this.description=new SimpleStringProperty(description);
        this.StartDate=new SimpleStringProperty(startdate.format(formatter));
        this.EndDate=new SimpleStringProperty(enddate.format(formatter));
        this.userlist=new SimpleListProperty<User>(FXCollections.observableList(userlist));
        this.Participants=new SimpleIntegerProperty(limit);
        this.startDatePrivate=startdate;
        this.endDatePrivate=enddate;
        this.location=new SimpleStringProperty(location);
        System.out.println(startDatePrivate);
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

    public int getUsersize() {return userlist.size();}

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

    public LocalDate getDate(boolean which){
        if(which)
            return startDatePrivate.toLocalDate();
        else
            return endDatePrivate.toLocalDate();
    }

    public LocalTime getTime(boolean which){
        if(which)
            return startDatePrivate.toLocalTime();
        else
            return endDatePrivate.toLocalTime();
    }
    public int getHour(boolean which){
        if(which)
            return startDatePrivate.getHour();
        else
            return endDatePrivate.getHour();
    }
    public int getMinute(boolean which){
        if(which)
            return startDatePrivate.getMinute();
        else
            return endDatePrivate.getMinute();
    }
    public Timestamp getStartDatePrivate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(startDatePrivate.format(formatter));
        return sqlDate;
    }

    public Timestamp getEndDatePrivate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(endDatePrivate.format(formatter));
        return sqlDate;
    }
    public void setStartDatePrivate(LocalDateTime date){
        this.startDatePrivate=date;
        this.setStartdate(date.toString());
    }
    public void setEndDatePrivate(LocalDateTime date){
        this.endDatePrivate=date;
        this.setEnddate(date.toString());
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

    public SimpleIntegerProperty getCreator(){
        return creator;
    }
    public void setCreator(int creator){
        this.creator=new SimpleIntegerProperty(creator);
    }

    public String getLocation() {
        return location.get();
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }
}
