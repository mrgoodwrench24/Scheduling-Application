package DAO;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    public static Connection conn;  // Connection Interface
    private static Statement statement;

    /**
     * This method opens a connection to the database.  This method also includes a lambda
     * expression to notify the developer the connection to the database was opened when logging
     * into the application.  The lambda expression used here helps to streamline the coding needed
     * to display to the developer confirmation message.
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            conn = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            conStatus status = () -> System.out.println("Connection successful!");
            status.status();

        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * This method closes the connection to the database.  This method also includes a lambda
     * expression to notify the developer the connection to the database was closed upon exit
     * of the application.  The lambda expression used here helps to streamline the coding needed
     * to display to the developer confirmation message.
     */
    public static void closeConnection() {
        try {
            conn.close();
            conStatus status = () -> System.out.println("Connection closed!");
            status.status();
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * This method sets a prepared statement.
     * @param conn This is the connection to the database.
     * @param sqlStatement This is the statement to use.
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }

    /**
     * This method returns the Statement.
     * @return statement Returns the prepared statement.
     */
    public static Statement getPreparedStatement(){
        return statement;
    }
}
