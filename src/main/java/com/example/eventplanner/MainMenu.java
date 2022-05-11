package com.example.eventplanner;

import genericclasses.DatabaseComm;
import genericclasses.Event;
import genericclasses.Session;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    @FXML
    Button TestEventButton, LogoutButton,CalendarButton,NotifButton,SettingsButton,CreateEventButton,RefreshButton,EventManagementButton;

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
    TableView<EventModel> eventManagerTableView = new TableView<>();
    @FXML
    TableView<EventModel> eventManagerTableView2 = new TableView<>();

    @FXML
    public TableColumn<Event, Integer> eventID,eventID1;
    @FXML
    public TableColumn<Event,String> eventName,eventName1;

    @FXML
    public TableColumn<Event, Integer> participantsNum,participantsNum1;

    @FXML
    public TableColumn<Event, LocalDateTime> startDate,startDate1;

    @FXML
    public TableColumn<Event, LocalDateTime> endDate,endDate1;



    List<Event> eventList=new ArrayList<>();

    ArrayList<User> userList=new ArrayList<>();

    public void setButtonClass(){
       /* Button buttonsarr[]={TestEventButton, LogoutButton,CalendarButton,NotifButton,SettingsButton,CreateEventButton,RefreshButton,EventManagementButton};
        List<Button> buttons;
        buttons=Arrays.asList(buttonsarr);
        Session.ButtonConfig(buttons);*/
    }

    private void addUser(){
        userList.add(new User(2, "Ion", "ion.gmail.com"));
    }


    private ObservableList<EventModel> eventModels = FXCollections.observableArrayList(
            new EventModel( 1," SLjazzing","Este vorba despre o plimbare muzicala cu Tramvaiul Turistic ce strabate orasul de pe Bega, totul pe acorduri Jazzy, așa cum v-am obișnuit.",LocalDateTime.now(),LocalDateTime.now(),new ArrayList<User>(), 50,1)
            );
    private ObservableList<EventModel> eventManagerModels = FXCollections.observableArrayList();

    @FXML
    protected void onTestEvent(){
        addUser();
        eventModels.add(new EventModel( 2,"Street Food Festival","Dacă îți dorești un prânz/ o cină în aer liber, sau doar vrei să ieși la o băutură rece alături de prieteni, la Iulius Town vei putea face asta, iar noi te așteptăm cu brațele deschise!",LocalDateTime.now(),LocalDateTime.now(),userList,50,1));
      eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
      eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
      participantsNum.setCellValueFactory(new PropertyValueFactory<>("Limit"));
      startDate.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
      endDate.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
      eventTableView.setItems(eventModels);
    }

    @FXML
    ImageView logo,homeButton;

        @FXML private AnchorPane mainmenu, eventmanagerpane,eventEditPane,eventViewPane;

    @FXML
    protected void onCreateEventButtonClick(){
        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("create-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            CreateEventController controller=  fxmlLoader.getController();
            controller.setButtonClass();
            String css = this.getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
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
        int currentUser = Session.getInstance().getUser().getId();
        eventTableView.getItems().clear();
        eventModels = DatabaseComm.refreshlist(currentUser,false);
        eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
        eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        participantsNum.setCellValueFactory(new PropertyValueFactory<>("Usersize"));
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
            LoginController controller=  fxmlLoader.getController();
            controller.setButtonClass();
            String css = this.getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
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
    void onEventManagementButtonPress(MouseEvent event){
//        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("event-manager-view.fxml"));
//        Scene scene = null;
//        try {
//            Stage stage= new Stage();
//            scene = new Scene(fxmlLoader.load());
//            stage.setMinWidth(304);
//            stage.setMinHeight(262);
//            stage.setTitle("Event Management");
//            stage.setScene(scene);
//            //stage.setResizable(false);
//            EventManagerController controller=  fxmlLoader.getController();
//            controller.start(Session.getInstance().getUser(),stage); // will need an actual user with id
//            controller.setButtonClass();
//            String css = this.getClass().getResource("Style.css").toExternalForm();
//            scene.getStylesheets().add(css);
//            stage.show();
//            Stage stagelogin= (Stage) EventManagementButton.getScene().getWindow();
//            stagelogin.close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }
    @FXML
    protected void onSettingsButtonClick(){

    }
    @FXML
    protected void onCalendarButtonClick(){

    }

    @FXML
    private Text descEvent,end,start,idEvent,nameEvent,participants;


    public void fillEventPane(EventModel eventval){
        //idevent isn't selected
        idEvent.setText(String.valueOf(eventval.getID()));
        nameEvent.setText(eventval.getNume());
        descEvent.setText(eventval.getDescription());
        start.setText(eventval.getStartdate());
        end.setText(eventval.getEnddate());
        participants.setText(eventval.getUserlist().size() +"/"+ eventval.getLimit());
    }

    @FXML
    Button backEventButton,joinEventButton;

    @FXML
    protected void onBackEventPressed(){
        eventViewPane.setVisible(false);
        mainmenu.setVisible(true);
    }
    @FXML
    protected void onJoinEventPressed(){

    }

    @FXML
    protected void onMouseClickTable(){
        //eventTableView.getSelectionModel().getSelectedItem();
        EventModel eventSelected = eventTableView.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("View_Event.fxml"));
        Scene scene = null;
        if (eventSelected == null) System.out.println("couldn't find event");
        else {
            mainmenu.setVisible(false);
            System.out.println(eventSelected.getID());
            System.out.println(eventSelected.getNume());
            fillEventPane(eventSelected);
            eventViewPane.setVisible(true);

        }


    }

    @FXML
    void onManagerMouseClickTable(javafx.scene.input.MouseEvent event) {

    }
    @FXML
    void onManagerMouseClickTable2(javafx.scene.input.MouseEvent event) {

    }

    @FXML
    void onDeleteButtonClick(ActionEvent event) {
        EventModel selectedEvent=eventManagerTableView.getSelectionModel().getSelectedItem();
        if(selectedEvent!=null){
            if(DatabaseComm.deleteEvent(selectedEvent)==0)
            {
                eventManagerTableView.getItems().clear();
                eventManagerModels=DatabaseComm.refreshlist(Session.getInstance().getUser().getId(),true);
                eventManagerTableView.setItems(eventManagerModels);
            }
            else { //error
            }
        }
    }

    @FXML
    private TextField EventNameField;

    @FXML
    private Spinner<Integer> Hour1;

    @FXML
    private Spinner<Integer> Hour2;

    @FXML
    private TextArea DescArea;

    @FXML
    private DatePicker StartDateField,EndDateField;

    @FXML
    private Spinner<Integer> Min1;

    @FXML
    private Spinner<Integer> Min2;

    private EventModel currentEvent;

    void setEventData(EventModel event){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryMin1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryMin2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60);
        EventNameField.setText(event.getNume());
        DescArea.setText(event.getDescription());
        StartDateField.setValue(event.getDate(true));
        EndDateField.setValue(event.getDate(false));
        Hour1.setValueFactory(valueFactory1);
        Hour2.setValueFactory(valueFactory2);
        Min1.setValueFactory(valueFactoryMin1);
        Min2.setValueFactory(valueFactoryMin2);
        Hour1.getValueFactory().setValue(event.getHour(true));
        Hour2.getValueFactory().setValue(event.getHour(false));
        Min1.getValueFactory().setValue(event.getMinute(true));
        Min2.getValueFactory().setValue(event.getMinute(false));
        currentEvent=event;
    }

    @FXML
    void onSaveButtonPress(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime sDate,eDate;
        String eName,Desc;
        eName = EventNameField.getText();
        Desc = DescArea.getText();
        String formattedHour1 = String.format("%02d", Hour1.getValue());
        String formattedHour2 = String.format("%02d", Hour2.getValue());
        String formattedMin1 = String.format("%02d", Min1.getValue());
        String formattedMin2 = String.format("%02d", Min2.getValue());
        sDate = LocalDateTime.parse(StartDateField.getValue()+" "+formattedHour1+":"+formattedMin1+":00",formatter);
        eDate = LocalDateTime.parse(EndDateField.getValue()+" "+formattedHour2+":"+formattedMin2+":00",formatter);
        System.out.println(sDate);
        System.out.println(eDate);
        if(DatabaseComm.updateEvent(currentEvent)==0){
            //DatabaseComm.commitQueries();
            currentEvent.setNume(eName);
            currentEvent.setDescription(Desc);
            currentEvent.setStartDatePrivate(sDate);
            currentEvent.setEndDatePrivate(eDate);
            DatabaseComm.updateEvent(currentEvent);
            eventEditPane.setVisible(false);
            eventmanagerpane.setVisible(true);
        }
        else{
            System.out.println("Error occured.");
        }
    }

    @FXML
    void onBackButtonPress(){
        eventEditPane.setVisible(false);
        eventmanagerpane.setVisible(true);
    }

    public User currentUserGlobal=Session.getInstance().getUser();



    public void onEventManagementButtonPress(javafx.scene.input.MouseEvent mouseEvent) {
        eventManagerTableView.getItems().clear();
        eventViewPane.setVisible(false);
        eventEditPane.setVisible(false);
        mainmenu.setVisible(false);
        eventmanagerpane.setVisible(true);
        System.out.println(eventmanagerpane.getChildren());
        eventManagerTableView.setRowFactory(tv -> {
            TableRow<EventModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    EventModel rowData = row.getItem();
                    eventmanagerpane.setVisible(false);
                    eventEditPane.setVisible(true);
                    setEventData(rowData);
                    System.out.println("Double click on: "+rowData.getNume());
                }
            });
            return row ;
        });
        //eventModels.add(new EventModel( 2,"Event 2","Test event description", LocalDateTime.now(),LocalDateTime.now(),userList,50));
        System.out.println("Current id:"+currentUserGlobal.getId());
        eventManagerModels=DatabaseComm.refreshlist(currentUserGlobal.getId(),true);
        eventID1.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
        eventName1.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        participantsNum1.setCellValueFactory(new PropertyValueFactory<>("Limit"));
        startDate1.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
        endDate1.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
        eventManagerTableView.setItems(eventManagerModels);
        //eventManagerTableView.refresh();
        System.out.println("Complete.");
    }
    public void onHomeButtonPress(javafx.scene.input.MouseEvent mouseEvent) {
        eventViewPane.setVisible(false);
        eventEditPane.setVisible(false);
        eventmanagerpane.setVisible(false);
        mainmenu.setVisible(true);
    }
    public void onCalendarButtonPress(javafx.scene.input.MouseEvent mouseEvent) {
        mainmenu.setVisible(false);
        eventViewPane.setVisible(false);
        eventEditPane.setVisible(false);
        eventmanagerpane.setVisible(false);
        // NOT DONE YET, ONLY HAS THE EVENTMANAGER SETVISIBLE
        //eventmanagerpane.getChildren();
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
