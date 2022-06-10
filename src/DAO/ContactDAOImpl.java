package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAOImpl {

    private static final ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * This method reads all contacts from the database and saves each contact as a Contact object in the allContacts observable list.
     */
    public static void setAllContacts() throws SQLException {
        String sqlStatement = "select * from contacts";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int Contact_ID = result.getInt("Contact_ID");
            String Contact_Name = result.getString("Contact_Name");
            String Email = result.getString("Email");
            Contact newContact = new Contact(Contact_ID,Contact_Name,Email);
            allContacts.add(newContact);
        }
    }

    /**
     * This method returns the allContacts observable list.
     * @return allContacts Returns the allContacts observable list.
     */
    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }

    /**
     * This method searches the allContacts observable list for the parameter Contact_ID that is passed in and returns the corresponding contact with the same Contact_ID.
     * @param Contact_ID this is the ID of the contact to search for in the allContacts list
     * @return search Returns the found Contact object.
     */
    public static Contact searchContact(int Contact_ID){
        ObservableList<Contact> searchList = getAllContacts();

        for (Contact search : searchList){
            if(search.getContact_ID() == Contact_ID){
                return search;
            }
        }
        return null;
    }
}
