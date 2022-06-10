package controller;

import DAO.CustomersDaoImpl;
import DAO.DivisionsDAOImpl;
import DAO.UserDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.FirstLevelDivisions;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    Stage stage;
    Parent scene;

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

    /**
     * This method sets the statesComboBox with the correct state/provinces for the selected country
     * when the country is selected with the countryComboBox.
     * @param event This activates when the countryComboBox is clicked on.
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
     * This method takes all the parameters from the text boxes and input boxes and sets
     * variables for all the parameters needed to make a Customer Object.  The new
     * Customer object is then saved to the database.  And then refreshes the customer
     * table on the Customers form.  It then returns the user to the screen they came from.
     * @param event When the user clicks the save button.
     */
    @FXML
    private void onActionSave(ActionEvent event) throws IOException, SQLException {

        String customerName = customerNameTxt.getText();
        int stateID = stateComboBox.getSelectionModel().getSelectedItem().getDivision_ID();
        String state = stateComboBox.getSelectionModel().getSelectedItem().getDivision();
        String address = addressTxt.getText();
        String zip = postalTxt.getText();
        String phone = phoneNumberTxt.getText();
        User user = UserDaoImpl.getCurrent();
        String userName = user.getUser_Name();

        Customer newCustomer = new Customer(customerName,address,zip,phone,userName,userName,stateID,state);

        CustomersDaoImpl.addCustomer(newCustomer);

        CustomersController.refreshTableView();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method returns the user to the CustomersForm.
     * @param event When the user clicks on the back button.
     */
    @FXML
    private void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method initializes the page and sets the countryComboBox.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryComboBox.setItems(DivisionsDAOImpl.getAllCountries());
    }
}
