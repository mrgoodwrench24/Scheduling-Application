package controller;

import DAO.AppointmentDaoImpl;
import DAO.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    private Appointment selectedAppointment = null;

    @FXML
    private Label dateLabel;

    @FXML
    private TableView<Appointment> appointmentsTableView;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, String> contactCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startTimeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endTimeCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;

    @FXML
    private TableColumn<Appointment, Integer> userIDCol;

    @FXML
    private ToggleGroup vToggle;

    /**
     * This method takes the user to the AddAppointment form.
     * @param event When the user clicks on the add appointment button.
     */
    @FXML
    private void onActionAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method sets the selected appointment on the appointments table and passes
     * it to the UpdatedAppointmentController form.  Then takes the user to the
     * UpdatedAppointment Form.
     * @param event When the user clicks the update appointment button.
     */
    @FXML
    private void onActionUpdateAppointment(ActionEvent event) throws IOException {
        try
        {
            selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
            UpdateAppointmentController.setSelectedAppointment(selectedAppointment);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointmentForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        }
        catch(LoadException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Select an appointment");
            alert.showAndWait();
            return;

        }


    }


    /**
     * This method deletes the selected appointment from the allAppointments list and from the
     * appointments database.
     * @param event When the user clicks the delete button.
     */
    @FXML
    private void onActionDeleteAppointment(ActionEvent event) throws Exception {
        try {
            Appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
            int appointmentID = selectedAppointment.getAppointment_ID();
            AppointmentDaoImpl.deleteAppointment(appointmentID);
            refreshTableView();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Appointment Canceled");
            alert.setContentText("Appointment " + selectedAppointment.getAppointment_ID() + ": " + selectedAppointment.getType() + " canceled!");
            alert.showAndWait();
            return;
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Select an appointment");
            alert.showAndWait();
            return;
        }
    }

    /**
     * This method takes the user to the Customers form.
     * @param event When the user clicks the customers button.
     */
    @FXML
    private void onActionCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method takes the user to the Reports form.
     * @param event When the user clicks the reports button.
     */
    @FXML
    private void onActionReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * This method exits the application.
     * @param event When the user clicks the exit button.
     */
    @FXML
    private void onActionExit(ActionEvent event) {

        DBConnection.closeConnection();
        System.exit(0);
    }

    /**
     * This action changes the appointments table to only show appointments in the
     * current month.
     * @param event When the user clicks the month radio button.
     */
    @FXML
    private void onActionMonth(ActionEvent event) {

        try {
            ObservableList<Appointment> filteredMonthlyAppointments = FXCollections.observableArrayList();
            ObservableList<Appointment> allAppointments = AppointmentDaoImpl.getAllAppointments();


            LocalDate startOfMonth = LocalDate.now();
            LocalDate endOfMonth = LocalDate.now();

            LocalDate currentMonth = LocalDate.now();

            while (startOfMonth.getMonth().equals(currentMonth.getMonth())) {
                startOfMonth = startOfMonth.minusDays(1);
            }

            while (endOfMonth.getMonth().equals(currentMonth.getMonth())) {
                endOfMonth = endOfMonth.plusDays(1);
            }

            for (Appointment searchApp : allAppointments) {
                LocalDate appointmentDate = searchApp.getStart().toLocalDate();
                if (appointmentDate.isAfter(startOfMonth) && appointmentDate.isBefore(endOfMonth)) {
                    filteredMonthlyAppointments.add(searchApp);
                }
            }

            appointmentsTableView.setItems(filteredMonthlyAppointments);
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("No appointments this week");
            alert.showAndWait();
            return;
        }

    }

    /**
     * This method changes the appointments table to only show appointments in the current
     * week of when the application is being used.
     * @param event When the user clicks the week radio button.
     */
    @FXML
    private void onActionWeek(ActionEvent event) throws Exception {

        try {
            ObservableList<Appointment> filteredWeeklyAppointments = FXCollections.observableArrayList();
            ObservableList<Appointment> allAppointments = AppointmentDaoImpl.getAllAppointments();


            LocalDate startOfWeek = LocalDate.now();
            LocalDate endOfWeek = LocalDate.now();

            if(startOfWeek.getDayOfWeek() == DayOfWeek.MONDAY){
            }
            else{
                while (startOfWeek.getDayOfWeek() != DayOfWeek.MONDAY) {
                    if(startOfWeek.getDayOfWeek() == DayOfWeek.MONDAY){
                        startOfWeek = startOfWeek.minusDays(1);
                    }
                    else{
                        startOfWeek = startOfWeek.minusDays(1);
                    }

                }
            }


            if(endOfWeek.getDayOfWeek() == DayOfWeek.SUNDAY){
            }
            else{
                while (endOfWeek.getDayOfWeek() != DayOfWeek.SUNDAY) {
                    if(endOfWeek.getDayOfWeek() == DayOfWeek.SUNDAY){
                    }
                    else{
                        endOfWeek = endOfWeek.plusDays(1);
                    }

                }

            }

            for (Appointment searchApp : allAppointments) {
                LocalDate appointmentDate = searchApp.getStart().toLocalDate();
                if ((appointmentDate.isAfter(startOfWeek) || appointmentDate.isEqual(startOfWeek)) && (appointmentDate.isBefore(endOfWeek) || appointmentDate.isEqual(endOfWeek))) {
                    filteredWeeklyAppointments.add(searchApp);
                }
            }

            appointmentsTableView.setItems(filteredWeeklyAppointments);
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("No appointments this week");
            alert.showAndWait();
            return;
        }


    }

    /**
     * This method changes the appointments table to show all appointments in the database.
     * @param event When the user clicks the all appointments radio button.
     */
    @FXML
    private void onActionAllAppointments(ActionEvent event) {
        appointmentsTableView.setItems(AppointmentDaoImpl.getAllAppointments());

    }

    /**
     * This method clears allAppointments list and then imports all appointments back
     * into the program and regenerates the allAppointments list.
     */
    public static void refreshTableView() throws Exception {
        AppointmentDaoImpl.clearAppointments();
        AppointmentDaoImpl.setAllAppointments();
    }






    /**
     * This method initializes the Appointment Form by setting the table view and the date and time label.
     * This method also has a lambda express that sets up the appointmentsTableView.  This
     * allows it to be used as part of an anonymous method and to streamline the code.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //TODO

        dateLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE MMMM dd yyyy\n                   HH:mm")));

        tableSet appTbl = () ->{
            appointmentsTableView.setItems(AppointmentDaoImpl.getAllAppointments());
            appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
            startTimeCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
            endTimeCol.setCellValueFactory(new PropertyValueFactory<>("End"));
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
        };
        appTbl.setTable();

    }



}
