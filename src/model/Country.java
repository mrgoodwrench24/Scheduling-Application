package model;

import java.time.LocalDateTime;

public class Country {

    private int Country_ID;
    private String Country;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;

    public Country(int country_ID, String country, LocalDateTime create_Date, String created_By, LocalDateTime last_Update, String last_Updated_By) {
        this.Country_ID = country_ID;
        this.Country = country;
        this.Create_Date = create_Date;
        this.Created_By = created_By;
        this.Last_Update = last_Update;
        this.Last_Updated_By = last_Updated_By;
    }

    /**
     * This method returns the id of the country.
     * @return Country_ID Returns the countries ID.
     */
    public int getCountry_ID() {
        return Country_ID;
    }

    /**
     * This method sets the country ID.
     * @param country_ID Sets the countries ID.
     */
    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    /**
     * This method returns the country name.
     * @return Country Returns the countries name.
     */
    public String getCountry() {
        return Country;
    }

    /**
     * This method sets the country name.
     * @param country Sets the name of the country.
     */
    public void setCountry(String country) {
        Country = country;
    }

    /**
     * This method returns the LocalDateTime the country object was created.
     * @return Create_Date Returns the LocalDateTime of when the object was created.
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**
     * This method sets the LocalDateTime the country object was created.
     * @param create_Date Sets the LocalDateTime of when the object is created.
     */
    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    /**
     * This method returns the name of the user who created the country object.
     * @return Created_By Returns the name of the user who created the object.
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * This method sets the name of the user who created the country object.
     * @param created_By Sets the name of the user who creates the object.
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * This method returns the LocalDateTime of the last time the country object was updated.
     * @return Last_Update Returns the LocalDateTime of when the object was last updated.
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    /**
     * This method sets the LocalDateTime of the last update of the country object.
     * @param last_Update Sets the LocalDateTime of when the object was last updated.
     */
    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    /**
     * This method returns the name of the user who last updated the country object.
     * @return Last_Updated_By Returns the name of the user who last updated the object.
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * This method sets the name of the user who last updated the country object.
     * @param last_Updated_By Sets the name of the user who last updated the object.
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    /**
     *This method overrides the toString() method to allow a combo box to return the correct information.
     * @return Country Returns the name of the country rather than the objects memory address.
     */
    @Override
    public String toString(){
        return (Country);
    }


}
