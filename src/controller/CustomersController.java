package controller;

import DAO.AppointmentDaoImpl;
import DAO.CustomersDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

    Stage stage;
    Parent scene;

    private Customer selectedCustomer = null;


    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> stateCol;

    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    @FXML
    private TableColumn<Customer, String> phoneNumberCol;

    /**
     * This method takes the user to the AddCustomer form.
     * @param event When the user clicks the add customer button.
     */
    @FXML
    private void onActionAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomerForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method passes the selected customer to the UpdateCustomerController and
     * takes the user to the UpdateCustomer form.
     * @param event When the user clicks the update customer button.
     */
    @FXML
    private void onActionUpdateCustomer(ActionEvent event) throws IOException {

        try {
            selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
            UpdateCustomerController.setUpdateCustomer(selectedCustomer);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/UpdateCustomerForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Select a customer.");
            alert.showAndWait();
            return;
        }

    }

    /**
     * This method deletes the selected customer from the allCustomers list and
     * from the customers table in the database.
     * @param event When the user clicks the delete customer button.
     */
    @FXML
    private void onActionDeleteCustomer(ActionEvent event) throws SQLException {

        try {
            ObservableList<Appointment> appointmentsSearch = FXCollections.observableArrayList();
            Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
            int customerID = selectedCustomer.getCustomer_ID();

            appointmentsSearch = AppointmentDaoImpl.getAllAppointments();

            for (int i = 0; i < appointmentsSearch.size(); i++) {
                if (appointmentsSearch.get(i).getCustomer_ID() == customerID) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setResizable(true);
                    alert.setTitle("Delete Associated Appointments");
                    alert.setContentText("Customer has appointments scheduled.");
                    alert.showAndWait();
                    return;
                }
            }

            CustomersDaoImpl.deleteCustomer(customerID);
            refreshTableView();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Customer Deleted");
            alert.setContentText(selectedCustomer.getCustomer_Name() + " deleted!");
            alert.showAndWait();
            return;
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Select a customer.");
            alert.showAndWait();
            return;
        }

    }

    /**
     * This method takes the user back to the Appointments form.
     * @param event When the user clicks the back button.
     */
    @FXML
    private void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method refreshes the customer table by clearing all customers
     * from the allCustomers observable list and then importing all customers
     * from the customers table and regenerating the allCustomers list.
     * */
    public static void refreshTableView() throws SQLException {
        CustomersDaoImpl.clearCustomers();
        CustomersDaoImpl.setAllCustomers();
    }

    /**
     * This method initializes the page by setting the customers table.
     * This method also has a lambda express that sets up the customersTable.  This
     * allows it to be used as part of an anonymous method and to streamline the code.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO

        tableSet cusTbl = () -> {
            customersTable.setItems(CustomersDaoImpl.getAllCustomers());
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
            stateCol.setCellValueFactory(new PropertyValueFactory<>("State"));
            postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
            phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        };
        cusTbl.setTable();



    }



}
