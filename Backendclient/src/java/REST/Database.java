package REST;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

/**
 *
 * @author Casper
 */
public class Database {
    private String hostAddress;
    private String username;
    private String password;
    
    public Database(String hostAddress, String username, String password){
        this.hostAddress = "jdbc:mysql://" + hostAddress;
        this.username = username;
        this.password = password;
    }
    
    public Connection createConnection(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(hostAddress, username, password);
            
        } catch (Exception e) {
            System.out.println("From createConnection: " + e.getMessage());
        }
        
        return conn;
    }
    
    public boolean testConnection(){
        boolean hasConnected = false;
        
        Connection conn = this.createConnection();
        
        try{
            if (conn.isClosed()) {
                hasConnected = false;
            } else{
                hasConnected = true;
            }
            
        } catch (SQLException e) {
            System.out.println("IsClosted exception: " + e.getMessage());
        }
        return hasConnected;
    }
    
    public JsonObject getNrEmployed(){
        String SQL = "SELECT count(id) as 'nrOf' FROM Employes;";
        ResultSet rs;
        try {
            Connection conn = this.createConnection();
            
            PreparedStatement stmt = conn.prepareStatement(SQL);
            rs = stmt.executeQuery();
            rs.next();
            JsonObjectBuilder JOB = Json.createObjectBuilder();
            JOB.add("nrOf", rs.getInt("nrOf"));
            conn.close();
            return JOB.build();
        } catch (Exception e) {
            System.out.println("From getNrEmployed: " + e.getMessage());
            return null;
        }
    }
    
    public JsonArray getStatusInfo(){
        String SQL = "SELECT * FROM status";
        JsonArrayBuilder JAB = Json.createArrayBuilder();
        
        try {
            Connection conn = this.createConnection();
            
            PreparedStatement stmt = conn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                JAB.add(Json.createObjectBuilder()
                .add("id", rs.getInt("id"))
                .add("status", rs.getString("status")));
            }
            return JAB.build();
        } catch (Exception e) {
            System.out.println("From Statusinfo: " + e.getMessage());
            return null;
        }
    }
    
    public JsonObject getUserInfo(int userID){
        String SQL = "SELECT * FROM employes_info WHERE ID = " + userID;
        JsonObjectBuilder JOB = Json.createObjectBuilder();
        try {
            Connection conn = this.createConnection();
            
            PreparedStatement stmt = conn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
           
           
            rs.next();
            
            Date returnDate = rs.getDate("Return_Date");
            
            JOB.add("userID", rs.getInt("ID"));
            JOB.add("full_name", rs.getString("Full_name") );
            JOB.add("role", rs.getString("Role"));
            JOB.add("status", rs.getString("Status"));
            
            //Causes crash if dateobject is null and you do toString.
            if (returnDate == null) {
                JOB.add("returnDate", "-");
            } else {
                JOB.add("returnDate", returnDate.toString());
            }
            
            JOB.add("manager", rs.getString("Manager"));
            JOB.add("office", rs.getString("Office"));
            
            conn.close();
            
            return JOB.build();
            
        } catch (Exception e) {
            System.out.println("From getUserInfo: " + e.getMessage());
            return null;
        }
    }
    
    public JsonArray getAway(){
        String SQL = "SELECT * FROM employes_info WHERE Return_date IS NOT NULL ORDER BY Return_date;";
        JsonArrayBuilder JAB = Json.createArrayBuilder();
        
        try {
            Connection conn = this.createConnection();
            
            PreparedStatement stmt = conn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Date returndate = rs.getDate("Return_Date");

                
                JAB.add(Json.createObjectBuilder()
                .add("userID", rs.getInt("ID"))
                .add("full_name", rs.getString("Full_name") )
                .add("role", rs.getString("Role"))
                .add("status", rs.getString("Status"))
                .add("returnDate", returndate.toString())
                .add("manager", rs.getString("Manager"))
                .add("office", rs.getString("Office"))
                );
                
            }
            
            conn.close();
            
            return JAB.build();
        } catch (Exception e) {
            System.out.println("From getAway: " + e.getMessage());
            return null;
        }
    }
    
    public JsonArray getEmployed(){
        String SQL = "SELECT * FROM employes_info;";
        JsonArrayBuilder JAB = Json.createArrayBuilder();
        Date returnDate;
        
        try {
            Connection conn = this.createConnection();
            
            PreparedStatement stmt = conn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
                        
            while (rs.next()) {
                returnDate = rs.getDate("Return_date");
                if (returnDate == null) {
                    JAB.add(Json.createObjectBuilder()
                    .add("userID", rs.getInt("ID"))
                    .add("full_name", rs.getString("Full_name") )
                    .add("role", rs.getString("Role"))
                    .add("status", rs.getString("Status"))
                    .add("returnDate", "-")
                    .add("manager", rs.getString("Manager"))
                    .add("office", rs.getString("Office"))
                    );
                } else {
                    JAB.add(Json.createObjectBuilder()
                    .add("full_name", rs.getString("Full_name") )
                    .add("role", rs.getString("Role"))
                    .add("status", rs.getString("Status"))
                    .add("returnDate", returnDate.toString())
                    .add("manager", rs.getString("Manager"))
                    .add("office", rs.getString("Office"))
                    );
                }

            }
            
            conn.close();
            
            return JAB.build();
        } catch (Exception e) {
            System.out.println("From getEmployed: " + e.getMessage());
            return null;
        }
    }
    
    public JsonObject getNrAway(){
        String SQL = "SELECT count(id) as 'nrOf' FROM employes_info WHERE Return_date IS NOT NULL;";
        ResultSet rs;
        try {
            Connection conn = this.createConnection();
            
            PreparedStatement stmt = conn.prepareStatement(SQL);
            rs = stmt.executeQuery();
            rs.next();
            JsonObjectBuilder JOB = Json.createObjectBuilder();
            JOB.add("nrOf", rs.getInt("nrOf"));
            
            conn.close();
            
            return JOB.build();
        } catch (Exception e) {
            System.out.println("From getNrAway: " + e.getMessage());
            return null;
        }
    }
    
    public boolean putLeave(String Body){
        JsonReader JR = Json.createReader(new StringReader(Body));
        JsonObject JO = JR.readObject();
        
        String userID = JO.getString("userID");
        String returnDate = JO.getString("returnDate");
        String status = JO.getString("status");
        
        String SQL = "UPDATE employes SET status_id = (SELECT id FROM status WHERE status = ?), returnDate = ? WHERE id = ?;";
        try {
            Connection conn = this.createConnection();
            
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setString(1, status);
            stmt.setInt(2, Integer.parseInt(returnDate));
            stmt.setInt(3, Integer.parseInt(userID)); 
            
            stmt.executeUpdate();
            
            return true;
        } catch (Exception e) {
            System.out.println("From putLeave" + e.getMessage());
            return false;
        }
    }
    /**
     * @return User ID if the auth is OK
     */
    public int authenticate(String body){
        int returnValue = -1;
        if (!"".equals(body)) {
            boolean success = false;
            JsonReader JR = Json.createReader(new StringReader(body));
            JsonObject JO = JR.readObject();

            String username = JO.getString("username");
            String password = JO.getString("password");

            String SQL = "SELECT * FROM person WHERE person.username = ? AND person.password = ?";
            try {
                Connection conn = this.createConnection();

                PreparedStatement stmt = conn.prepareStatement(SQL);
                stmt.setString(1, username);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    returnValue = rs.getInt("id");
                }
                
                return returnValue;

            } catch (SQLException e) {
                System.out.println("From authenticate: " + e.getMessage());
                return returnValue;
            }
        } else {
            return returnValue;
        }
    }
    
}
