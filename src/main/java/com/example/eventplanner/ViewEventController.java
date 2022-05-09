package com.example.eventplanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewEventController {
    //FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("View_Event.fxml"));

    @FXML
    Text TextId;

    @FXML
    private Text DescriptionEvent;

    @FXML
    private Text End;

    @FXML
    private Text IdEvent;

    @FXML
    private Text NameEvent;

    @FXML
    private Text Participants;

    @FXML
    private Text Start;

    EventModel eventt;
    public void initialize(EventModel event){
        IdEvent.setText(String.valueOf(event.getID()));
        NameEvent.setText(event.getNume());
        DescriptionEvent.setText(event.getDescription());
        Start.setText(event.getStartdate());
        End.setText(event.getEnddate());
        Participants.setText(String.valueOf(event.getUserlist().size())+"/"+String.valueOf(event.getLimit()));
    }



}

