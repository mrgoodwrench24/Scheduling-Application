package Main;

import DAO.AppointmentDaoImpl;
import DAO.ContactDAOImpl;
import DAO.DBConnection;
import DAO.DivisionsDAOImpl;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class creates an appointment scheduling application.  This class loads the AppointmentForm.
 *
 * @author Michael White 000547320
 * */
public class Main extends Application {

    static Stage stage;

    @Override
    public void start(Stage stage){

        Main.stage = stage;

        //Locale.setDefault(Locale.CANADA_FRENCH);


        ResourceBundle Nat = ResourceBundle.getBundle("utilities/Nat");

        LoginController.setLang(Nat.getLocale().getDisplayLanguage());


        Parent root = null;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
            loader.setResources(Nat);
            root = loader.load();

            stage.setTitle("Appointment Scheduler");
            Scene scene = new Scene(root, 1200, 800);
            stage.setScene(scene);
            stage.show();

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * This class passes in any arguments or parameters to start the program.  I have put test data here.
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        DBConnection.openConnection();

        try {
            AppointmentDaoImpl.setAllAppointments();
            ContactDAOImpl.setAllContacts();
            DivisionsDAOImpl.setDivisions();
            DivisionsDAOImpl.setCountries();
            DivisionsDAOImpl.setStates();
            DAO.CustomersDaoImpl.setAllCustomers();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        launch(args);


    }
}
