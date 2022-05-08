package com.example.eventplanner;

import genericclasses.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    public void setButtonClass(){
        Button buttonsarr[]={};
        List<Button> buttons;
        buttons= Arrays.asList(buttonsarr);
        Session.ButtonConfig(buttons);
    }

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

