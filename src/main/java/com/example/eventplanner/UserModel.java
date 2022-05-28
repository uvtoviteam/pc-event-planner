package com.example.eventplanner;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserModel {
    public SimpleIntegerProperty ID;
    public SimpleStringProperty name;

    public UserModel(Integer id, String nume){
        this.ID=new SimpleIntegerProperty(id);
        this.name=new SimpleStringProperty(nume);

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
        return name.get();
    }

    public SimpleStringProperty numeProperty() {
        return name;
    }

    public void setNume(String nume) {
        this.name.set(nume);
    }


}
