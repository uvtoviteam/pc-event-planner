package genericclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

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
            stmnt.setDate(3, event.getStartdate());
            stmnt.setDate(4, event.getEnddate());
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
}
