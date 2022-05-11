package com.example.eventplanner;

import genericclasses.DatabaseComm;
import genericclasses.Event;
import genericclasses.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    @FXML
    Button TestEventButton, LogoutButton,CalendarButton,NotifButton,SettingsButton,CreateEventButton,ViewEventButton,RefreshButton,EventManagementButton;

    @FXML
    TextField SearchField;

    @FXML
    Text NotifText;

    ObservableList<String> filtersName = FXCollections.observableArrayList("All Events", "Daytime Events", "Nighttime Events", "Weekend Events", "Formal Events", "Casual Events", "Sport Events", "Charity Events");
    @FXML
    ChoiceBox filters;

    @FXML
    TableView<EventModel> eventTableView = new TableView<>();

    @FXML
    public TableColumn<Event, Integer> eventID;
    @FXML
    public TableColumn<Event,String> eventName;

    @FXML
    public TableColumn<Event, Integer> participantsNum;

    @FXML
    public TableColumn<Event, LocalDateTime> startDate;

    @FXML
    public TableColumn<Event, LocalDateTime> endDate;

    List<Event> eventList=new ArrayList<>();

    ArrayList<User> userList=new ArrayList<>();
    private void addUser(){
        userList.add(new User(2, "Ion", "12345678", "ion.gmail.com"));
    }


    private ObservableList<EventModel> eventModels = FXCollections.observableArrayList(
            new EventModel( 1," SLjazzing","Este vorba despre o plimbare muzicala cu Tramvaiul Turistic ce strabate orasul de pe Bega, totul pe acorduri Jazzy, așa cum v-am obișnuit.",LocalDateTime.now(),LocalDateTime.now(),new ArrayList<User>(), 50)
            );
    @FXML
    protected void onTestEvent(){
        addUser();
        eventModels.add(new EventModel( 2,"Street Food Festival","Dacă îți dorești un prânz/ o cină în aer liber, sau doar vrei să ieși la o băutură rece alături de prieteni, la Iulius Town vei putea face asta, iar noi te așteptăm cu brațele deschise!",LocalDateTime.now(),LocalDateTime.now(),userList,50));
      eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
      eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
      participantsNum.setCellValueFactory(new PropertyValueFactory<>("Limit"));
      startDate.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
      endDate.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
      eventTableView.setItems(eventModels);
    }

    @FXML
    ImageView logo;

    @FXML
    protected void onCreateEventButtonClick(){
        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("create-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), Color.web("#764AF1"));
            Stage stage= new Stage();
            stage.setMinWidth(304);
            stage.setMinHeight(262);
            stage.setTitle("Create Event");
            stage.setScene(scene);
            stage.show();
            Stage stagelogin= (Stage) CreateEventButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onNotifButtonClick(){

    }
    @FXML
    protected void onRefreshButtonClick(){
        eventTableView.getItems().clear();
        eventModels = DatabaseComm.refreshlist();
        eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
        eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        participantsNum.setCellValueFactory(new PropertyValueFactory<>("Limit"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
        eventTableView.setItems(eventModels);
    }
    @FXML
    protected void onViewEventButtonClick(){

    }
    @FXML
    protected void onLogoutButtonClick(){
        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("hello-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            Stage stage= new Stage();
            stage.setMinWidth(304);
            stage.setMinHeight(262);
            stage.setTitle("Login Screen");
            stage.setScene(scene);
            stage.show();
            Stage stagelogin= (Stage) LogoutButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    protected void onEventManagementButtonClick(){
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("event-manager-view.fxml"));
        Scene scene = null;
        try {
            Stage stage= new Stage();
            scene = new Scene(fxmlLoader.load());
            stage.setMinWidth(304);
            stage.setMinHeight(262);
            stage.setTitle("Event Management");
            stage.setScene(scene);
            //stage.setResizable(false);
            stage.show();
            Stage stagelogin= (Stage) EventManagementButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    protected void onSettingsButtonClick(){

    }
    @FXML
    protected void onCalendarButtonClick(){

    }

    @FXML
    protected void onMouseClickTable(){
        //eventTableView.getSelectionModel().getSelectedItem();
        EventModel eventSelected = eventTableView.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("View_Event.fxml"));
        Scene scene = null;
        if (eventSelected == null) System.out.println("couldn't find event");
        else {


            try {
                Stage stage = new Stage();
                scene = new Scene(fxmlLoader.load());
                stage.setMinWidth(304);
                stage.setMinHeight(262);
                stage.setTitle("View Event");
                stage.setScene(scene);
                //stage.setResizable(false);
                ViewEventController controller = fxmlLoader.<ViewEventController>getController();
                controller.initialize(eventSelected);
                stage.show();


            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }


    }

    @FXML
    protected void onSearch(){
        String[] keys = SearchField.getText().split(" ");
        ArrayList<Event> events = DatabaseComm.getAllEvents();

        ArrayList<Event> results = new ArrayList<>();

        for(Event e : events)
            for(String k : keys)
                if(e.getNume().contains(k) || e.getDescription().contains(k)) {
                    results.add(e);
                    continue;
                }

        ObservableList<EventModel> resultsTable = FXCollections.observableArrayList();
        for(Event e : results)
            resultsTable.add(new EventModel(e.getId(), e.getNume(), e.getDescription(), e.getStartdate().toLocalDateTime(), e.getEnddate().toLocalDateTime(), e.getUserlist(), e.getLimit()));

        eventTableView.getItems().clear();
        eventModels = resultsTable;
        eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        participantsNum.setCellValueFactory(new PropertyValueFactory<>("Limit"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
        eventTableView.setItems(eventModels);

    }

    @FXML
    protected void onFilter(){
        String key = filters.getValue().toString();

        int filter = 0;
        if(key.equals("Daytime Events"))
            filter = 1;
        else if(key.equals("Nighttime Events"))
            filter = 2;
        else if(key.equals("Weekend Events"))
            filter = 3;
        else if(key.equals("Formal Events"))
            filter = 4;
        else if(key.equals("Casual Events"))
            filter = 5;
        else if(key.equals("Sport Events"))
            filter = 6;
        else if(key.equals("Charity Events"))
            filter = 7;

        ArrayList<Event> results = new ArrayList<>();
        if(filter != 0)
            results = DatabaseComm.getFilteredEvents(filter);
        else
            results = DatabaseComm.getAllEvents();

        ObservableList<EventModel> resultsTable = FXCollections.observableArrayList();
        for(Event e : results)
            resultsTable.add(new EventModel(e.getId(), e.getNume(), e.getDescription(), e.getStartdate().toLocalDateTime(), e.getEnddate().toLocalDateTime(), e.getUserlist(), e.getLimit()));

        eventTableView.getItems().clear();
        eventModels = resultsTable;
        eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        participantsNum.setCellValueFactory(new PropertyValueFactory<>("Limit"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
        eventTableView.setItems(eventModels);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filters.setItems(filtersName);
        filters.setValue("All Events");
    }
}
