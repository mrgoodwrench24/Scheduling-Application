package model;

import java.time.LocalDateTime;

public class Customer {

    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;
    private int Division_ID;
    private String State;



    public Customer(int customer_ID, String customer_Name, String address, String postal_Code, String phone, LocalDateTime create_Date, String created_By, LocalDateTime last_Update, String last_Updated_By, int division_ID, String State) {
        this.Customer_ID = customer_ID;
        this.Customer_Name = customer_Name;
        this.Address = address;
        this.Postal_Code = postal_Code;
        this.Phone = phone;
        this.Create_Date = create_Date;
        this.Created_By = created_By;
        this.Last_Update = last_Update;
        this.Last_Updated_By = last_Updated_By;
        this.Division_ID = division_ID;
        this.State = State;
    }

    public Customer(String customer_Name, String address, String postal_Code, String phone, LocalDateTime create_Date, String created_By, LocalDateTime last_Update, String last_Updated_By, int division_ID, String state) {
        this.Customer_Name = customer_Name;
        this.Address = address;
        this.Postal_Code = postal_Code;
        this.Phone = phone;
        this.Create_Date = create_Date;
        this.Created_By = created_By;
        this.Last_Update = last_Update;
        this.Last_Updated_By = last_Updated_By;
        this.Division_ID = division_ID;
        this.State = state;
    }

    public Customer(String customer_Name, String address, String postal_Code, String phone, String created_By, String last_Updated_By, int division_ID, String state) {
        Customer_Name = customer_Name;
        Address = address;
        Postal_Code = postal_Code;
        Phone = phone;
        Created_By = created_By;
        Last_Updated_By = last_Updated_By;
        Division_ID = division_ID;
        State = state;
    }

    /**
     * This method returns the customer id.
     * @return Customer_ID Returns the ID of the customer object.
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }

    /**
     * This method sets the customer id.
     * @param customer_ID Sets the id of the customer object.
     */
    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    /**
     * This method returns the customers name
     * @return Customer_Name Returns the name of the customer object.
     */
    public String getCustomer_Name() {
        return Customer_Name;
    }

    /**
     * This method sets the customer name.
     * @param customer_Name Sets the name of the customer object.
     */
    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    /**
     * This method returns the address of a customer object.
     * @return Address Returns the address of the customer object.
     */
    public String getAddress() {
        return Address;
    }

    /**
     * This method sets the address of a customer object.
     * @param address Sets the address of the customer object.
     */
    public void setAddress(String address) {
        Address = address;
    }

    /**
     * This method returns the postal code of the customer object.
     * @return Postal_Code Returns the customer objects postal code.
     */
    public String getPostal_Code() {
        return Postal_Code;
    }

    /**
     * The method sets the postal code of the customer object.
     * @param postal_Code Sets the customer objects postal code.
     */
    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }

    /**
     * This method returns the phone number of the customer object.
     * @return Phone Returns the customer objects phone number.
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * This method sets the phone number string of the customer object.
     * @param phone Sets the customer objects phone number.
     */
    public void setPhone(String phone) {
        Phone = phone;
    }

    /**
     * This method returns the LocalDateTime of when the customer object was created.
     * @return Create_Date Returns the LocalDateTime of when the object was created.
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**
     * This method sets the LocalDateTime of the customer object as it is created.
     * @param create_Date Sets the LocalDateTime of the object when it is created.
     */
    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    /**
     * This method returns the name of the user who created the customer object.
     * @return Created_By Returns the name of the user who creates the object.
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * This method sets the name of the user when the customer object is created.
     * @param created_By Sets the name of the user who creates the object.
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * This method returns the LocalDateTime of when the customer object was last updated.
     * @return Last_Update Returns the LocalDateTime of the last update to the object.
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    /**
     * This method sets the name of the user when an update is being made to the customer object.
     * @param last_Update Sets the LocalDateTime when the object is updated.
     */
    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    /**
     * This method returns the name of the user who last updated the customer object.
     * @return Last_Update_By Returns the name of the user who last updated the object.
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * This method sets the name of the user who last updated the customer object.
     * @param last_Update_By Sets the name of the user when the object is updated.
     */
    public void setLast_Updated_By(String last_Update_By) {
        Last_Updated_By = last_Update_By;
    }

    /**
     * This method returns the division id of the customer object.
     * @return Division_ID Returns the division ID of the object.
     */
    public int getDivision_ID() {
        return Division_ID;
    }

    /**
     * This method sets the division id of the customer object.
     * @param division_ID Sets the division id of the object.
     */
    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    /**
     * This method sets the state of the customer object.
     * @return State Returns the customer objects state of residence.
     */
    public String getState() {
        return State;
    }

    /**
     * This method sets the state of the customer object.
     * @param state Sets the customer objects state of residence.
     */
    public void setState(String state) {
        State = state;
    }

    @Override
    public String toString() {
        return (Customer_Name);
    }
}
