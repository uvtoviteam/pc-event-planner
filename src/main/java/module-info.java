module com.example.eventplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.eventplanner to javafx.fxml;
    exports com.example.eventplanner;
}