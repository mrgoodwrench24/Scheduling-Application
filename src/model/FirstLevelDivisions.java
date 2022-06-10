package model;

import java.time.LocalDateTime;

public class FirstLevelDivisions {

    private int Division_ID;
    private String Division;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;
    private int Country_ID;

    public FirstLevelDivisions(int division_ID, String division, LocalDateTime create_Date, String created_By, LocalDateTime last_Update, String last_Updated_By, int country_ID) {
        this.Division_ID = division_ID;
        this.Division = division;
        this.Create_Date = create_Date;
        this.Created_By = created_By;
        this.Last_Update = last_Update;
        this.Last_Updated_By = last_Updated_By;
        this.Country_ID = country_ID;
    }

    /**
     * This method returns the division id of the object.
     * @return Division_ID Returns the division ID of the object.
     */
    public int getDivision_ID() {
        return Division_ID;
    }

    /**
     * This method sets the division id of the object.
     * @param division_ID Sets the division ID of the object.
     */
    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    /**
     * This method gets the division name.  Name of state or province of the object.
     * @return Division Returns the objects name.
     */
    public String getDivision() {
        return Division;
    }

    /**
     * This method sets the division name.
     * @param division Sets the name of the object.
     */
    public void setDivision(String division) {
        Division = division;
    }

    /**
     * This method returns the LocalDateTime of when the object was created.
     * @return Create_Date Returns the LocalDateTime of when the object was created.
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**
     * This method sets the created date LocalDateTime parameter when the object is created.
     * @param create_Date Sets the LocalDateTime of when the object was created.
     */
    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    /**
     * This method returns the username of the user who created the object.
     * @return Created_By Returns the name of the user who created the object.
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * This method sets the name of the user who creates the object.
     * @param created_By Sets the name of the user who creates the object.
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * This method returns the LocalDateTime of when the object was last updated.
     * @return Last_Update Returns the LocalDateTime of the last update to the object.
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    /**
     * This method sets the created date LocalDateTime parameter when the object is updated.
     * @param last_Update Sets the LocalDateTime of the last update to the object.
     */
    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    /**
     * This method returns the name of the user who last updated the object.
     * @return Last_Updated_By Returns the name of the user who updated the object last.
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * This method records the name of the user who made the last update.
     * @param last_Updated_By Sets the name of the user who made the last update to the object.
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    /**
     * This method returns the country id of the first level division object.
     * @return Country_ID Returns the country id of the object.
     */
    public int getCountry_ID() {
        return Country_ID;
    }

    /**
     * This method sets the country id of the first level division object.
     * @param country_ID Sets the country ID of the object.
     */
    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    /**
     * This method overrides the toString() method to correctly return the objects name in a combobox.
     * @return Division Returns the name of the object rather than the memory address of the object.
     */
    @Override
    public String toString(){
        return (Division);
    }
}
