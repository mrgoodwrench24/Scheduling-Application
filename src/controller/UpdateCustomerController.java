package controller;

import DAO.CustomersDaoImpl;
import DAO.DivisionsDAOImpl;
import DAO.UserDaoImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.FirstLevelDivisions;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {
    Stage stage;
    Parent scene;

    private static Customer updateCustomer = null;

    private static Country customerCountry = null;

    private static FirstLevelDivisions customerState = null;

    /**
     * This method sets the updateCustomer with the parameter selectedCustomer
     * and sets the state and country from the selectedCustomer object.
     * @param selectedCustomer The selected customer from the customers form.
     */
    public static void setUpdateCustomer(Customer selectedCustomer){
        updateCustomer = selectedCustomer;
        setState(updateCustomer.getDivision_ID());
        setCountry(updateCustomer.getDivision_ID());
    }

    /**
     * This method sets the customers country based on the passed in
     * parameter stateID.
     * @param stateID The state ID associated with the customer object.
     */
    private static void setCountry(int stateID){
        customerCountry = DivisionsDAOImpl.setCountry(stateID);

    }

    /**
     * This method sets the customers state based on the passed in parameter stateID.
     * @param stateID The state ID associated with the customer object.
     */
    private static void setState(int stateID){
        ObservableList<FirstLevelDivisions> searchList = DivisionsDAOImpl.getAllDivisions();

        for(FirstLevelDivisions search : searchList) {
            if (search.getDivision_ID() == stateID) {
                customerState = search;
                break;
            }
        }

    }

    @FXML
    private TextField customerNameTxt;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private ComboBox<FirstLevelDivisions> stateComboBox;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField postalTxt;

    @FXML
    private TextField phoneNumberTxt;

    @FXML
    private Label customerIDLabel;

    /**
     * This method sets the statesComboBox with the correct state/provinces for the selected country
     * when the country is selected with the countryComboBox. This also selects the correct country
     * for the countryComboBox.
     * @param event When the user selects the countryComboBox.
     */
    @FXML
    private void onActionCountries(ActionEvent event) {

        if(countryComboBox.getSelectionModel().getSelectedItem().getCountry().contentEquals("U.S")){
            stateComboBox.setItems(DivisionsDAOImpl.getUsStates());
        }
        else if (countryComboBox.getSelectionModel().getSelectedItem().getCountry().contentEquals("UK")){
            stateComboBox.setItems(DivisionsDAOImpl.getUkStates());
        }
        else if (countryComboBox.getSelectionModel().getSelectedItem().getCountry().contentEquals("Canada")) {
            stateComboBox.setItems(DivisionsDAOImpl.getCnStates());
        }
        else{
            stateComboBox.setItems(null);
        }
    }

    /**
     * This method selects the customers state in the stateComboBox.
     */
    private void setStateList(){
        if(countryComboBox.getSelectionModel().getSelectedItem().getCountry().contentEquals("U.S")){
            stateComboBox.setItems(DivisionsDAOImpl.getUsStates());
            stateComboBox.getSelectionModel().select(customerState);
        }
        else if (countryComboBox.getSelectionModel().getSelectedItem().getCountry().contentEquals("UK")){
            stateComboBox.setItems(DivisionsDAOImpl.getUkStates());
            stateComboBox.getSelectionModel().select(customerState);
        }
        else if (countryComboBox.getSelectionModel().getSelectedItem().getCountry().contentEquals("Canada")) {
            stateComboBox.setItems(DivisionsDAOImpl.getCnStates());
            stateComboBox.getSelectionModel().select(customerState);
        }
        else{
            stateComboBox.setItems(null);
        }
    }

    /**
     * This method saves a new customer to the database and refreshes the all customers view
     * on the Customers Form.  It then returns the user to the Customers Form.
     * @param event When the user clicks the save button.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {

        String customerName = customerNameTxt.getText();
        int stateID = stateComboBox.getSelectionModel().getSelectedItem().getDivision_ID();
        String state = stateComboBox.getSelectionModel().getSelectedItem().getDivision();
        String address = addressTxt.getText();
        String zip = postalTxt.getText();
        String phone = phoneNumberTxt.getText();
        User user = UserDaoImpl.getCurrent();
        String userName = user.getUser_Name();

        Customer newCustomer = null;
        newCustomer = updateCustomer;

        newCustomer.setCustomer_Name(customerName);
        newCustomer.setAddress(address);
        newCustomer.setPostal_Code(zip);
        newCustomer.setPhone(phone);
        newCustomer.setLast_Update(LocalDateTime.now());
        newCustomer.setLast_Updated_By(userName);
        newCustomer.setDivision_ID(stateID);
        newCustomer.setState(state);

        CustomersDaoImpl.updateCustomer(newCustomer);
        CustomersController.refreshTableView();

        updateCustomer = null;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method takes the user back to the Customers Form.
     * @param event When the user clicks the back button.
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method initializes the Update Customer Controller Form.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        customerIDLabel.setText(String.valueOf(updateCustomer.getCustomer_ID()));
        customerNameTxt.setText(updateCustomer.getCustomer_Name());
        countryComboBox.setItems(DivisionsDAOImpl.getAllCountries());
        countryComboBox.getSelectionModel().select(customerCountry);
        setStateList();
        addressTxt.setText(updateCustomer.getAddress());
        postalTxt.setText(updateCustomer.getPostal_Code());
        phoneNumberTxt.setText(updateCustomer.getPhone());



    }
}
