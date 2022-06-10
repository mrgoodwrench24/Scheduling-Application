package controller;

import DAO.AppointmentDaoImpl;
import DAO.DBConnection;
import DAO.UserDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.User;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ResourceBundle;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField passwordTxt;

    @FXML
    private TextField userNameTxt;

    @FXML
    private Label locationLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label titleLabel;

    ResourceBundle Nat;

    private static String lang;

    /**
     * This method takes the username and password entered by the user and
     * searches the allUsers observable list for a matching username.  Then compares
     * the password entered to the password of the User object.  If both the username
     * and password match the user is permitted to login.  If not an error stating
     * either invalid username or invalid password is displayed.
     * @param event When the user clicks the login button.
     */
    @FXML
    private void onActionLogin(ActionEvent event) throws Exception {
        String userName = userNameTxt.getText();
        String password = passwordTxt.getText();
        Boolean userMatch = FALSE;
        Boolean passMatch = FALSE;
        User user = null;

        while (userMatch == FALSE) {
            if(UserDaoImpl.searchUser(userName) != null){
                user = UserDaoImpl.searchUser(userName);
                userMatch = TRUE;

            }
            else{
                if(lang == "français"){
                    log(FALSE);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Nom d'utilisateur invalide");
                    alert.showAndWait();
                    return;
                }
                else if(lang == "English"){
                    log(FALSE);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Invalid UserName");
                    alert.showAndWait();
                    return;
                }


            }

        }

        while (passMatch == FALSE){
            if (user.getPassword().contentEquals(password)){
                passMatch = TRUE;
                log(TRUE);
            }
            else {
                if (lang == "français") {
                    log(FALSE);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Mot de passe incorrect");
                    alert.showAndWait();
                    return;
                } else if (lang == "English") {
                    log(FALSE);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Invalid Password");
                    alert.showAndWait();
                    return;
                }
            }
        }

        AppointmentDaoImpl.upcommingAppointments();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method exits the program.
     * @param event When the user clicks the exit button.
     */
    public void onActionExit(ActionEvent event) {
         DBConnection.closeConnection();
        System.exit(0);
    }

    private void log(Boolean success) throws IOException {
        String filename = "src\\login_activity.txt";

        FileWriter fwriter = new FileWriter(filename, true);

         PrintWriter output = new PrintWriter(fwriter);

         if(success.equals(TRUE)){
             output.println(userNameTxt.getText() + " successfully logged in on: " + LocalDate.now() + " at " + LocalTime.now() + ".");
         }
         else{
             output.println(userNameTxt.getText() + " failed to log in on: " + LocalDate.now() + " at " + LocalTime.now() + ".");
         }

         output.close();

    }

    public static void setLang(String language){
        lang = language;
    }

    public static String getLang(){
        return lang;
    }

    /**
     * This method initializes the page and if the users location is from a french speaking
     * country, the text is translated to french.  Otherwise the page is shown in english.
     */
    @Override
    public void initialize(URL url, ResourceBundle Nat) {
        this.Nat = Nat;
        ZoneId zone = ZoneId.systemDefault();
        String zoneString = zone.toString();
        locationLabel.setText(zoneString);

        usernameLabel.setText(Nat.getString("Username"));
        passwordLabel.setText(Nat.getString("Password"));
        titleLabel.setText(Nat.getString("Title"));
        loginButton.setText(Nat.getString("Login"));
        exitButton.setText(Nat.getString("Exit"));
        System.out.println(lang);


    }

}