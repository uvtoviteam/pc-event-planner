package genericclasses;


import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class Event {
    int id;
    String nume,description;
    LocalDateTime startdate;
    LocalDateTime enddate;
    ArrayList<User> userlist;
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
}
