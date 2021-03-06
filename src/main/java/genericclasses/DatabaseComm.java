package genericclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.example.eventplanner.EventModel;
import com.example.eventplanner.NotificationModel;
import com.example.eventplanner.UserModel;
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
            String query2 = "INSERT INTO USERACCOUNT (`NAME`,`PASSWORD`,`EMAIL`, `TYPE`) VALUES (?,?,?, 1)";
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

            Session.getInstance().setUser(new User(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getInt(5)));

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
            String queryEvent = "INSERT INTO EVENTS (`NAME`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `CREATOR`, `LIMIT`, `LOCATION`) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setString(1, event.getNume());
            stmnt.setString(2, event.getDescription());
            stmnt.setTimestamp(3, event.getStartdate());
            stmnt.setTimestamp(4, event.getEnddate());
            //creator here
            stmnt.setInt(5, creator);
            stmnt.setInt(6, event.getLimit());
            stmnt.setString(7, event.getLocation());
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
                        rs.getDate(3).toLocalDate().atStartOfDay(), rs.getDate(4).toLocalDate().atStartOfDay(), rs.getInt(8), rs.getString(9));
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
                        rs.getDate(3).toLocalDate().atStartOfDay(), rs.getDate(4).toLocalDate().atStartOfDay(), rs.getInt(8), rs.getString(9));
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
                User newuser=new User(rs.getInt(1),rs.getString(4),rs.getString(2), rs.getInt(5));
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
            String queryEvent = "UPDATE EVENTS SET `NAME`=?, `DESCRIPTION`=?, `START_DATE`=?, `END_DATE`=?/*, `CREATOR`=?*/, `LIMIT`=? WHERE event_id=?";
            System.out.println(event.getEndDatePrivate());
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setString(1, event.getNume());
            stmnt.setString(2, event.getDescription());
            stmnt.setTimestamp(3, event.getStartDatePrivate());
            stmnt.setTimestamp(4, event.getEndDatePrivate());
          //  stmnt.setInt(5, event.getCreator().get());
            stmnt.setInt(6-1, event.getLimit());
            stmnt.setInt(7-1,event.getID());
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
            }

            //stmnt.setString(2, pass);
            ResultSet rs = stmnt.executeQuery();
            ResultSet rs2,rs3;
            int id, limit ;
            int creator, status;
            String name , description, location;
            LocalDateTime sdate , edate ;
            ArrayList<User> Luser = new ArrayList<>();
            int userid,type;
            String uname,umail;

            while(rs.next()){

                id = rs.getInt("event_id");
                creator = rs.getInt("creator");
                name = rs.getString("name");
                description = rs.getString("description");
                sdate =  LocalDateTime.parse(rs.getString("start_date"),formatter);
                edate = LocalDateTime.parse(rs.getString("end_date"),formatter);
                limit = rs.getInt("limit");
                location = rs.getString("location");

                query = "SELECT user_id,name,email,u.type FROM enrolments as e INNER JOIN useraccount as u on user_id=id WHERE event_id = ?";

                stmnt = conn.prepareStatement(query);
                stmnt.setInt(1,id);
                rs2 = stmnt.executeQuery();

                while(rs2.next()){
                    userid=rs2.getInt("user_id");
                    type=rs2.getInt("type");
                    uname=rs2.getString("name");
                    umail=rs2.getString("email");
                    Luser.add(new User(userid,umail,uname,type));

                }
                System.out.println("Event:"+name);
                Rflist.add(new EventModel(id,name ,description,sdate ,edate, Luser, limit,creator, location));
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
                        LocalDateTime.parse(rs.getString(3), formatter),  LocalDateTime.parse(rs.getString(4), formatter), rs.getInt(8), rs.getString(9)));
            }
            return events;

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return events;
    }

    public static ArrayList<Event> getPopularEvents(){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;
        ArrayList<Event> events = new ArrayList<>();
        Event dummy = new Event();
        dummy.setNume("Event not found");
        dummy.setDescription("");
        for(int i=0; i<6; i++)
            events.add(dummy);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "SELECT events.* , count(enrolments.event_id) as participants FROM EVENTS left JOIN enrolments on events.event_id = enrolments.event_id group by events.event_id\n" +
                    "/*where END_DATE > Sysdate()*/\n" +
                    "ORDER BY participants DESC LIMIT 6;";
            stmnt = conn.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            int p = 0;
            while (rs.next()) {
                events.add(p, new Event(rs.getInt(1), rs.getString(2), rs.getString(5),
                        LocalDateTime.parse(rs.getString(3), formatter),  LocalDateTime.parse(rs.getString(4), formatter), rs.getInt(8), rs.getString(9)));
                p++;
            }
            return events;

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return events;
    }

    public static ArrayList<Event> getInterestEvents(){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;
        ArrayList<Event> events = new ArrayList<>();
        Event dummy = new Event();
        dummy.setNume("Event not found");
        dummy.setDescription("");
        for(int i=0; i<6; i++)
            events.add(dummy);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "SELECT events.* , count(enrolments.event_id) as participants FROM EVENTS left JOIN enrolments on events.event_id = enrolments.event_id group by events.event_id\n" +
                    "/*where END_DATE > Sysdate()*/\n" +
                    "ORDER BY participants DESC LIMIT 6;";
            stmnt = conn.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            int p = 0;
            while (rs.next()) {
                events.add(p, new Event(rs.getInt(1), rs.getString(2), rs.getString(5),
                        LocalDateTime.parse(rs.getString(3), formatter),  LocalDateTime.parse(rs.getString(4), formatter), rs.getInt(8), rs.getString(9)));
                p++;
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
                        LocalDateTime.parse(rs.getString(3), formatter),  LocalDateTime.parse(rs.getString(4), formatter), rs.getInt(8), rs.getString(9)));
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
        System.out.println(userId+" Happened");

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query;

            query = "SELECT * FROM events JOIN enrolments WHERE events.event_id = enrolments.event_id AND enrolments.user_id = " + userId + " AND events.end_date >= Sysdate() /*AND enrolments.type = 1*/ GROUP BY events.event_id ";
            stmnt = conn.prepareStatement(query);

            //stmnt.setString(2, pass);
            ResultSet rs = stmnt.executeQuery();
            ResultSet rs2;
            int id, limit ;
            int creator, status;
            String name , description, location;
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
                location = rs.getString("location");

                Rflist.add(new EventModel(id,name ,description,sdate ,edate, Luser, limit,creator, location));
            }


        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return Rflist ;

    }

    public static ObservableList<EventModel> popularEvents(){
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
            String query = "SELECT events.* , count(enrolments.event_id) as participants FROM EVENTS left JOIN enrolments on events.event_id = enrolments.event_id group by events.event_id\n" +
                    "/*where END_DATE > Sysdate()*/\n" +
                    "ORDER BY participants DESC LIMIT 6;";
            stmnt = conn.prepareStatement(query);

            //stmnt.setString(2, pass);
            ResultSet rs = stmnt.executeQuery();
            ResultSet rs2;
            int id, limit ;
            int creator, status;
            String name , description, location;
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
                location = rs.getString("location");

                Rflist.add(new EventModel(id,name ,description,sdate ,edate, Luser, limit,creator, location));
            }


        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return Rflist ;

    }

    public static ObservableList<EventModel> interestEvents(int user){
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

        ArrayList<Integer> filters = getFilters(user);

        for(int f : filters) {

            try {
                String query = "SELECT events.*, event_filter.filter_id FROM EVENTS left JOIN event_filter on events.event_id = event_filter.event_id  where filter_id = " + f + " group by events.event_id limit 1";
                stmnt = conn.prepareStatement(query);

                ResultSet rs = stmnt.executeQuery();
                int id, limit;
                int creator, status;
                String name, description, location;
                LocalDateTime sdate, edate;
                ArrayList<User> Luser = new ArrayList<>();

                while (rs.next()) {

                    id = rs.getInt("event_id");
                    creator = rs.getInt("creator");
                    name = rs.getString("name");
                    description = rs.getString("description");
                    sdate = LocalDateTime.parse(rs.getString("start_date"), formatter);
                    edate = LocalDateTime.parse(rs.getString("end_date"), formatter);
                    limit = rs.getInt("limit");
                    location = rs.getString("location");

                    Rflist.add(new EventModel(id, name, description, sdate, edate, Luser, limit, creator, location));
                }


            } catch (SQLException var11) {
                var11.printStackTrace();
            }
        }

        Collections.shuffle(Rflist);
        for(EventModel r : Rflist){
            System.out.println(r);
        }

        for(int i=0; i<Rflist.size()-1; i++) {
            for(int j=i+1; j<Rflist.size(); j++) {
                if(Rflist.get(i).getID() == Rflist.get(j).getID())
                    Rflist.remove(j);
            }
        }
        System.out.println("------------");
        for(EventModel r : Rflist){
            System.out.println(r);
        }

        if(Rflist.size() < 3) {
            Rflist = popularEvents();
            Collections.shuffle(Rflist);
        }

        return Rflist ;

    }

    public static ObservableList<UserModel> getParticipants(int event){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;
        ObservableList<UserModel> Rflist = FXCollections.observableArrayList();

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "select useraccount.* from enrolments join useraccount on user_id = id where event_id = ? and enrolments.type = 1 ";
            stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, event);

            ResultSet rs = stmnt.executeQuery();
            int id;
            String name;

            while(rs.next()){
                id = rs.getInt("id");
                name = rs.getString("name");

                Rflist.add(new UserModel(id, name));
            }


        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return Rflist ;

    }

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
                        LocalDateTime.parse(rs.getString(3), formatter),  LocalDateTime.parse(rs.getString(4), formatter), rs.getInt(8), rs.getString(9)));
            }
            return events;

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return events;
    }

    public static ArrayList<Integer> getFilters(int user){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;
        ArrayList<Integer> filters = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "select filter_id from event_filter join enrolments on enrolments.event_id = event_filter.event_id where user_id = " + user + " group by filter_id";
            stmnt = conn.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                filters.add(rs.getInt(1));
            }
            return filters;

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return filters;
    }

    public static int add_participant(int event, int user){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;
        System.out.println("event "+event);
        System.out.println("user "+user);
        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String queryEvent = "INSERT INTO ENROLMENTS (`EVENT_ID`, `USER_ID`, `TYPE`) VALUES (?, ?, 1)";
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setInt(1, event);
            stmnt.setInt(2, user);

            stmnt.executeUpdate();
            System.out.println("===========================================================");

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return 0;
    }

    public static int add_interest(int event, int user){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String queryEvent = "INSERT INTO ENROLMENTS (`EVENT_ID`, `USER_ID`, `TYPE`) VALUES (?, ?, 2)";
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setInt(1, event);
            stmnt.setInt(2, user);

            stmnt.executeUpdate();

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return 0;
    }

    public static int checkParticipants(int event){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "select count(event_id) from enrolments where event_id = ? and type = 1 group by event_id";
            stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, event);
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

    public static int deleteEnrolment(UserModel model){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String queryEvent = "DELETE FROM enrolments WHERE user_id = ?";
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setInt(1, model.getID());
            stmnt.executeUpdate();

        }catch(SQLException var11){
            var11.printStackTrace();
            return 1;
        }

        return 0;

    }

    public static int checkEnrolment(int event, int user, int type){
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "SELECT * FROM ENROLMENTS WHERE user_id = ? AND event_id = ? AND type = ?";
            stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, user);
            stmnt.setInt(2, event);
            stmnt.setInt(3, type);
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

    public static ObservableList<NotificationModel> refreshNotiflist(int userId, boolean onlyCreator){
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        MysqlDataSource dataSource=SQLOnLaunch();
        Connection conn= null;
        ObservableList<NotificationModel> Rflist = FXCollections.observableArrayList();

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query;
            if(onlyCreator==false){
                query = "SELECT * FROM notifications_join"; //WHERE end_date >= Sysdate() ";
                stmnt = conn.prepareStatement(query);}
            else{
                query = "SELECT * FROM notifications_join WHERE to_notifications=?";
                stmnt = conn.prepareStatement(query);
                stmnt.setInt(1, userId);
            }

            //stmnt.setString(2, pass);
            ResultSet rs = stmnt.executeQuery();
            ResultSet rs2;
            int id, status, eventid;
            int from,to;
            String from_name,to_name;
            String name , description;
            User tempfrom;

            while(rs.next()){

                id = rs.getInt("id_notifications");
                status = rs.getInt("status_notifications");
                name = rs.getString("title_notifications");
                description = rs.getString("description_notifications");
                from=rs.getInt("user_notifications");
                to=rs.getInt("to_notifications");
                from_name=rs.getString("user_notif_name");
                to_name=rs.getString("to_notif_name");
                eventid=rs.getInt("id_event");
                //System.out.println("Event:"+name);
                tempfrom=new User(from,from_name,null,1);
                Rflist.add(new NotificationModel(id,name,description,tempfrom,status,eventid));
            }


        }catch(SQLException var11){
            var11.printStackTrace();
        }
        System.out.println(Rflist.size());
        return Rflist ;

    }


    public static int deleteNotification(NotificationModel model){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String queryEvent = "DELETE FROM notifications WHERE id_notifications=?";
            //System.out.println(event.getEndDatePrivate());
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setInt(1, model.getID());
            //System.out.println(queryEvent);
            stmnt.executeUpdate();

        }catch(SQLException var11){
            var11.printStackTrace();
            return 1;
        }

        return 0;

    }
    public static int acceptNotification(NotificationModel model){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String queryEvent = "INSERT INTO enrolments (user_id,event_id, type) values (?,?, 1)";
            //System.out.println(event.getEndDatePrivate());
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setInt(1, Session.getInstance().getUser().getId());
            stmnt.setInt(2,model.getIdEvent());
            //System.out.println(queryEvent);
            stmnt.executeUpdate();

        }catch(SQLException var11){
            var11.printStackTrace();
            return 1;
        }

        return 0;

    }
    public static int markNotification(NotificationModel model){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String queryEvent;
            if(model.getStatus()==1){
                queryEvent = "UPDATE notifications SET status_notifications=2 WHERE id_notifications=?";
            }
            else queryEvent = "UPDATE notifications SET status_notifications=1 WHERE id_notifications=?";
            //System.out.println(event.getEndDatePrivate());
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setInt(1, model.getIdEvent());
            //System.out.println(queryEvent);
            stmnt.executeUpdate();

        }catch(SQLException var11){
            var11.printStackTrace();
            return 1;
        }

        return 0;

    }

    public static  int updateSetting( User updUser){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String queryEvent = "UPDATE useraccount SET `name`=?, `password`=?, `email`=? WHERE id=?";
            stmnt = conn.prepareStatement(queryEvent);
            stmnt.setString(1,updUser.getUsername());
            stmnt.setString(2,updUser.getPassword());
            stmnt.setString(3, updUser.getEmail());
            stmnt.setInt(4,updUser.getId());
            //System.out.println(queryEvent);
            stmnt.executeUpdate();

        }catch(SQLException var11){
            var11.printStackTrace();
            return 1;
        }

        return 0;

    }


}
