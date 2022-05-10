package genericclasses;


import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Event {
    int id;
    String nume,description;
    LocalDateTime startdate;
    LocalDateTime enddate;
    ArrayList<User> userlist;
    User creator;
    int limit;
    public Event(){
        id=1;
        nume="Ion";
        description="test";
        startdate=LocalDateTime.now();
        enddate=LocalDateTime.parse("2023-05-20T15:15:00");
        limit=50;
        ArrayList<User> userlist= new ArrayList<>();
    }

    public Event(String name, String description, LocalDateTime startDate, LocalDateTime endDate, int limit) {
        this.nume = name;
        this.description = description;
        this.startdate = startDate;
        this.enddate = endDate;
        this.limit = limit;
        this.userlist= new ArrayList<>();
    }

    public Event(int id, String name, String description, LocalDateTime startDate, LocalDateTime endDate, int limit) {
        this.id = id;
        this.nume = name;
        this.description = description;
        this.startdate = startDate;
        this.enddate = endDate;
        this.limit = limit;
        this.userlist= new ArrayList<>();
    }

    public String getNume() {
        return nume;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getStartdate() {
        java.sql.Timestamp sqlTime = java.sql.Timestamp.valueOf(startdate);
        return sqlTime;
    }

    public Timestamp getEnddate() {
        java.sql.Timestamp sqlTime = java.sql.Timestamp.valueOf(enddate);
        return sqlTime;
    }

    public LocalDateTime getStartdateLocal() {
        return startdate;
    }

    public LocalDateTime getEnddateLocal() {
        return enddate;
    }

    public int getLimit() {
        return limit;
    }

    public int getId() {
        return id;
    }

    public ArrayList<User> getUserlist() {
        return userlist;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", description='" + description + '\'' +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", userlist=" + userlist +
                ", limit=" + limit +
                '}';
    }
}
