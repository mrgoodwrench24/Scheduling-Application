package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivisions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DivisionsDAOImpl {

    private static final ObservableList<FirstLevelDivisions> allDivisions = FXCollections.observableArrayList();
    private static final ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static final ObservableList<FirstLevelDivisions> usStates = FXCollections.observableArrayList();
    private static final ObservableList<FirstLevelDivisions> cnStates = FXCollections.observableArrayList();
    private static final ObservableList<FirstLevelDivisions> ukStates = FXCollections.observableArrayList();

    /**
     * This method reads first level divisions from the database and sets each line from that table as a new FirstLevelDivision object and adds the object to the allDivisions observable list.
     */
    public static void setDivisions() throws SQLException {
        String sqlStatement = "Select * from first_level_divisions";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int Division_ID = result.getInt("Division_ID");
            String Division = result.getString("Division");
            LocalDate createDate = result.getDate("Create_Date").toLocalDate();
            LocalTime createTime = result.getTime("Create_Date").toLocalTime();
            LocalDateTime Create_Date = createDate.atTime(createTime);
            String Created_By = result.getString("Created_By");
            LocalDate updateDate = result.getDate("Last_Update").toLocalDate();
            LocalTime updateTime = result.getTime("Last_Update").toLocalTime();
            LocalDateTime Last_Update = updateDate.atTime(updateTime);
            String Last_Updated_By = result.getString("Last_Updated_By");
            int Country_ID = result.getInt("Country_ID");
            FirstLevelDivisions division = new FirstLevelDivisions(Division_ID,Division,Create_Date,Created_By,Last_Update,Last_Updated_By,Country_ID);
            allDivisions.add(division);
        }
    }

    /**
     * This method reads each line of the countries table of the database and sets each line as a new Country object and adds that object to the allCountries observable list.
     */
    public static void setCountries() throws SQLException {
        String sqlStatement = "select * from countries";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int Country_ID = result.getInt("Country_ID");
            String Country = result.getString("Country");
            LocalDate createDate = result.getDate("Create_Date").toLocalDate();
            LocalTime createTime = result.getTime("Create_Date").toLocalTime();
            LocalDateTime Create_Date = createDate.atTime(createTime);
            String Created_By = result.getString("Created_By");
            LocalDate updateDate = result.getDate("Last_Update").toLocalDate();
            LocalTime updateTime = result.getTime("Last_Update").toLocalTime();
            LocalDateTime Last_Update = updateDate.atTime(updateTime);
            String Last_Updated_By = result.getString("Last_Updated_By");
            model.Country country = new Country(Country_ID,Country,Create_Date,Created_By,Last_Update,Last_Updated_By);
            allCountries.add(country);

        }

    }

    /**
     * This method takes an int Division_ID as a parameter and searches the allDivisions list for a matching division id.  It then returns the FirstLevelDivision object.
     * @param Division_ID The ID of the division to search for.
     * @return state Returns the name of the state found.
     */
    public static String findState(int Division_ID){
        ObservableList<FirstLevelDivisions> searchList = allDivisions;

        for(FirstLevelDivisions search : searchList){
            if(search.getDivision_ID() == Division_ID){
                String state = search.getDivision();
                return state;
            }
        }
        return null;
    }

    /**
     * This method takes an int Division_ID as a parameter and searches the allDivisions list for the Division_ID.  When it finds a match
     * it sets an int Country_ID as the Country_ID of the FirstLevelDivision found.  It then searches the allCountries list for the matching
     * Country_ID.  It then returns the Country object.
     * @param Division_ID The ID FirstLevelDivision to search for to find the Country_ID of that FirstLevelDivision object.
     * @return country Returns the Country object found.
     */
    public static Country setCountry (int Division_ID){
        ObservableList<FirstLevelDivisions> searchList = allDivisions;
        ObservableList<Country> searchCountry = allCountries;
        int Country_ID = 0;

        for(FirstLevelDivisions search : searchList) {
            if (search.getDivision_ID() == Division_ID) {
                Country_ID = search.getCountry_ID();
                break;
            }

        }

        for(int i = 0; i < searchCountry.size(); i++){
            Country country = searchCountry.get(i);
            if(country.getCountry_ID() == Country_ID){
                return country;
            }
        }
        return null;

    }

    /**
     * This method populates the usStates, ukStates, and cnStates based on the allDivisions list.  The objects are added to
     * the correct list based on the Country_ID.
     */
    public static void setStates (){
        for (FirstLevelDivisions state : allDivisions){

                if (state.getCountry_ID() == 1){
                    usStates.add(state);
                }
                else if (state.getCountry_ID() == 2) {
                    ukStates.add(state);
                }
                else if (state.getCountry_ID() == 3){
                    cnStates.add(state);
                }
                else{
                    return;
                }
        }
    }

    /**
     * This method returns the allDivisions observable list.
     * @return allDivisions Returns the allDivisions observable list.
     */
    public static ObservableList<FirstLevelDivisions> getAllDivisions() {
        return allDivisions;
    }

    /**
     * This method returns the allCountries observable list.
     * @return allCountries Returns the allCountries observable list.
     */
    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    /**
     * This method returns the usStates observable list.
     * @return usStates Returns the usStates observable list.
     */
    public static ObservableList<FirstLevelDivisions> getUsStates() {
        return usStates;
    }

    /**
     * This method returns the cnStates observable list.
     * @return cnStates Returns the cnStates observable list.
     */
    public static ObservableList<FirstLevelDivisions> getCnStates() {
        return cnStates;
    }

    /**
     * This method returns the ukStates observable list.
     * @return ukStates Returns the ukStates observable list.
     */
    public static ObservableList<FirstLevelDivisions> getUkStates() {
        return ukStates;
    }
}
