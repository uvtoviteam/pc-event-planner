package com.example.eventplanner;

import genericclasses.DatabaseComm;
import genericclasses.Event;
import genericclasses.Session;
import genericclasses.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu implements Initializable {
    @FXML
    Button TestEventButton, interestedButton, LogoutButton,CalendarButton,NotifButton,SettingsButton,CreateEventButton,RefreshButton,EventManagementButton;

    @FXML
    TextField SearchField, SearchField1;

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
    TableView<NotificationModel> notificationsTableView= new TableView<>();

    @FXML
    TableView<UserModel> UserTable = new TableView<>();

    @FXML
    public TableColumn<Event, Integer> eventID,eventID1, eventID11;
    @FXML
    public TableColumn<Event,String> eventName,eventName1,eventName11;

    @FXML
    public TableColumn<Event, Integer> participantsNum,participantsNum1,participantsNum11;

    @FXML
    public TableColumn<Event, LocalDateTime> startDate,startDate1,startDate11;

    @FXML
    public TableColumn<Event, LocalDateTime> endDate,endDate1,endDate11;

    @FXML
    public TableColumn<User, Integer> userID;
    @FXML
    public TableColumn<User,String> userName;

    List<Event> eventList=new ArrayList<>();

    ArrayList<User> userList=new ArrayList<>();

    EventModel testEvent;

    public void setButtonClass(){
       /* Button buttonsarr[]={TestEventButton, LogoutButton,CalendarButton,NotifButton,SettingsButton,CreateEventButton,RefreshButton,EventManagementButton};
        List<Button> buttons;
        buttons=Arrays.asList(buttonsarr);
        Session.ButtonConfig(buttons);*/
    }

    private void addUser(){
        userList.add(new User(2, "Ion", "ion.gmail.com", 1));
    }


    private ObservableList<EventModel> eventModels = FXCollections.observableArrayList(
            new EventModel( 1," SLjazzing","Este vorba despre o plimbare muzicala cu Tramvaiul Turistic ce strabate orasul de pe Bega, totul pe acorduri Jazzy, așa cum v-am obișnuit.",LocalDateTime.now(),LocalDateTime.now(),new ArrayList<User>(), 50,"Bega River")
            );
    private ObservableList<EventModel> eventManagerModels = FXCollections.observableArrayList();
    private ObservableList<EventModel> eventManagerModels2 = FXCollections.observableArrayList();
    private ObservableList<NotificationModel> notificationModels = FXCollections.observableArrayList();
    private ObservableList<UserModel> userModels = FXCollections.observableArrayList();

    @FXML
    protected void onTestEvent(){
        addUser();
        eventModels.add(new EventModel( 2,"Street Food Festival","Dacă îți dorești un prânz/ o cină în aer liber, sau doar vrei să ieși la o băutură rece alături de prieteni, la Iulius Town vei putea face asta, iar noi te așteptăm cu brațele deschise!",LocalDateTime.now(),LocalDateTime.now(),userList,50,"Some location"));
      eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
      eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
      participantsNum.setCellValueFactory(new PropertyValueFactory<>("Limit"));
      startDate.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
      endDate.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
      eventTableView.setItems(eventModels);
    }

    @FXML
    private AnchorPane mainmenu, eventmanagerpane,eventEditPane,eventViewPane, createPane, mainmenu2, sidebar, sidebar1,settingsPane, notificationsPane;

    @FXML
    protected void onCreateEventButtonClick(){
        eventmanagerpane.setVisible(false);
        createPane.setVisible(true);
    }

    @FXML
    Label notificationCount;

    @FXML
    Circle notificationBubble;

    @FXML
    protected void onNotifButtonClick(){
        eventViewPane.setVisible(false);
        eventEditPane.setVisible(false);
        eventmanagerpane.setVisible(false);
        mainmenu.setVisible(false);
        mainmenu2.setVisible(false);
        settingsPane.setVisible(false);
        notificationsPane.setVisible(true);
    }
    @FXML
    protected void onRefreshButtonClick(){
        int currentUser = Session.getInstance().getUser().getId();
        eventTableView.getItems().clear();
        eventModels = DatabaseComm.refreshlist(currentUser,false);
        eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
        eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        participantsNum.setCellValueFactory(new PropertyValueFactory<>("Limit"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
        eventTableView.setItems(eventModels);
    }

    @FXML
    protected void onCreatorRefreshButtonClick(){
        int currentUser = Session.getInstance().getUser().getId();
        eventManagerTableView.getItems().clear();
        eventManagerModels = DatabaseComm.refreshlist(currentUser,true);
        eventID1.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
        eventName1.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        participantsNum1.setCellValueFactory(new PropertyValueFactory<>("Limit"));
        startDate1.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
        endDate1.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
        eventManagerTableView.setItems(eventManagerModels);
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
    protected void onSettingsButtonClick(){
        eventViewPane.setVisible(false);
        eventEditPane.setVisible(false);
        eventmanagerpane.setVisible(false);
        mainmenu.setVisible(false);
        mainmenu2.setVisible(false);
        notificationsPane.setVisible(false);
        settingsPane.setVisible(true);
    }

    @FXML
    protected void onCalendarButtonClick(){

    }

    @FXML
    private Text descEvent,end,start,idEvent,nameEvent,participants, tag1;


    public void fillEventPane(EventModel eventval){
        //idevent isn't selected
        idEvent.setText(String.valueOf(eventval.getLocation()));
        nameEvent.setText(eventval.getNume());
        descEvent.setText(eventval.getDescription());
        start.setText(eventval.getStartdate());
        end.setText(eventval.getEnddate());
        participants.setText(DatabaseComm.checkParticipants(eventval.getID()) +" / "+ eventval.getLimit());
        tag1.setText(DatabaseComm.getTags(eventval.getID()));
    }

    @FXML
    Button backEventButton,joinEventButton;

    @FXML
    protected void onBackEventPressed(){
        eventViewPane.setVisible(false);
        mainmenu.setVisible(true);
        joinEventButton.setDisable(false);
        joinEventButton.setText("Join");
        interestedButton.setVisible(true);
    }
    @FXML
    protected void onJoinEventPressed(){

        EventModel eventSelected = eventTableView.getSelectionModel().getSelectedItem();
        testEvent=eventSelected;

        serJoin.start();
        serJoin.setOnSucceeded(e -> { serJoin.reset();});
        System.out.println("passed serjoin");
        //DatabaseComm.add_participant(eventSelected.getID(), Session.getInstance().getUser().getId());
        joinEventButton.setDisable(true);
        joinEventButton.setText("Joined");
        interestedButton.setVisible(false);
    }

    @FXML
    protected void onJoinEventPressedOut(EventModel event){
        EventModel eventSelected = event;
        testEvent=event;
        serJoin.start();
        serJoin.setOnSucceeded(e -> { serJoin.reset();});
        System.out.println("passed serjoin");
       // DatabaseComm.add_participant(eventSelected.getID(), Session.getInstance().getUser().getId());
        joinEventButton.setDisable(true);
        joinEventButton.setText("Joined");
        interestedButton.setVisible(false);
    }

    @FXML
    protected void onInterestedEventPressed(){
        EventModel eventSelected = eventTableView.getSelectionModel().getSelectedItem();
        DatabaseComm.add_interest(eventSelected.getID(), Session.getInstance().getUser().getId());
        interestedButton.setVisible(false);
    }

    @FXML
    protected void onInterestedEventPressedOut(EventModel event){
        EventModel eventSelected = event;
        DatabaseComm.add_interest(eventSelected.getID(), Session.getInstance().getUser().getId());
        interestedButton.setVisible(false);
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

            if(DatabaseComm.checkEnrolment(eventSelected.getID(), Session.getInstance().getUser().getId(), 1) != 0) {
                joinEventButton.setDisable(true);
                joinEventButton.setText("Joined");
                interestedButton.setVisible(false);
            }

            if(DatabaseComm.checkEnrolment(eventSelected.getID(), Session.getInstance().getUser().getId(), 2) != 0)
                interestedButton.setVisible(false);

            if(DatabaseComm.checkParticipants(eventSelected.getID()) >= eventSelected.getLimit() && DatabaseComm.checkEnrolment(eventSelected.getID(), Session.getInstance().getUser().getId(), 1) == 0) {
                joinEventButton.setDisable(true);
                joinEventButton.setText("Full event");
                System.out.println("limit " + eventSelected.getLimit());
                interestedButton.setVisible(false);
            }

            if(Session.getInstance().getUser().getType() == 2) {
                interestedButton.setVisible(false);

                joinEventButton.setVisible(true);
                joinEventButton.setText("Edit");
                joinEventButton.setOnAction((event) -> {
                    eventViewPane.setVisible(false);
                    EventModel rowData = eventTableView.getSelectionModel().getSelectedItem();
                    mainmenu.setVisible(false);
                    eventEditPane.setVisible(true);
                    setEventData(rowData);

                });
                BackButton.setOnAction((event) -> {
                    mainmenu.setVisible(true);
                    eventEditPane.setVisible(false);
                });

            }

            eventViewPane.setVisible(true);
            backEventButton.setOnAction((event) -> {
                onBackEventPressed();
            });
        }
    }

    @FXML
    protected void onMouseClickTable2(){
        //eventTableView.getSelectionModel().getSelectedItem();
        EventModel eventSelected = eventManagerTableView2.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("View_Event.fxml"));
        Scene scene = null;
        if (eventSelected == null) System.out.println("couldn't find event");
        else {
            eventmanagerpane.setVisible(false);
            System.out.println(eventSelected.getID());
            System.out.println(eventSelected.getNume());
            fillEventPane(eventSelected);

            if(DatabaseComm.checkEnrolment(eventSelected.getID(), Session.getInstance().getUser().getId(), 1) != 0) {
                joinEventButton.setDisable(true);
                joinEventButton.setText("Joined");
                interestedButton.setVisible(false);
            }

            if(DatabaseComm.checkEnrolment(eventSelected.getID(), Session.getInstance().getUser().getId(), 2) != 0)
                interestedButton.setVisible(false);

            if(DatabaseComm.checkParticipants(eventSelected.getID()) >= eventSelected.getLimit() && DatabaseComm.checkEnrolment(eventSelected.getID(), Session.getInstance().getUser().getId(), 1) == 0) {
                joinEventButton.setDisable(true);
                joinEventButton.setText("Full event");
                interestedButton.setVisible(false);
            }

            eventViewPane.setVisible(true);
            backEventButton.setOnAction((event) -> {
                onBackEventPressed2();
            });
        }
    }

    @FXML
    protected void onBackEventPressed2(){
        eventViewPane.setVisible(false);
        eventmanagerpane.setVisible(true);
        joinEventButton.setDisable(false);
        joinEventButton.setText("Join");
        interestedButton.setVisible(true);
    }

    @FXML
    protected void onBackEventPressedMan(){
        eventViewPane.setVisible(false);
        mainmenu.setVisible(true);
        joinEventButton.setVisible(false);
        interestedButton.setVisible(false);
    }

    @FXML
    protected void onBackEventPressed3(){
        eventViewPane.setVisible(false);
        mainmenu2.setVisible(true);
        joinEventButton.setDisable(false);
        joinEventButton.setText("Join");
        interestedButton.setVisible(true);
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

    @FXML
    void onDeleteButtonClickUser(ActionEvent event) {
        UserModel selectedUser = UserTable.getSelectionModel().getSelectedItem();
        if(selectedUser!=null){
            if(DatabaseComm.deleteEnrolment(selectedUser) == 0)
            {
                UserTable.getItems().clear();
                userModels = DatabaseComm.getParticipants(eventManagerTableView.getSelectionModel().getSelectedItem().getID());
                UserTable.setItems(userModels);
            }
            else { //error
            }
        }
    }

    public void onEventManagementButtonPress() {
        eventManagerTableView.getItems().clear();
        eventViewPane.setVisible(false);
        eventEditPane.setVisible(false);
        settingsPane.setVisible(false);
        notificationsPane.setVisible(false);
        mainmenu.setVisible(false);
        mainmenu2.setVisible(false);
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
//--------------------------------------------------------------------------------------------------------------------------
                    userModels = DatabaseComm.getParticipants(rowData.getID());
                    userID.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
                    userName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
                    UserTable.setItems(userModels);

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

        eventManagerTableView2.getItems().clear();
        eventManagerModels2 = DatabaseComm.attendingEvents(currentUserGlobal.getId());
        eventID11.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
        eventName11.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        participantsNum11.setCellValueFactory(new PropertyValueFactory<>("Limit"));
        startDate11.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
        endDate11.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
        eventManagerTableView2.setItems(eventManagerModels2);
    }

    public void onHomeButtonPress() {
        eventViewPane.setVisible(false);
        eventEditPane.setVisible(false);
        eventmanagerpane.setVisible(false);
        mainmenu2.setVisible(false);
        notificationsPane.setVisible(false);
        settingsPane.setVisible(false);
        mainmenu.setVisible(true);
    }

    public void onHomeButtonClick() {
        eventViewPane.setVisible(false);
        eventEditPane.setVisible(false);
        eventmanagerpane.setVisible(false);
        mainmenu.setVisible(false);
        notificationsPane.setVisible(false);
        settingsPane.setVisible(false);
        mainmenu2.setVisible(true);
    }

    public void onCalendarButtonPress() {
        mainmenu.setVisible(false);
        eventViewPane.setVisible(false);
        eventEditPane.setVisible(false);
        eventmanagerpane.setVisible(false);
        notificationsPane.setVisible(false);
        settingsPane.setVisible(false);
        mainmenu2.setVisible(false);
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
            resultsTable.add(new EventModel(e.getId(), e.getNume(), e.getDescription(), e.getStartdate().toLocalDateTime(), e.getEnddate().toLocalDateTime(), e.getUserlist(), e.getLimit(), Session.getInstance().getUser().getId(), e.getLocation()));

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
    protected void onSearchMy(){
        String[] keys = SearchField1.getText().split(" ");
        ArrayList<Event> events = DatabaseComm.getMyEvents(Session.getInstance().getUser().getId());

        ArrayList<Event> results = new ArrayList<>();

        for(Event e : events)
            for(String k : keys)
                if(e.getNume().contains(k) || e.getDescription().contains(k)) {
                    results.add(e);
                    continue;
                }

        ObservableList<EventModel> resultsTable = FXCollections.observableArrayList();
        for(Event e : results)
            resultsTable.add(new EventModel(e.getId(), e.getNume(), e.getDescription(), e.getStartdate().toLocalDateTime(), e.getEnddate().toLocalDateTime(), e.getUserlist(), e.getLimit(), Session.getInstance().getUser().getId(), e.getLocation()));

        eventManagerTableView.getItems().clear();
        eventManagerModels = resultsTable;
        eventID1.setCellValueFactory(new PropertyValueFactory<>("ID"));
        eventName1.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        participantsNum1.setCellValueFactory(new PropertyValueFactory<>("Limit"));
        startDate1.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
        endDate1.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
        eventManagerTableView.setItems(eventManagerModels);

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
            resultsTable.add(new EventModel(e.getId(), e.getNume(), e.getDescription(), e.getStartdate().toLocalDateTime(), e.getEnddate().toLocalDateTime(), e.getUserlist(), e.getLimit(), e.getLocation()));

        eventTableView.getItems().clear();
        eventModels = resultsTable;
        eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        participantsNum.setCellValueFactory(new PropertyValueFactory<>("Limit"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
        eventTableView.setItems(eventModels);

    }

    @FXML Label welcome, title1, title2, title3, title4, title5, title6, title7, title8, title9, desc1, desc2, desc3, desc4, desc5, desc6, desc7, desc8, desc9;

    ObservableList<EventModel> popular = DatabaseComm.popularEvents();
    ObservableList<EventModel> interest = DatabaseComm.interestEvents(Session.getInstance().getUser().getId());

    @FXML
    Button BackButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(Session.getInstance().getUser().getType() == 2) {
            mainmenu.setVisible(true);
            mainmenu2.setVisible(false);
            sidebar.setVisible(false);
            notificationsPane.setVisible(false);
            settingsPane.setVisible(false);
            sidebar1.setVisible(true);

            joinEventButton.setVisible(true);
            joinEventButton.setText("Edit");
            joinEventButton.setOnAction((event) -> {
                eventViewPane.setVisible(false);
                EventModel rowData = eventTableView.getSelectionModel().getSelectedItem();
                mainmenu.setVisible(false);
                eventEditPane.setVisible(true);
                setEventData(rowData);

            });
            BackButton.setOnAction((event) -> {
                mainmenu.setVisible(true);
                eventEditPane.setVisible(false);
            });

            interestedButton.setVisible(false);

        }

        filters.setItems(filtersName);
        filters.setValue("All Events");

        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryHs = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryMs = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryHe = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryMe = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        startH.setValueFactory(valueFactoryHs);
        endH.setValueFactory(valueFactoryHe);
        startM.setValueFactory(valueFactoryMs);
        endM.setValueFactory(valueFactoryMe);

        startH.getValueFactory().setValue(12);
        endH.getValueFactory().setValue(12);
        startM.getValueFactory().setValue(0);
        endM.getValueFactory().setValue(0);

        ArrayList<Event> events = DatabaseComm.getPopularEvents();
        title1.setText(events.get(0).getNume());
        desc1.setText(events.get(0).getDescription());

        title2.setText(events.get(1).getNume());
        desc2.setText(events.get(1).getDescription());

        title3.setText(events.get(2).getNume());
        desc3.setText(events.get(2).getDescription());

        title4.setText(events.get(3).getNume());
        desc4.setText(events.get(3).getDescription());

        title5.setText(events.get(4).getNume());
        desc5.setText(events.get(4).getDescription());

        title6.setText(events.get(5).getNume());
        desc6.setText(events.get(5).getDescription());

        title7.setText(interest.get(0).getNume());
        desc7.setText(interest.get(0).getDescription());

        title8.setText(interest.get(1).getNume());
        desc8.setText(interest.get(1).getDescription());

        title9.setText(interest.get(2).getNume());
        desc9.setText(interest.get(2).getDescription());

       // welcome.setText("Welcome " + Session.getInstance().getUser().getEmail() + "!");

       // NotifButton.getStyleClass().add("icon-button");
      //  NotifButton.setPickOnBounds(true);

        User_name.setText(Session.getInstance().getUser().getUsername());
        Email.setText(Session.getInstance().getUser().getEmail());
        User_name.setEditable(false);
        Password.setEditable(false);
        Email.setEditable(false);
        Confirm_password.setEditable(false);

        notificationCount.setVisible(false);
        notificationCount.setText("0");
        notificationBubble.setVisible(false);

    }

    protected void viewEv(EventModel eventG) {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("View_Event.fxml"));
        Scene scene = null;
        mainmenu2.setVisible(false);
        fillEventPane(eventG);
        if(DatabaseComm.checkEnrolment(eventG.getID(), Session.getInstance().getUser().getId(), 1) != 0) {
            joinEventButton.setDisable(true);
            joinEventButton.setText("Joined");
            interestedButton.setVisible(false);
        }
        if(DatabaseComm.checkEnrolment(eventG.getID(), Session.getInstance().getUser().getId(), 2) != 0)
            interestedButton.setVisible(false);
        eventViewPane.setVisible(true);
        backEventButton.setOnAction((event) -> {
            onBackEventPressed3();
        });
        joinEventButton.setOnAction((event) -> {
            onJoinEventPressedOut(eventG);
        });
        interestedButton.setOnAction((event) -> {
            onInterestedEventPressedOut(eventG);
        });

    }

    @FXML
    protected void view1() { viewEv(popular.get(0)); }

    @FXML
    protected void view2() { viewEv(popular.get(1)); }

    @FXML
    protected void view3() { viewEv(popular.get(2)); }

    @FXML
    protected void view4() { viewEv(popular.get(3)); }

    @FXML
    protected void view5() { viewEv(popular.get(4)); }

    @FXML
    protected void view6() { viewEv(popular.get(5)); }

    @FXML
    protected void view7() { viewEv(interest.get(0)); }

    @FXML
    protected void view8() { viewEv(interest.get(1)); }

    @FXML
    protected void view9() { viewEv(interest.get(2)); }

    @FXML
    private TextField nameField, participantNumber, locationField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private DatePicker startDate2, endDate2;

    @FXML
    private Button cancelButton, createButton;

    @FXML
    private Label errorLabel;

    @FXML
    private CheckBox daytimeCheck, nighttimeCheck, weekendCheck, formalCheck, casualCheck, sportsCheck, charityCheck;

    @FXML
    private Spinner<Integer> startH = new Spinner<>();

    @FXML
    private Spinner<Integer> startM = new Spinner<>();

    @FXML
    private Spinner<Integer> endH = new Spinner<>();

    @FXML
    private Spinner<Integer> endM = new Spinner<>();



    public String twoDigits(int x) {
        if(x < 10)
            return "0" + x;
        else
            return "" + x;
    }

    @FXML
    protected void onCreateButtonClick() {
        String name = nameField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();
        int numberp = 0;
        LocalDate sd = startDate2.getValue(), ed = endDate2.getValue();
        LocalDateTime sDate, eDate;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if(!participantNumber.getText().isEmpty())
            numberp = Integer.parseInt(participantNumber.getText());

        int allOK = 1;
        if(name.isEmpty() || description.isEmpty() || location.isEmpty() || participantNumber.getText().isEmpty() || sd == null || ed == null) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Please fill all fields!");
            allOK = 0;
        } else {
            if(sd.isAfter(ed)) {
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Please enter a valid time period!");
                allOK = 0;
            }
        }

        // get the creator id
        User creator = Session.getInstance().getUser();
        int creatorId = Session.getInstance().getUser().getId();

        if(allOK == 1) {
            sDate = LocalDateTime.parse(sd + " " + twoDigits(startH.getValue()) + ":" + twoDigits(startM.getValue()) + ":00", formatter);
            eDate = LocalDateTime.parse(ed + " " + twoDigits(endH.getValue()) + ":" + twoDigits(endM.getValue()) + ":00", formatter);

            Event newEvent = new Event(name, description, sDate, eDate, numberp, location);
            int code = DatabaseComm.add_event(newEvent, creatorId);

            if (code == 0) {
                Event event = DatabaseComm.getLatestEvent();
                //  System.out.println("latest: " + event);

                // add the filters
                if(daytimeCheck.isSelected())
                    DatabaseComm.add_filter(event, 1);
                if(nighttimeCheck.isSelected())
                    DatabaseComm.add_filter(event, 2);
                if(weekendCheck.isSelected())
                    DatabaseComm.add_filter(event, 3);
                if(formalCheck.isSelected())
                    DatabaseComm.add_filter(event, 4);
                if(casualCheck.isSelected())
                    DatabaseComm.add_filter(event, 5);
                if(sportsCheck.isSelected())
                    DatabaseComm.add_filter(event, 6);
                if(charityCheck.isSelected())
                    DatabaseComm.add_filter(event, 7);

                createPane.setVisible(false);
                eventmanagerpane.setVisible(true);
                //move to main menu
            } else {
                //Error message
                System.out.println("Couldn't create event");
            }
        }
    }

    @FXML
    protected void onCancelButtonClick() {
        createPane.setVisible(false);
        eventmanagerpane.setVisible(true);
    }

    @FXML
    public TableColumn<NotificationModel, Integer> idCell;
    @FXML
    public TableColumn<NotificationModel,String> messageCell;

    @FXML
    public TableColumn<NotificationModel, String> fromCell;

    @FXML
    public TableColumn<NotificationModel, NotificationModel> buttonCell;

    SimpleObjectProperty<Service<Void>> ser = new SimpleObjectProperty<>(this, "ser", new Service<Void>() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                    idCell.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
                    messageCell.setCellValueFactory(new PropertyValueFactory<>("NotifDesc"));
                    fromCell.setCellValueFactory(new PropertyValueFactory<>("User_from"));
                    buttonCell.setCellValueFactory(
                            param -> new ReadOnlyObjectWrapper<>(param.getValue())
                    );
                    buttonCell.setCellFactory(param -> new TableCell<NotificationModel, NotificationModel>() {
                        private final Button acceptButton = new Button("A");
                        private final Button deleteButton = new Button("D");
                        private final Button markButton = new Button("R");

                        final HBox pane = new HBox(acceptButton, markButton, deleteButton);

                        @Override
                        protected void updateItem(NotificationModel model, boolean empty) {
                            super.updateItem(model, empty);

                            if (model == null) {
                                setGraphic(null);
                                return;
                            }

                            setGraphic(pane);
                            deleteButton.setOnMouseClicked(
                                    event -> {
                                        if (
                                                DatabaseComm.deleteNotification(model) == 0)
                                            getTableView().getItems().remove(model);
                                        else {// error
                                        }
                                    }
                            );
                            acceptButton.setOnMouseClicked(
                                    event -> {
                                        if (DatabaseComm.acceptNotification(model) == 0) {
                                            DatabaseComm.deleteNotification(model);
                                            getTableView().getItems().remove(model);
                                            System.out.println("Accepted.");
                                        }
                                    }
                            );
                            markButton.setOnMouseClicked(
                                    event -> {
                                        System.out.println("Marked.");
                                        if (DatabaseComm.markNotification(model) == 0) {
                                            if (model.getStatus() == 1) {
                                                model.setStatus(2);
                                            } else model.setStatus(1);
                                        }
                                        // Change look of row based on status (1 as invite/not read, 2 as invite/read)
                                    }
                            );
                        }
                    });
                    while (true) {
                        synchronized (this) {
                            try {
                                wait(60000);
                                if (isCancelled()) {
                                    break;
                                } else {
                                    notificationModels = DatabaseComm.refreshNotiflist(currentUserGlobal.getId(), true);
                                    System.out.println(notificationModels);
                                    notificationsTableView.getItems().clear();
                                    notificationsTableView.setItems(notificationModels);

                                    int n;
                                    n = notificationModels.size();
                                    if (n > 0) {

                                        KeyFrame update = new KeyFrame(Duration.seconds(0.5), event -> {
                                            notificationCount.setText("" + n);
                                        });
                                        Timeline tl = new Timeline(update);
                                        tl.setCycleCount(Timeline.INDEFINITE);
                                        tl.play();

                                        notificationCount.setVisible(true);
                                        notificationBubble.setVisible(true);

                                    } else {
                                        KeyFrame update = new KeyFrame(Duration.seconds(0.5), event -> {
                                            notificationCount.setText("0");
                                        });
                                        Timeline tl = new Timeline(update);
                                        tl.setCycleCount(Timeline.INDEFINITE);
                                        tl.play();
                                        notificationCount.setVisible(false);
                                        notificationBubble.setVisible(false);
                                    }

                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        System.out.println("Refreshed after 60 seconds");
                    }
                    return null;
                }
            };
        }
    });

    Service<Void> serJoin = new Service<Void>() {
        @Override protected Task createTask() {
            return new Task<Void>() {
                @Override protected Void call() throws InterruptedException {
                        synchronized(this){
                            System.out.println("started!!!");
                                    DatabaseComm.add_participant(testEvent.getID(), Session.getInstance().getUser().getId());
                        }
                    System.out.println("Serjoin complete??");
                    // You code you want to execute in service backgroundgoes here
                    //return null;
                    return null;
                }
            };
        }
    };
    public Service startBackgroundService(){
//        ser.setOnSucceeded((WorkerStateEvent event) -> {
//            // Anything which you want to update on javafx thread (GUI) after completion of background process.
//        });
        ser.get().start();
        return ser.get();
    }

    //SETTINGS PANE
    @FXML
    TextField User_name,Password,Email,Confirm_password;

    Alert alert=null;

    private boolean validateemail(String email){

        Pattern p = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`"
                + "{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher m = p.matcher(email);
        return m.find() && m.group().equals(email);
    }

    private boolean validatepassword(String pass){
        Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
        Matcher m = p.matcher(pass);
        return m.find() && m.group().equals(pass);
    }

    @FXML
    void editar(ActionEvent event) {
        User_name.setEditable(true);
        Password.setEditable(true);
        Email.setEditable(true);
        Confirm_password.setEditable(true);
        salvar.setVisible(true);

    }

    @FXML
    Button salvar, editar;

    @FXML
    protected void salvar(ActionEvent event) {

        editar.setVisible(true);
        salvar.setVisible(false);

        String userName= User_name.getText();
        String email= Email.getText();
        String password= Password.getText();
        String password1= Confirm_password.getText();

        if (userName.trim().equals(""))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Please fill the username field!");
            alert.showAndWait();
        }

        else if(email.trim().equals(""))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Please fill the email field!");
            alert.showAndWait();
        }
        else if(!validateemail(email))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Please enter a valid email");
            alert.showAndWait();
        }
        else if(!validatepassword(password))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Please enter a valid password");
            alert.showAndWait();
        }
        else if(!password.equals(password1))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Your password don't match");
            alert.showAndWait();
        }
        User tempUser = new User(Session.getInstance().getUser().getId(), email,userName,Session.getInstance().getUser().getType(), password);
        if(DatabaseComm.updateSetting(tempUser)==0){
            Session.getInstance().getUser().setUsername(userName);
            Session.getInstance().getUser().setEmail(email);
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("An error has occurred during the update!");
            alert.showAndWait();
        }

        User_name.setEditable(false);
        Password.setEditable(false);
        Email.setEditable(false);
        Confirm_password.setEditable(false);
    }



}
