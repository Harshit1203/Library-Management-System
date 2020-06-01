
package library.assistance.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Databasehandler {
    private static Databasehandler handler;
    
    private static final String DB_URL= "jdbc:mysql://localhost:3306/lds";
    private static final String user = "root";
    private static final String pass = "root";
    private static Connection con = null;
    private static Statement st = null;
    
    private  Databasehandler()
    {
        createConnection();
    }
    
    public static Databasehandler getInstance(){
            
        if(handler==null)
            handler=new Databasehandler();
        return handler;
    }
    
     void createConnection()
    {
        try
        {
         Class.forName("com.mysql.cj.jdbc.Driver");
         con = DriverManager.getConnection(DB_URL,user,pass);   
        }
         catch(Exception e){
            System.out.println(e);
         }
    }
     
    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            st = con.createStatement();
            result = st.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            st = con.createStatement();
            st.execute(qu);
            return true;
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
        finally {
        }
    }
}
