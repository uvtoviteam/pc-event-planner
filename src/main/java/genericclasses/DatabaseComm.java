package genericclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.example.eventplanner.EventModel;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseComm {
    private static MysqlDataSource SQLOnLaunch() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root"); // user
        dataSource.setPassword("Cr157124"); //pass
        dataSource.setURL("jdbc:mysql://localhost:3306/event_planner"); //in loc de event_planner, pui ce database ai
        return dataSource;
    }
    public static int register_user(String name, String pass, String email){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "SELECT * FROM USERACCOUNT WHERE email=?";
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                return 3;
            }
            String query2 = "INSERT INTO USERACCOUNT (`NAME`,`PASSWORD`,`EMAIL`) VALUES (?,?,?)";
            stmnt = conn.prepareStatement(query2);
            stmnt.setString(1,name);
            stmnt.setString(2,pass);
            stmnt.setString(3,email);
            stmnt.executeUpdate();

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return 0;
    }
    public static int login_user(String name, String pass){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "SELECT * FROM USERACCOUNT WHERE name=? AND password=?";
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, name);
            stmnt.setString(2, pass);
            ResultSet rs = stmnt.executeQuery();
            if (!rs.next()) {
                return 1;
            }
            return 0;

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return 0;
    }

    // adding an event to the database
    public static int add_event(Event event, int creator){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String queryEvent = "INSERT INTO EVENTS (`NAME`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `CREATOR`, `LIMIT`) VALUES (?, ?, ?, ?, ?, ?)";
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setString(1, event.getNume());
            stmnt.setString(2, event.getDescription());
            stmnt.setTimestamp(3, event.getStartdate());
            stmnt.setTimestamp(4, event.getEnddate());
            //creator here
            stmnt.setInt(5, creator);
            stmnt.setInt(6, event.getLimit());
            stmnt.executeUpdate();

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return 0;
    }

    // registering an event with it's filter in the database
    public static int add_filter(Event event, int filter){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String queryEvent = "INSERT INTO EVENT_FILTER (`EVENT_ID`, `FILTER_ID`) VALUES (?, ?)";
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setInt(1, event.getId());
            stmnt.setInt(2, filter);

            stmnt.executeUpdate();

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return 0;
    }

    // get details of an event
    public static Event getEventDetails(String name){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "SELECT * FROM EVENTS WHERE name = ?";
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, name);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                return new Event(rs.getInt(1), rs.getString(2), rs.getString(5),
                        rs.getDate(3).toLocalDate().atStartOfDay(), rs.getDate(4).toLocalDate().atStartOfDay(), rs.getInt(8));
            }
            return new Event();

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return new Event();
    }

    // get latest event
    public static Event getLatestEvent(){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "SELECT * FROM EVENTS ORDER BY EVENT_ID DESC LIMIT 1";
            stmnt = conn.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                return new Event(rs.getInt(1), rs.getString(2), rs.getString(5),
                        rs.getDate(3).toLocalDate().atStartOfDay(), rs.getDate(4).toLocalDate().atStartOfDay(), rs.getInt(8));
            }
            return new Event();

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return new Event();
    }

    // get user id
    public static User getUserInfo(String user){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "SELECT * FROM USERACCOUNT WHERE NAME = ?";
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, user);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                User newuser=new User(rs.getInt(1),rs.getString(2),rs.getString(4));
                return newuser;
            }
            return null;

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return null;
    }
    public static int getUserId(User user){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "SELECT * FROM USERACCOUNT WHERE NAME = ?";
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, user.getUsername());
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return 0;
    }
    public static int updateEvent(EventModel event){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String queryEvent = "UPDATE EVENTS SET `NAME`=?, `DESCRIPTION`=?, `START_DATE`=?, `END_DATE`=?, `CREATOR`=?, `LIMIT`=? WHERE event_id=?";
            System.out.println(event.getEndDatePrivate());
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setString(1, event.getNume());
            stmnt.setString(2, event.getDescription());
            stmnt.setTimestamp(3, event.getStartDatePrivate());
            stmnt.setTimestamp(4, event.getEndDatePrivate());
            stmnt.setInt(5, event.getCreator().get());
            stmnt.setInt(6, event.getLimit());
            stmnt.setInt(7,event.getID());
            //System.out.println(queryEvent);
            stmnt.executeUpdate();

        }catch(SQLException var11){
            var11.printStackTrace();
            return 1;
        }

        return 0;

    }

    public static int deleteEvent(EventModel event){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String queryEvent = "DELETE FROM events WHERE event_id=?";
            //System.out.println(event.getEndDatePrivate());
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setInt(1, event.getID());
            //System.out.println(queryEvent);
            stmnt.executeUpdate();

        }catch(SQLException var11){
            var11.printStackTrace();
            return 1;
        }

        return 0;

    }

    public static int commitQueries(){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
    public static ObservableList<EventModel> refreshlist(int userId, boolean onlyCreator){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;
        ObservableList<EventModel> Rflist = FXCollections.observableArrayList();

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query;
            if(onlyCreator==false){
                 query = "SELECT * FROM events WHERE end_date >= Sysdate() ";
                 stmnt = conn.prepareStatement(query);}
            else{
                 query = "SELECT * FROM events WHERE creator=?";
                stmnt = conn.prepareStatement(query);
                stmnt.setInt(1, userId);
            };

            //stmnt.setString(2, pass);
            ResultSet rs = stmnt.executeQuery();
            ResultSet rs2;
            int id, limit ;
            int creator, status;
            String name , description;
            LocalDateTime sdate , edate ;
            ArrayList<User> Luser = new ArrayList<>();

            while(rs.next()){

                id = rs.getInt("event_id");
                creator = rs.getInt("creator");
                name = rs.getString("name");
                description = rs.getString("description");
                sdate =  LocalDateTime.parse(rs.getString("start_date"),formatter);
                edate = LocalDateTime.parse(rs.getString("end_date"),formatter);
                limit = rs.getInt("limit");

                query = "SELECT user_id FROM enrolments WHERE event_id =?";

                stmnt = conn.prepareStatement(query);
                stmnt.setInt(1,id);
                rs2 = stmnt.executeQuery();
                while(rs2.next()){
                    //Aici se aduga idurile;


                };
                System.out.println("Event:"+name);
                Rflist.add(new EventModel(id,name ,description,sdate ,edate, Luser, limit,creator));
            }


        }catch(SQLException var11){
            var11.printStackTrace();
        }
        System.out.println(Rflist.size());
        return Rflist ;

    }

    // get all events
    public static ArrayList<Event> getAllEvents(){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;
        ArrayList<Event> events = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "SELECT * FROM EVENTS  WHERE END_DATE > Sysdate()";
            stmnt = conn.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                events.add(new Event(rs.getInt(1), rs.getString(2), rs.getString(5),
                        LocalDateTime.parse(rs.getString(3), formatter),  LocalDateTime.parse(rs.getString(4), formatter), rs.getInt(8)));
            }
            return events;

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return events;
    }

    public static ArrayList<Event> getMyEvents(int creator){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;
        ArrayList<Event> events = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "SELECT * FROM EVENTS  WHERE CREATOR = " + creator;
            stmnt = conn.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                events.add(new Event(rs.getInt(1), rs.getString(2), rs.getString(5),
                        LocalDateTime.parse(rs.getString(3), formatter),  LocalDateTime.parse(rs.getString(4), formatter), rs.getInt(8)));
            }
            return events;

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return events;
    }

    public static ObservableList<EventModel> attendingEvents(int userId){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;
        ObservableList<EventModel> Rflist = FXCollections.observableArrayList();

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query;

            query = "SELECT * FROM events JOIN enrolments WHERE events.event_id = enrolments.event_id AND enrolments.user_id = " + userId + " AND events.end_date >= Sysdate() ";
            stmnt = conn.prepareStatement(query);

            //stmnt.setString(2, pass);
            ResultSet rs = stmnt.executeQuery();
            ResultSet rs2;
            int id, limit ;
            int creator, status;
            String name , description;
            LocalDateTime sdate , edate ;
            ArrayList<User> Luser = new ArrayList<>();

            while(rs.next()){

                id = rs.getInt("event_id");
                creator = rs.getInt("creator");
                name = rs.getString("name");
                description = rs.getString("description");
                sdate =  LocalDateTime.parse(rs.getString("start_date"),formatter);
                edate = LocalDateTime.parse(rs.getString("end_date"),formatter);
                limit = rs.getInt("limit");

                Rflist.add(new EventModel(id,name ,description,sdate ,edate, Luser, limit,creator));
            }


        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return Rflist ;

    }

    // get all events
    public static String getTags(int event){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;
        ArrayList<Event> events = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String result = "";

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        int OK = 0;

        try {
            String query = "SELECT DISTINCT FILTER_ID FROM EVENT_FILTER  WHERE EVENT_ID = " + event;
            stmnt = conn.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                int filter = rs.getInt("filter_id");
                String filterName = "";

                if(filter == 1)
                    filterName = "Daytime";
                else if(filter == 2)
                    filterName = "Nighttime";
                else if(filter == 3)
                    filterName = "Weekend";
                else if(filter == 4)
                    filterName = "Formal";
                else if(filter == 5)
                    filterName = "Casual";
                else if(filter == 6)
                    filterName = "Sport";
                else if(filter == 7)
                    filterName = "Charity";

                if(OK == 1)
                    result = result + " | " + filterName;
                else
                    result = result + filterName;
                OK = 1;
            }
            return result;

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return result;
    }

    // get filtered events
    public static ArrayList<Event> getFilteredEvents(int filter){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;
        ArrayList<Event> events = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "SELECT * FROM events JOIN event_filter WHERE events.event_id = event_filter.event_id AND filter_id = " + filter;
            stmnt = conn.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                events.add(new Event(rs.getInt(1), rs.getString(2), rs.getString(5),
                        LocalDateTime.parse(rs.getString(3), formatter),  LocalDateTime.parse(rs.getString(4), formatter), rs.getInt(8)));
            }
            return events;

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return events;
    }
}
