package controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDAOImpl;
import DAO.CustomersDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    Stage stage;
    Parent scene;

    private final ObservableList<String> allTypes = FXCollections.observableArrayList();
    private final ObservableList<Appointment> allAppointments = AppointmentDaoImpl.getAllAppointments();
    private final ObservableList<Contact> allContacts = ContactDAOImpl.getAllContacts();
    private final ObservableList<String> allMonths = FXCollections.observableArrayList();
    private final ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private String appType;
    private String appMonth;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    @FXML
    private TableView<Appointment> appointmentsTableView;

    @FXML
    private Label canadianCustomersLabel;

    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endTimeCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private Label numberLabel;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startTimeCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private Label ukCustomersLabel;

    @FXML
    private Label usCustomersLabel;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

    /**
     * This method sets the appointment table based on the contact selected
     * in the contactComboBox.  It shows all appointments for the selected
     * contact.
     * @param event When the user activates the contactComboBox.
     */
    @FXML
    private void onActionContacts(ActionEvent event) throws Exception {
        filteredAppointments.clear();
        Contact selectedContact = contactComboBox.getValue();
        try {
            for (Appointment search : allAppointments) {
                if (search.getContact_ID() == selectedContact.getContact_ID()) {
                    filteredAppointments.add(search);
                }

                appointmentsTableView.setItems(filteredAppointments);

            }
        }
        catch(NullPointerException e){

        }



    }

    /**
     * This method is called when the monthComboBox is used and if
     * both the monthComboBox and typeComboBox are selected it
     * calls the setCount() method. If the typeComboBox is not selected
     * the method returns void.
     * @param event When the user activates the monthComboBox.
     */
    @FXML
    private void onActionMonth(ActionEvent event) {
        appMonth = monthComboBox.getValue();

        try{
            if(appMonth.isBlank() && appType.isBlank()){
                return;
            }
            else{
                setCount(appType,appMonth);
            }
        }
        catch(NullPointerException e){

        }


    }

    /**
     * This method is called when the typeComboBox is used and if
     * both the typeComboBox and monthComboBox are selected it
     * calls the setCount() method. If the monthComboBox is not selected
     * the method returns void.
     * @param event When the user activates the typeComboBox.
     */
    @FXML
    private void onActionType(ActionEvent event) {
        appType = typeComboBox.getValue();

        try{
            if(appMonth.isBlank() && appType.isBlank()){
                return;
            }
            else{
                setCount(appType,appMonth);
            }
        }
        catch(NullPointerException e){

        }


    }

    /**
     * This method counts the number of a certain type of appointment based on a selected month.  The type and month
     * are passed into the method and then the allAppointments observable list is searched for appointments matching
     * the selected type and month.
     * @param selectedType The selected type chosen in the typeComboBox.
     * @param selectedMonth The selected month chosen in the monthComboBox.
     */
    private void setCount(String selectedType, String selectedMonth) {
        int count = 0;
        for (Appointment search : allAppointments) {
            if (search.getType().contentEquals(selectedType) && search.getStart().getMonth().toString().contentEquals(selectedMonth)) {
                count = count + 1;
            }
        }
        numberLabel.setText(count + " Appointments");
    }

    /**
     * This method takes the user back to the appointments form.
     * @param event When the user clicks the back button.
     */
    @FXML
    private void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method generates the allTypes and allMonths list to be used for combo boxes.
     */
    private void setTypes(){

        for(int i = 0; i < allAppointments.size(); i++){

                if(!allTypes.contains(allAppointments.get(i).getType())){
                    allTypes.add(allAppointments.get(i).getType());
                }
            }

        allMonths.addAll("JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER");
    }

    /**
     * This method calculates the number of customers in each region and displays this to the user.
     */
    private void customersByRegion(){
        allCustomers = CustomersDaoImpl.getAllCustomers();
        int usCount = 0;
        int canCount = 0;
        int ukCount = 0;

        for(int i = 0; i < allCustomers.size(); i++){
            if(allCustomers.get(i).getDivision_ID() <= 55){
                usCount++;
            }
            else if(allCustomers.get(i).getDivision_ID() >= 59 && allCustomers.get(i).getDivision_ID() <= 75){
                canCount++;
            }
            else if(allCustomers.get(i).getDivision_ID() >= 99){
                ukCount++;
            }
        }
        usCustomersLabel.setText(usCount + " customers in US.");
        canadianCustomersLabel.setText(canCount + " customers in Canada.");
        ukCustomersLabel.setText(ukCount + " customers in the United Kingdom.");
    }


    /**
     * This method initializes the page by setting all combo boxes and table views.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTypes();

        typeComboBox.setItems(allTypes);
        monthComboBox.setItems(allMonths);
        contactComboBox.setItems(allContacts);

        appointmentsTableView.setItems(filteredAppointments);
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));

        customersByRegion();



    }

}
