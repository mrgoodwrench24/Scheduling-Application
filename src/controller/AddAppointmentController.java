package controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDAOImpl;
import DAO.CustomersDaoImpl;
import DAO.UserDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField titleTxt;

    @FXML
    private ComboBox<Contact> comboContact;

    @FXML
    private ComboBox<Customer> comboCustomer;

    @FXML
    private ComboBox<User> comboUser;

    @FXML
    private TextField locationTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private TextArea descriptionTxt;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<LocalTime> comboEndTime;

    @FXML
    private ComboBox<LocalTime> comboStartTime;

    private LocalDate selectedDate;

    private final User currentUser = UserDaoImpl.getCurrent();



    /**
     * This method sets the date from the DatePicker fxml object and set it as the selectedDate object.
     * @param event The event of when the datepicker is selected.
     */
    @FXML
    private void handleDatePicker(ActionEvent event) {
        selectedDate = datePicker.getValue();

    }


    /**
     * This method sets the appointment end time combo box choices.  It is based on the start time
     * combo box.  The first time is 15 minutes after the start time and then generates the choices
     * in the combo box at 15 minute intervals.  If the start time has not been selected the end
     * time combo box is empty.
     * @param event When the comboStartTime box is activated.
     */
    @FXML
    private void handleStartTime(ActionEvent event) {
        Boolean isNull = true;

        LocalTime selectedStart = comboStartTime.getSelectionModel().getSelectedItem();
        if(selectedStart != null){
            LocalTime end = LocalTime.of(23,0);

            while(selectedStart.isBefore(end.plusSeconds(1))){
                comboEndTime.getItems().add(selectedStart);
                selectedStart = selectedStart.plusMinutes(15);
            }
            for(int i = 0; i < 3; i++){
                comboEndTime.getItems().add(selectedStart);
                selectedStart = selectedStart.plusMinutes(15);
            }
        }

    }


    /**
     * This method sends the user back to the Appointment page.
     * @param event When the back button is clicked on.
     */
    @FXML
    private void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method takes all the fields on the page and saves each field as a variable.  Then
     * a new Appointment object is saved with the saved variables.  Then the new appointment
     * is entered into the database.  It then calls the refreshTableView() method which
     * then resets the tableview on the Appointments form.  Finally it takes the user back to
     * the Appointments Form.
     * @param event When the save button is clicked on.
     */
    @FXML
    private void onActionSave(ActionEvent event) throws Exception {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        allAppointments = AppointmentDaoImpl.getAllAppointments();
        Boolean complete = false;
        Boolean overlapping = true;
        String title = null;
        String description = null;
        String location = null;
        Contact selectedContact = null;
        int contactID = -1;
        String type = null;
        LocalTime open = LocalTime.of(8, 0);
        LocalTime close = LocalTime.of(22, 0);
        LocalTime selectedStartTime;
        LocalTime selectedEndTime;
        LocalTime startEastern = null;
        LocalTime endEastern = null;
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        Customer selectedCustomer = null;
        User selectedUser = null;

        while (complete != true) {
            try {
                title = titleTxt.getText();
                description = descriptionTxt.getText();
                location = locationTxt.getText();
                selectedContact = comboContact.getSelectionModel().getSelectedItem();
                contactID = selectedContact.getContact_ID();
                type = typeTxt.getText();
                selectedStartTime = comboStartTime.getSelectionModel().getSelectedItem();
                selectedEndTime = comboEndTime.getSelectionModel().getSelectedItem();
                startTime = LocalDateTime.of(selectedDate, selectedStartTime);
                startEastern = convertToEastern(startTime).toLocalTime();
                endTime = LocalDateTime.of(selectedDate, selectedEndTime);
                endEastern = convertToEastern(endTime).toLocalTime();
                selectedCustomer = comboCustomer.getSelectionModel().getSelectedItem();
                selectedUser = comboUser.getSelectionModel().getSelectedItem();

                if (selectedCustomer == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Customer Not Selected.");
                    alert.showAndWait();
                    return;

                }

                else if(selectedUser == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("User Not Selected.");
                    alert.showAndWait();
                    return;
                }
                else if(selectedEndTime.isBefore(selectedStartTime) || selectedStartTime.isAfter(selectedEndTime)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("End of appointment cannot be before start of appointment.");
                    alert.showAndWait();
                    return;

                }
                else if(startEastern.isBefore(open)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Start time is outside business hours.  Business Hours are 8:00am to 10:00pm Eastern Time.");
                    alert.showAndWait();
                    return;
                }
                else if(endEastern.isAfter(close)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("End time is outside business hours.  Business Hours are 8:00am to 10:00pm Eastern Time.");
                    alert.showAndWait();
                    return;
                }
                else if(titleTxt.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Please enter a title");
                    alert.showAndWait();
                    return;
                }
                else if(descriptionTxt.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Please enter a description.");
                    alert.showAndWait();
                    return;
                }
                else if(locationTxt.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Please enter a location.");
                    alert.showAndWait();
                    return;
                }
                else if(typeTxt.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Please enter a type.");
                    alert.showAndWait();
                    return;
                }
                else{
                    complete = true;
                }

            }
            catch(NullPointerException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please enter a valid value for each Text Field");
                alert.showAndWait();
                return;
            }



        }





        Appointment appointment = new Appointment(contactID, LocalDateTime.now(),currentUser.getUser_Name(),selectedCustomer.getCustomer_ID(),description, endTime, LocalDateTime.now(),currentUser.getUser_Name(), location, startTime, title, type, selectedUser.getUser_ID());

        while(overlapping != false){
            ObservableList<Appointment> allApps = AppointmentDaoImpl.getAllAppointments();
            ObservableList<Appointment> custAppoints = FXCollections.observableArrayList();

            for (int i = 0; i < allApps.size(); i++) {
                if (allApps.get(i).getCustomer_ID() == selectedCustomer.getCustomer_ID()) {
                    custAppoints.add(allApps.get(i));
                }
        }
            LocalDateTime oStart;
            LocalDateTime oEnd;


            for(int i = 0; i < custAppoints.size(); i++){
                oStart = custAppoints.get(i).getStart();
                oEnd = custAppoints.get(i).getEnd();

                if((startTime.isAfter(oStart) || startTime.isEqual(oStart)) && (startTime.isBefore(oEnd))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Overlapping Appointment");
                    alert.showAndWait();
                    return;
                }
                else if(endTime.isAfter(oStart) && (endTime.isBefore(oEnd) || endTime.isEqual(oEnd))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Overlapping Appointment");
                    alert.showAndWait();
                    return;
                }
                else if((startTime.isBefore(oStart) || startTime.isEqual(oStart)) && (endTime.isAfter(oEnd) || endTime.isEqual(oEnd))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Overlapping Appointment");
                    alert.showAndWait();
                    return;
                }
            }
            overlapping = false;
        }

        AppointmentDaoImpl.addAppointment(appointment);

        AppointmentController.refreshTableView();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method sets the appointment start time combo box.  The first choice is 0000 and then each
     * additional choice is in 15 minute increments.
     */
    private void setComboStartTime (){
        LocalTime start = LocalTime.of(0,0);
        LocalTime end = LocalTime.of(23,0);

        while(start.isBefore(end.plusSeconds(1))){
            comboStartTime.getItems().add(start);
            start = start.plusMinutes(15);
        }
        for(int i = 0; i < 3; i++){
            comboStartTime.getItems().add(start);
            start = start.plusMinutes(15);
        }
    }

    /**
     * This method takes a LocalDateTIme in as a parameter and then converts it to eastern
     * time and then returns that converted LocalDateTime.
     * @param selectedTime The LocalDateTime to convert.
     * @return timeConverted The LocalDateTime converted.
     */
    private LocalDateTime convertToEastern(LocalDateTime selectedTime){


        ZonedDateTime zdt = selectedTime.atZone(ZoneId.systemDefault());
        ZonedDateTime selEastern = zdt.withZoneSameInstant(ZoneId.of("UTC-05:00"));
        LocalDateTime timeConverted = selEastern.toLocalDateTime();

        return timeConverted;
    }

    /**
     * This method initializes the AddAppointments page.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            comboUser.setItems(UserDaoImpl.getAllUsers());
        } catch (Exception e) {
            e.printStackTrace();
        }
        comboContact.setItems(ContactDAOImpl.getAllContacts());
        comboCustomer.setItems(CustomersDaoImpl.getAllCustomers());
        setComboStartTime();

    }
}
