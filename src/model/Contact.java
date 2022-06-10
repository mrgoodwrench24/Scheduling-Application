package model;

public class Contact {

    private int Contact_ID;
    private String Contact_Name;
    private String Email;

    public Contact(int contact_ID, String contact_Name, String email) {
        Contact_ID = contact_ID;
        Contact_Name = contact_Name;
        Email = email;
    }

    /**
     * This method returns the id of the contact.
     * @return Contact_ID Returns the contacts id.
     * */
    public int getContact_ID() {
        return Contact_ID;
    }

    /**
     * This method sets the id of the contact.
     * @param contact_ID Sets the contacts ID.
     * */
    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    /**
     * This method returns the contact name.
     * @return Contact_Name Returns the contacts name.
     * */
    public String getContact_Name() {
        return Contact_Name;
    }

    /**
     * This method sets the contact name.
     * @param contact_Name Sets the name of the contact.
     * */
    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }

    /**
     * This method returns the email string of the contact.
     * @return Email Returns the contacts email.
     * */
    public String getEmail() {
        return Email;
    }

    /**
     * This method sets the email string of the contact.
     * @param email Sets the contacts email address.
     * */
    public void setEmail(String email) {
        Email = email;
    }

    /**
     * This method overrides the toString() method to allow the combo box on certain pages to populate correctly.
     * @return Contact_Name Returns the contact name instead of the memory address.
     * */
    @Override
    public String toString(){
        return (Contact_Name);
    }
}
