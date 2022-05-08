package genericclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.eventplanner.EventModel;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseComm {
    private static MysqlDataSource SQLOnLaunch() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root"); // user
        dataSource.setPassword("1234"); //pass
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
            String query = "SELECT * FROM login WHERE email=?";
            stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                return 3;
            }
            String query2 = "INSERT INTO login (`NAME`,`PASSWORD`,`EMAIL`) VALUES (?,?,?)";
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
            String query = "SELECT * FROM login WHERE username=? AND password=?";
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
    public static ObservableList<EventModel> refreshlist(){
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
            String query = "SELECT * FROM events WHERE end_date > Sysdate() ";
            stmnt = conn.prepareStatement(query);
            //stmnt.setString(1, name);
            //stmnt.setString(2, pass);
            ResultSet rs = stmnt.executeQuery();
            ResultSet rs2;
            int id ;
            int creator, status;
            String name , description;
            LocalDateTime sdate , edate ;
            ArrayList<User> Luser = new ArrayList<>();




            while(rs.next()){
                id = rs.getInt("event_id");
                creator = rs.getInt("creator");
                name = rs.getString("name");
                description = rs.getString("description");
                sdate =  LocalDateTime.parse(rs.getString("sdate"));
                edate = LocalDateTime.parse(rs.getString("edate"));

                 query = "SELECT user_id FROM enrolments WHERE event_id =?";

                 stmnt = conn.prepareStatement(query);
                 stmnt.setInt(1,id);
                 rs2 = stmnt.executeQuery();
                 while(rs2.next()){
                     //Aici se aduga idurile;


                 };

                 Rflist.add(new EventModel(id,name ,description,sdate ,edate, Luser, Luser.size()));
            }


        }catch(SQLException var11){
            var11.printStackTrace();
        }
      return Rflist ;

    }
}
