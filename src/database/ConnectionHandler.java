package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Luca
 */
public class ConnectionHandler {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306";
    private Connection connection = null;
    private static Statement statement = null;
    
    public ConnectionHandler(){
        try {
            connection=DriverManager.getConnection(DATABASE_URL, "root", "");
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
        try {
            statement=(Statement) connection.createStatement();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }
    
    protected Statement getStatement(){
        return ConnectionHandler.statement;
    }
    
    protected Connection getConnection(){
        return this.connection;
    }
    
}
