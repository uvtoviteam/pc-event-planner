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
    public static int add_event(Event event){
        MysqlDataSource dataSource = SQLOnLaunch();
        Connection conn= null;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        PreparedStatement stmnt = null;

        try {
            String query = "INSERT INTO EVENT (`NAME`, `DESCRIPTION`, `STARTDATE`, `ENDDATE`, `LIMIT`) VALUES (?, ?, ?, ?, ?)";
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, event.getNume());
            stmnt.setString(2, event.getDescription());
            stmnt.setDate(3, event.getStartdate());
            stmnt.setDate(4, event.getEnddate());
            stmnt.setInt(5, event.getLimit());
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
            String query = "SELECT * FROM EVENT WHERE name = ?";
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, name);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                return new Event(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4).toLocalDate().atStartOfDay(), rs.getDate(5).toLocalDate().atStartOfDay(), rs.getInt(6));
            }
            return new Event();

        }catch(SQLException var11){
            var11.printStackTrace();
        }

        return new Event();
    }
}
