package DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import static DAO.DBConnection.conn;

public class Query {

    private static String query;
    private static Statement stmt;
    private static ResultSet result;

    /**
     * This method makes a query to the database.
     * @param q This is SQL statement to be executed in the database as a String.
     */
    public static void makeQuery(String q){
        query =q;
        try{
            stmt=conn.createStatement();
            if(query.toLowerCase().startsWith("select"))
                result=stmt.executeQuery(q);
            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);

        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /**
     * This method returns the results of the query.
     * @return result Returns the results of the query.
     */
    public static ResultSet getResult(){
        return result;
    }

}
