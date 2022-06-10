package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

public class CustomersDaoImpl {

    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * This method reads each customer in from the database as a Customer object and adds it to the observable list allCustomers.
     */
    public static void setAllCustomers() throws SQLException {
        String sqlStatement = "select * from customers";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int Customer_ID = result.getInt("Customer_ID");
            String Customer_Name = result.getString("Customer_Name");
            String Address = result.getString("Address");
            String Postal_Code = result.getString("Postal_Code");
            String Phone = result.getString("Phone");
            LocalDate createDate = result.getDate("Create_Date").toLocalDate();
            LocalTime createTime = result.getTime("Create_Date").toLocalTime();
            LocalDateTime Create_Date = createDate.atTime(createTime);
            String Created_By = result.getString("Created_By");
            LocalDate Date = result.getDate("Last_Update").toLocalDate();
            LocalTime Time = result.getTime("Last_Update").toLocalTime();
            LocalDateTime Last_Update = Date.atTime(Time);
            String Last_Updated_By = result.getString("Last_Updated_By");
            int Division_ID = result.getInt("Division_ID");
            String State = DivisionsDAOImpl.findState(Division_ID);
            Customer customerResult = new Customer(Customer_ID,Customer_Name,Address, Postal_Code,Phone,Create_Date,Created_By,Last_Update,Last_Updated_By,Division_ID,State);
            allCustomers.add(customerResult);

        }
    }

    /**
     * This method returns the observable list allCustomers.
     * @return allCustomers Returns the allCustomers observable list
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /**
     * This method clears the allCustomers observable list.
     */
    public static void clearCustomers(){
        allCustomers.clear();
    }

    /**
     * This method takes a customer id as a parameter and deletes the row in the database with the associated customer id. 
     * @param Customer_ID This is the ID of the customer to delete.
     */
    public static void deleteCustomer(int Customer_ID) throws SQLException {
        String sqlStatement = "delete from customers where Customer_ID = ?";
        DBConnection.setPreparedStatement(DBConnection.conn,sqlStatement);
        PreparedStatement delCusStatement = (PreparedStatement) DBConnection.getPreparedStatement();
        delCusStatement.setString(1, String.valueOf(Customer_ID));
        delCusStatement.execute();
    }

    /**
     * This method takes a customer object as a parameter and adds it into the database as a new customer.
     * @param newCustomer This is the customer object to add to the database.
     */
    public static void addCustomer (Customer newCustomer) throws SQLException {
        String sqlStatement = "INSERT INTO customers(Customer_Name,Address,Postal_Code,Phone,Create_Date,Created_By,Last_Update,Last_Updated_By,Division_ID) VALUES(?,?,?,?,?,?,?,?,?);";
        DBConnection.setPreparedStatement(DBConnection.conn,sqlStatement);
        PreparedStatement psac = (PreparedStatement) DBConnection.getPreparedStatement();
        String Customer_Name = newCustomer.getCustomer_Name();
        String Address = newCustomer.getAddress();
        String Postal_Code = newCustomer.getPostal_Code();
        String Phone = newCustomer.getPhone();
        LocalDateTime createTimeLocal = LocalDateTime.now();
        ZonedDateTime cl = ZonedDateTime.of(createTimeLocal, ZoneId.systemDefault());
        ZonedDateTime clUTC = cl.withZoneSameInstant(ZoneOffset.UTC);
        Timestamp Create_Date = Timestamp.valueOf(clUTC.toLocalDateTime());
        String Created_By = newCustomer.getCreated_By();
        LocalDateTime updateLocal = LocalDateTime.now();
        ZonedDateTime ld = ZonedDateTime.of(updateLocal, ZoneId.systemDefault());
        ZonedDateTime ldUTC = ld.withZoneSameInstant(ZoneOffset.UTC);
        Timestamp Last_Update = Timestamp.valueOf(ldUTC.toLocalDateTime());
        String Last_Updated_By = newCustomer.getLast_Updated_By();
        int Division_ID = newCustomer.getDivision_ID();

        psac.setString(1,Customer_Name);
        psac.setString(2,Address);
        psac.setString(3,Postal_Code);
        psac.setString(4,Phone);
        psac.setTimestamp(5, Create_Date);
        psac.setString(6,Created_By);
        psac.setTimestamp(7, Last_Update);
        psac.setString(8,Last_Updated_By);
        psac.setString(9, String.valueOf(Division_ID));

        psac.execute();

    }

    /**
     * This method takes a customer object as a parameter and updates the customer in the database with the matching Customer_ID.
     * @param newCustomer This is the customer object to have the database update.
     */
    public static void updateCustomer(Customer newCustomer) throws SQLException {
        String sqlStatement = "UPDATE customers SET Customer_Name = ?,Address = ?,Postal_Code = ?,Phone = ?,Last_Update = ?,Last_Updated_By = ?,Division_ID = ? WHERE Customer_ID = ?";
        DBConnection.setPreparedStatement(DBConnection.conn,sqlStatement);
        PreparedStatement psuc = (PreparedStatement) DBConnection.getPreparedStatement();

        int Customer_ID = newCustomer.getCustomer_ID();
        String Customer_Name = newCustomer.getCustomer_Name();
        String Address = newCustomer.getAddress();
        String Postal_Code = newCustomer.getPostal_Code();
        String Phone = newCustomer.getPhone();
        LocalDateTime updateLocal = LocalDateTime.now();
        ZonedDateTime ld = ZonedDateTime.of(updateLocal, ZoneId.systemDefault());
        ZonedDateTime ldUTC = ld.withZoneSameInstant(ZoneOffset.UTC);
        Timestamp Last_Update = Timestamp.valueOf(ldUTC.toLocalDateTime());
        String Last_Updated_By = newCustomer.getLast_Updated_By();
        int Division_ID = newCustomer.getDivision_ID();

        psuc.setString(1,Customer_Name);
        psuc.setString(2,Address);
        psuc.setString(3,Postal_Code);
        psuc.setString(4,Phone);
        psuc.setString(5, String.valueOf(Last_Update));
        psuc.setString(6,Last_Updated_By);
        psuc.setString(7, String.valueOf(Division_ID));
        psuc.setString(8,String.valueOf(Customer_ID));

        psuc.execute();
    }

    /**
     * This method takes the customer name as a parameter and searches the all customers list and returns the customer object with a matching customer name.
     * @param customerName This is the name of the customer to search for.
     * @return search Returns the Customer object found.
     */
    public static Customer searchCustomer (String customerName){
        ObservableList<Customer> searchList = getAllCustomers();

        for(Customer search : searchList){
            if(search.getCustomer_Name().contentEquals(customerName)){
                return search;
            }
        }
        return null;
    }

    /**
     * This method takes a customer id as a parameter and searches the allCustomers list for a customer with a matching customer id.  It then returns that customer from the allCustomers list.
     * @param Customer_ID This is the ID of the customer to search for.
     * @return search Returns the Customer object found.
     */
    public static Customer searchCustomerID (int Customer_ID){
        ObservableList<Customer> searchList = getAllCustomers();

        for(Customer search : searchList){
            if(search.getCustomer_ID() == Customer_ID){
                return search;
            }
        }
        return null;
    }


}
