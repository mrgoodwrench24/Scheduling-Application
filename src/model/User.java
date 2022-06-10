package model;

import java.time.LocalDateTime;

public class User {
    private int User_ID;
    private String User_Name;
    private String Password;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;

    public User(int User_ID, String User_Name, String Password, LocalDateTime Create_Date, String Created_By, LocalDateTime Last_Update, String Last_Updated_By){
        this.User_ID = User_ID;
        this.User_Name = User_Name;
        this.Password = Password;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
    }

    /**
     * This method returns the user id of the User object.
     * @return User_ID Returns the id of the object.
     */
    public int getUser_ID() {
        return User_ID;
    }

    /**
     * This method sets the user id of the User object.
     * @param user_ID Sets the ID of the object.
     */
    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    /**
     * This method returns the username of the User object.
     * @return User_Name Returns the username of the object.
     */
    public String getUser_Name() {
        return User_Name;
    }

    /**
     * This method sets the username of the User object.
     * @param user_Name Sets the username of the object.
     */
    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    /**
     * This method returns the password of the User object.
     * @return Password Returns the password of the object.
     */
    public String getPassword() {
        return Password;
    }

    /**
     * This method sets the password of the User object.
     * @param password Sets the password of the object.
     */
    public void setPassword(String password) {
        Password = password;
    }

    /**
     * This method returns the time and date the User object is created.
     * @return Create_Date Returns the LocalDateTime of when the object is created.
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**
     * This method sets the time and date of when the User object is created.
     * @param create_Date Sets the LocalDateTime of when the object is created.
     */
    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    /**
     * This method returns the username of the user who creates the User object.
     * @return Created_By Returns the name of the user who creates the object.
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * This method sets the username of the user who creates the User object.
     * @param created_By Sets the name of the user who creates the object.
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * This method returns the time and date of the last update to the User object.
     * @return Last_Update Returns the LocalDateTime of when the object was last updated.
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    /**
     * This method sets the time and date of the last update to the User object.
     * @param last_Update Sets the LocalDateTime of the object of when the object was last updated.
     */
    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    /**
     * This method returns the username of the user who last updated the User object.
     * @return Last_Updated_By Returns the name of the user who last updated the object.
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * This method sets the username of the user who last updated the User object.
     * @param last_Updated_By Sets the name of the user who last updated the object.
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    public String toString(){
        return (User_Name);
    }
}
