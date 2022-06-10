package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UserDaoImpl {

    private static User current = null;

    /**
     *  This method reads all users from the database and sets each line as a new user in the allUsers observable list.
     * @return allUsers Returns the allUsers observable list.
     */
    public static ObservableList<User> getAllUsers() throws Exception {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sqlStatement = "select * from users";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int User_ID = result.getInt("User_ID");
            String User_Name = result.getString("User_Name");
            String Password = result.getString("Password");
            LocalDate createDate = result.getDate("Create_Date").toLocalDate();
            LocalTime createTime = result.getTime("Create_Date").toLocalTime();
            LocalDateTime Create_Date = createDate.atTime(createTime);
            String Created_By = result.getString("Created_By");
            LocalDate lastUpdateDate = result.getDate("Last_Update").toLocalDate();
            LocalTime lastUpdateTime = result.getTime("Last_Update").toLocalTime();
            LocalDateTime Last_Update = lastUpdateDate.atTime(lastUpdateTime);
            String Last_Updated_By = result.getString("Last_Updated_By");
            User userResult = new User(User_ID, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By);
            allUsers.add(userResult);

        }
        return allUsers;
    }

    /**
     * This method searches the allUsers list for the username entered in the login screen username box.
     * @param userText The text the user entered into the login screen
     * @return search The user found in the database.  Null if no user is found.
     */
    public static User searchUser(String userText) throws Exception {
        ObservableList<User> allUsersList = getAllUsers();

        for (int i = 0; i < allUsersList.size(); i++) {
            User search = allUsersList.get(i);
            if (search.getUser_Name().contentEquals(userText)) {
                current = search;
                return search;
            }
        }
        return null;
    }

    public static User searchUserID(int userID) throws Exception {
        ObservableList<User> searchUser = getAllUsers();

        for(User search : searchUser){
            if(search.getUser_ID() == userID){
                return search;
            }
        }
        return null;
    }

    /**
     * This method returns the current User.
     * @return current
     */
    public static User getCurrent() {
        return current;
    }

    /**
     * This method sets the current user.
     * @param current The object of the current user.
     */
    public void setCurrent(User current) {
        UserDaoImpl.current = current;
    }
}
