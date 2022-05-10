module com.example.eventplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires java.sql;
    requires mysql.connector.java;
    requires GMapsFX;
    requires java.desktop;


    opens com.example.eventplanner to javafx.fxml;
    exports com.example.eventplanner;
}