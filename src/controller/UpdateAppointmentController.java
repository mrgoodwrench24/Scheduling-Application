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

public class UpdateAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    private static Appointment selectedAppointment = null;

    /**
     * This method sets the selected appointment from the Appointments Form.  The
     * AppointmentController calls this method to pass the selected appointment to
     * the UpdateAppointmentController.
     * @param updateAppointment The selected appointment from the appointments form.
     */
    public static void setSelectedAppointment(Appointment updateAppointment) {
        UpdateAppointmentController.selectedAppointment = updateAppointment;
    }

    private LocalDate appointmentDate;

    private LocalDate selectedDate;

    private Customer appointmentCustomer;

    private final User currentUser = UserDaoImpl.getCurrent();

    @FXML
    private TextField appointmentIDTxt;

    @FXML
    private ComboBox<Contact> comboContact;

    @FXML
    private ComboBox<Customer> comboCustomer;

    @FXML
    private ComboBox<LocalTime> comboEndTime;

    @FXML
    private ComboBox<LocalTime> comboStartTime;

    @FXML
    private ComboBox<User> comboUser;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descriptionTxt;

    @FXML
    private TextField locationTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    /**
     * This method sets the date from the DatePicker fxml object and set it as the selectedDate object.
     * @param event When the user selects the date picker.
     */
    @FXML
    void handleDatePicker(ActionEvent event) {
        selectedDate = datePicker.getValue();
    }

    /**
     * This method selects the end time of the selected appointment when the comboStartTime
     * is selected.
     * @param event When the user selects the comboStartTime
     */
    @FXML
    void handleStartTime(ActionEvent event) {

    }

    /**
     * This method sends the user back to the Appointment page.
     * @param event When the user clicks the back button.
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method saves all information input from the user and saves a new
     * appointment object.  This appointment object is passed into the database
     * and the appointment is updated in the database that matches the selected
     * appointments appointment ID.  The allAppointments list is then cleared and
     * appointments are read from the database and each line is entered as an
     * appointment object in the allAppointmenst list.  Finally it takes the
     * user back to the Appointments Form.
     * @param event When the user clicks the save button.
     */
    @FXML
    void onActionSave(ActionEvent event) throws Exception {
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
        Appointment updatedApp = selectedAppointment;
        User selectedUser = null;

        if(selectedDate == null){
            selectedDate = datePicker.getValue();
        }

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

        updatedApp.setContact_ID(contactID);
        updatedApp.setTitle(title);
        updatedApp.setDescription(description);
        updatedApp.setLocation(location);
        updatedApp.setType(type);
        updatedApp.setStart(startTime);
        updatedApp.setEnd(endTime);
        updatedApp.setLast_Update(LocalDateTime.now());
        updatedApp.setLast_Update_By(currentUser.getUser_Name());
        updatedApp.setCustomer_ID(selectedCustomer.getCustomer_ID());
        updatedApp.setUser_ID(selectedUser.getUser_ID());

        while(overlapping){
            ObservableList<Appointment> allApps = AppointmentDaoImpl.getAllAppointments();
            ObservableList<Appointment> custAppoints = FXCollections.observableArrayList();

            for (int i = 0; i < allApps.size(); i++) {
                if (allApps.get(i).getCustomer_ID() == selectedCustomer.getCustomer_ID()) {
                    custAppoints.add(allApps.get(i));
                }
            }
            LocalDateTime oStart;
            LocalDateTime oEnd;


            for(int i = 0; i < custAppoints.size(); i++) {
                oStart = custAppoints.get(i).getStart();
                oEnd = custAppoints.get(i).getEnd();
                if (custAppoints.get(i).getAppointment_ID() == updatedApp.getAppointment_ID()) {
                    continue;
                }
                else {
                    if ((startTime.isAfter(oStart) || startTime.isEqual(oStart)) && (startTime.isBefore(oEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Warning Dialog");
                        alert.setContentText("Overlapping Appointment");
                        alert.showAndWait();
                        return;
                    } else if (endTime.isAfter(oStart) && (endTime.isBefore(oEnd) || endTime.isEqual(oEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Warning Dialog");
                        alert.setContentText("Overlapping Appointment");
                        alert.showAndWait();
                        return;
                    } else if ((startTime.isBefore(oStart) || startTime.isEqual(oStart)) && (endTime.isAfter(oEnd) || endTime.isEqual(oEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Warning Dialog");
                        alert.setContentText("Overlapping Appointment");
                        alert.showAndWait();
                        return;
                    }
                }
            }
            overlapping = false;
        }

        AppointmentDaoImpl.updateAppointment(updatedApp);
        AppointmentController.refreshTableView();

        selectedAppointment = null;

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method takes a LocalDateTime as a parameter and returns the localTime of the LocalDateTime.
     * @param selectedDateTime The LocalDateTime to convert.
     * @return time Returns the converted LocalTime.
     */
    private LocalTime localDateTimeConversion(LocalDateTime selectedDateTime){
        LocalTime time;
        appointmentDate = selectedDateTime.toLocalDate();
        time = selectedDateTime.toLocalTime();
        return time;
    }

    /**
     * This method sets the appointment start time combo box.  The first choice is 0000 and then each
     * additional choice is in 15 minute increments.  It then selects the start time from the selected
     * appointments start time.
     */
    private void setComboStartTime (){
        LocalTime start = LocalTime.of(0,0);
        LocalTime end = LocalTime.of(23,0);

        while(start.isBefore(end.plusSeconds(1))){
            comboStartTime.getItems().add(start);
            comboEndTime.getItems().add(start);
            start = start.plusMinutes(15);
        }
        for(int i = 0; i < 3; i++){
            comboStartTime.getItems().add(start);
            comboEndTime.getItems().add(start);
            start = start.plusMinutes(15);
        }
    }

    /**
     * This method takes a LocalDateTIme in as a parameter and then converts it to eastern
     * time and then returns that converted LocalDateTime.
     * @param selectedTime The LocalDateTime to convert to eastern time.
     * @return timeConverted Returns the converted LocalDateTime.
     */
    private LocalDateTime convertToEastern(LocalDateTime selectedTime){


        ZonedDateTime zdt = selectedTime.atZone(ZoneId.systemDefault());
        ZonedDateTime selEastern = zdt.withZoneSameInstant(ZoneId.of("UTC-05:00"));
        LocalDateTime timeConverted = selEastern.toLocalDateTime();

        return timeConverted;
    }


    /**
     * This method initializes the UpdateAppointments page.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setComboStartTime();
        appointmentCustomer = CustomersDaoImpl.searchCustomerID(selectedAppointment.getCustomer_ID());
        appointmentIDTxt.setText(String.valueOf(selectedAppointment.getAppointment_ID()));
        comboContact.setItems(ContactDAOImpl.getAllContacts());
        comboContact.getSelectionModel().select(ContactDAOImpl.searchContact(selectedAppointment.getContact_ID()));
        comboStartTime.getSelectionModel().select(localDateTimeConversion(selectedAppointment.getStart()));
        comboEndTime.getSelectionModel().select(localDateTimeConversion(selectedAppointment.getEnd()));
        datePicker.setValue(appointmentDate);
        descriptionTxt.setText(selectedAppointment.getDescription());
        locationTxt.setText(selectedAppointment.getLocation());
        titleTxt.setText(selectedAppointment.getTitle());
        typeTxt.setText(selectedAppointment.getType());
        comboCustomer.setItems(CustomersDaoImpl.getAllCustomers());
        comboCustomer.getSelectionModel().select(CustomersDaoImpl.searchCustomerID(selectedAppointment.getCustomer_ID()));
        try {
            comboUser.setItems(UserDaoImpl.getAllUsers());
            comboUser.getSelectionModel().select(UserDaoImpl.searchUserID(selectedAppointment.getUser_ID()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
