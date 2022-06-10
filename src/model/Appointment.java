package model;

import java.time.LocalDateTime;

public class Appointment {

    private int Appointment_ID;
    private int Contact_ID;
    private LocalDateTime Create_Date;
    private String Created_By;
    private int Customer_ID;
    private String Description;
    private LocalDateTime End;
    private LocalDateTime Last_Update;
    private String Last_Update_By;
    private String Location;
    private LocalDateTime Start;
    private String Title;
    private String Type;
    private int User_ID;

    public Appointment(int appointment_ID, int contact_ID, LocalDateTime create_Date, String created_By, int customer_ID, String description, LocalDateTime end, LocalDateTime last_Update, String last_Update_By, String location, LocalDateTime start, String title, String type, int user_ID) {
        this.Appointment_ID = appointment_ID;
        this.Contact_ID = contact_ID;
        this.Create_Date = create_Date;
        this.Created_By = created_By;
        this.Customer_ID = customer_ID;
        this.Description = description;
        this.End = end;
        this.Last_Update = last_Update;
        this.Last_Update_By = last_Update_By;
        this.Location = location;
        this.Start = start;
        this.Title = title;
        this.Type = type;
        this.User_ID = user_ID;
    }

    public Appointment(int contact_ID, LocalDateTime create_Date, String created_By, int customer_ID, String description, LocalDateTime end, LocalDateTime last_Update, String last_Update_By, String location, LocalDateTime start, String title, String type, int user_ID) {
        Contact_ID = contact_ID;
        Create_Date = create_Date;
        Created_By = created_By;
        Customer_ID = customer_ID;
        Description = description;
        End = end;
        Last_Update = last_Update;
        Last_Update_By = last_Update_By;
        Location = location;
        Start = start;
        Title = title;
        Type = type;
        User_ID = user_ID;
    }




    /**
     * This method returns the id of the appointment.
     * @return appointment_ID The appointment ID to return.
     */
    public int getAppointment_ID() {
        return Appointment_ID;
    }

    /**
     * This method sets the id of the appointment.
     * @param appointment_ID The appointment ID to set.
     */
    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    /**
     * This method returns the contact id.
     * @return Contact_ID The contact ID to return.
     */
    public int getContact_ID() {
        return Contact_ID;
    }

    /**
     * This method sets the contact id.
     * @param contact_ID The contact ID to set.
     */
    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    /**
     * This method gets the LocalDateTime the appointment was created.
     * @return Create_Date The creation LocalDateTime to return.
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**
     * This method sets the LocalDateTime the appointment was created.
     * @param create_Date The creation LocalDateTime to set.
     */
    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    /**
     * This method returns the name of the user who created the appointment.
     * @return Created_By The name of the user who created the object to return.
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * This method sets the user who creates the appointment.
     * @param created_By The name of the user who is creating the object to set.
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * This method returns the customer id for the customer attached to the appointment.
     * @return Customer_ID The customer ID to return.
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }

    /**
     * This method sets the customer ID for the appointment
     * @param customer_ID The customer ID to set.
     */
    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    /**
     * This method returns the description of the appointment.
     * @return Description The description of the appointment to return.
     */
    public String getDescription() {
        return Description;
    }

    /**
     * This method sets the description of the appointment.
     * @param description The description of the appointment to set.
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * This method returns the appointments end time.
     * @return End The LocalDateTime of the appointments end time to return.
     */
    public LocalDateTime getEnd() {
        return End;
    }

    /**
     * This method sets the end time of the appointment.
     * @param end The LocalDateTime of the appointments end time to set.
     */
    public void setEnd(LocalDateTime end) {
        End = end;
    }

    /**
     * This method returns the LocalDateTime of the last update.
     * @return Last_Update Returns the LocalDateTime of the last update.
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }


    /**
     * This method sets the LocalDateTime of the last time the appointment was updated.
     * @param last_Update Sets the LocalDateTime of the last update.
     */
    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    /**
     * This method returns the name of the user who made the last update to the appointment.
     * @return Last_Update_By Returns the name of the user who made the last update.
     */
    public String getLast_Update_By() {
        return Last_Update_By;
    }

    /**
     * This method sets the name of the user who last updated the appointment.
     * @param last_Update_By Sets the name of the user who made the last update.
     */
    public void setLast_Update_By(String last_Update_By) {
        Last_Update_By = last_Update_By;
    }

    /**
     * This method returns the location of the appointment.
     * @return Location Returns the location of the appointment.
     */
    public String getLocation() {
        return Location;
    }

    /**
     * This method sets the location of the appointment.
     * @param location Sets the location of the appointment.
     */
    public void setLocation(String location) {
        Location = location;
    }

    /**
     * This method returns the start time of the appointment in LocalDateTime.
     * @return Start Returns the LocalDateTime of the start of the appointment.
     */
    public LocalDateTime getStart() {
        return Start;
    }

    /**
     * This method sets the start time of the appointment as a LocalDateTime variable.
     * @param start Sets the LocalDateTime of the start of the appointment.
     */
    public void setStart(LocalDateTime start) {
        Start = start;
    }

    /**
     * This method returns the title of the appointment.
     * @return Title Returns the title of the appointment.
     */
    public String getTitle() {
        return Title;
    }

    /**
     * This method sets the title of the appointment.
     * @param title Sets the title of the appointment.
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * This method returns the type of the appointment.
     * @return Type Returns the type of the appointment.
     */
    public String getType() {
        return Type;
    }

    /**
     * This method sets the type of the appointment.
     * @param type Sets the type of the appointment.
     */
    public void setType(String type) {
        Type = type;
    }

    /**
     * This method returns the id of the current user.
     * @return User_ID Returns the user ID of the current user.
     */
    public int getUser_ID() {
        return User_ID;
    }

    /**
     * This method sets the id of the current user.
     * @param user_ID sets the user ID of the current user.
     */
    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }
}
