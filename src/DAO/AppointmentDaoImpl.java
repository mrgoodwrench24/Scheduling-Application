package DAO;


import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;
import model.User;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class AppointmentDaoImpl {

    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * This method reads in all appointments from the appointments table in the database and saves each appointment and its details as an appointment object.  All appointment objects are saved to the allAppointmenst observable list.
     */
    public static void setAllAppointments() throws Exception {
        String sqlStatement = "select * from appointments";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int Appointment_ID = result.getInt("Appointment_ID");
            int Contact_ID = result.getInt("Contact_ID");
            LocalDateTime Create_Date = result.getTimestamp("Create_Date").toLocalDateTime();
            String Created_By = result.getString("Created_By");
            int Customer_ID = result.getInt("Customer_ID");
            String Description = result.getString("Description");
            LocalDate endDate = result.getDate("End").toLocalDate();
            LocalTime endTime = result.getTime("End").toLocalTime();
            LocalDateTime EndUTC = endDate.atTime(endTime);
            ZonedDateTime endZT = EndUTC.atZone(ZoneId.of("UTC"));
            ZonedDateTime endLocal = endZT.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
            LocalDateTime End = endLocal.toLocalDateTime();
            LocalDate lastUpdateDate = result.getDate("Last_Update").toLocalDate();
            LocalTime lastUpdateTime = result.getTime("Last_Update").toLocalTime();
            LocalDateTime Last_Update = lastUpdateDate.atTime(lastUpdateTime);
            String Last_Updated_By = result.getString("Last_Updated_By");
            String Location = result.getString("Location");
            LocalDate startDate = result.getDate("Start").toLocalDate();
            LocalTime startTime = result.getTime("Start").toLocalTime();
            LocalDateTime StartUTC = startDate.atTime(startTime);
            ZonedDateTime StartZT = StartUTC.atZone(ZoneId.of("UTC"));
            ZonedDateTime StartLocal = StartZT.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
            LocalDateTime Start = StartLocal.toLocalDateTime();
            String Title = result.getString("Title");
            String Type = result.getString("Type");
            int User_ID = result.getInt("User_ID");
            Appointment appointmentResult = new Appointment(Appointment_ID, Contact_ID,Create_Date,Created_By,Customer_ID,Description,End,Last_Update,Last_Updated_By,Location,Start,Title,Type,User_ID);
            allAppointments.add(appointmentResult);
        }
    }

    /**
     * This method returns the observable list allAppointments
     * @return allAppointments Returns the allAppointments observable list.
     */
    public static ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }

    /**
     * This method saves a new appointment into the database from an appointment object.
     * @param newAppointment This is the new appointment object to be saved to the database.
     */
    public static void addAppointment (Appointment newAppointment) throws SQLException {
        String sqlStatement = "INSERT INTO appointments(Title,Description,Location,Type,Start,End,Create_Date,Created_By,Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
        DBConnection.setPreparedStatement(DBConnection.conn,sqlStatement);
        PreparedStatement psAddApp = (PreparedStatement) DBConnection.getPreparedStatement();

        User currentUser = UserDaoImpl.getCurrent();

        String title = newAppointment.getTitle();
        String Description = newAppointment.getDescription();
        String Location = newAppointment.getLocation();
        String Type = newAppointment.getType();
        LocalDateTime startDefault = newAppointment.getStart();
        ZonedDateTime startLocal = startDefault.atZone(ZoneId.systemDefault());
        ZonedDateTime startUTC = startLocal.withZoneSameInstant(ZoneOffset.UTC);
        LocalDateTime Start = startUTC.toLocalDateTime();
        LocalDateTime endDefault = newAppointment.getEnd();
        ZonedDateTime endLocal = endDefault.atZone(ZoneId.systemDefault());
        ZonedDateTime endUTC = endLocal.withZoneSameInstant(ZoneOffset.UTC);
        LocalDateTime End = endUTC.toLocalDateTime();
        LocalDateTime createTimeLocal = LocalDateTime.now();
        ZonedDateTime cl = ZonedDateTime.of(createTimeLocal, ZoneId.systemDefault());
        ZonedDateTime clUTC = cl.withZoneSameInstant(ZoneOffset.UTC);
        Timestamp Create_Date = Timestamp.valueOf(clUTC.toLocalDateTime());
        String Created_By = currentUser.getUser_Name();
        LocalDateTime updateLocal = LocalDateTime.now();
        ZonedDateTime ld = ZonedDateTime.of(updateLocal, ZoneId.systemDefault());
        ZonedDateTime ldUTC = ld.withZoneSameInstant(ZoneOffset.UTC);
        Timestamp Last_Update = Timestamp.valueOf(ldUTC.toLocalDateTime());
        String Last_Updated_By = newAppointment.getLast_Update_By();
        int Customer_ID = newAppointment.getCustomer_ID();
        int User_ID = currentUser.getUser_ID();
        int Contact_ID = newAppointment.getContact_ID();

        psAddApp.setString(1, title);
        psAddApp.setString(2, Description);
        psAddApp.setString(3, Location);
        psAddApp.setString(4, Type);
        psAddApp.setString(5, String.valueOf(Start));
        psAddApp.setString(6, String.valueOf(End));
        psAddApp.setString(7, String.valueOf(Create_Date));
        psAddApp.setString(8, Created_By);
        psAddApp.setString(9, String.valueOf(Last_Update));
        psAddApp.setString(10, Last_Updated_By);
        psAddApp.setString(11, String.valueOf(Customer_ID));
        psAddApp.setString(12, String.valueOf(User_ID));
        psAddApp.setString(13, String.valueOf(Contact_ID));

        psAddApp.execute();
    }

    /**
     * This method clears the observable list allAppointments.
     */
    public static void clearAppointments(){
        allAppointments.clear();
    }

    /**
     * This method deletes an appointment from the allAppointments list by using the Appointment_ID parameter passed in.
     * @param Appointment_ID This is the ID of the appointment to be deleted so you can only delete an appointment with that ID.
     */
    public static void deleteAppointment(int Appointment_ID) throws SQLException {
        String sqlStatement = "delete from appointments where Appointment_ID = ?";
        DBConnection.setPreparedStatement(DBConnection.conn,sqlStatement);
        PreparedStatement delAppStatement = (PreparedStatement) DBConnection.getPreparedStatement();
        delAppStatement.setString(1, String.valueOf(Appointment_ID));
        delAppStatement.execute();
    }

    /**
     * This method is passed an appointment object and then based on the appointment id of the object it updates the corresponding appointment in the database.
     * @param updatedAppointment This is the updated appointment object to be saved to the database.
     */
    public static void updateAppointment(Appointment updatedAppointment) throws SQLException {
        String sqlStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?;";
        DBConnection.setPreparedStatement(DBConnection.conn,sqlStatement);
        PreparedStatement psUpApp = (PreparedStatement) DBConnection.getPreparedStatement();

        String Title = updatedAppointment.getTitle();
        String Description = updatedAppointment.getDescription();
        String Location = updatedAppointment.getLocation();
        String Type = updatedAppointment.getType();
        LocalDateTime startDefault = updatedAppointment.getStart();
        ZonedDateTime startLocal = startDefault.atZone(ZoneId.systemDefault());
        ZonedDateTime startUTC = startLocal.withZoneSameInstant(ZoneOffset.UTC);
        LocalDateTime Start = startUTC.toLocalDateTime();
        LocalDateTime endDefault = updatedAppointment.getEnd();
        ZonedDateTime endLocal = endDefault.atZone(ZoneId.systemDefault());
        ZonedDateTime endUTC = endLocal.withZoneSameInstant(ZoneOffset.UTC);
        LocalDateTime End = endUTC.toLocalDateTime();
        LocalDateTime updateLocal = LocalDateTime.now();
        ZonedDateTime ld = ZonedDateTime.of(updateLocal, ZoneId.systemDefault());
        ZonedDateTime ldUTC = ld.withZoneSameInstant(ZoneOffset.UTC);
        Timestamp Last_Update = Timestamp.valueOf(ldUTC.toLocalDateTime());
        String Last_Updated_By = updatedAppointment.getLast_Update_By();
        int Customer_ID = updatedAppointment.getCustomer_ID();
        int User_ID = updatedAppointment.getUser_ID();
        int Contact_ID = updatedAppointment.getContact_ID();
        int Appointment_ID = updatedAppointment.getAppointment_ID();

        psUpApp.setString(1, Title);
        psUpApp.setString(2, Description);
        psUpApp.setString(3, Location);
        psUpApp.setString(4, Type);
        psUpApp.setString(5, String.valueOf(Start));
        psUpApp.setString(6, String.valueOf(End));
        psUpApp.setString(7, String.valueOf(Last_Update));
        psUpApp.setString(8, Last_Updated_By);
        psUpApp.setString(9, String.valueOf(Customer_ID));
        psUpApp.setString(10, String.valueOf(User_ID));
        psUpApp.setString(11, String.valueOf(Contact_ID));
        psUpApp.setString(12, String.valueOf(Appointment_ID));
        psUpApp.execute();


    }

    /**
     * This method checks to see if there are appointments within 15 minutes of when the user logs into the application.
     */
    public static void upcommingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        ObservableList<Appointment> allAppointments = AppointmentDaoImpl.getAllAppointments();
        boolean upcommingAppointment = false;

        for (Appointment search : allAppointments) {
            LocalDateTime appStart = search.getStart();
            if (appStart.isAfter(now) && appStart.isBefore(now.plusMinutes(15))) {
                if (LoginController.getLang().contentEquals("English")) {
                    upcommingAppointment = true;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Appointment: " + search.getAppointment_ID() + "\nStarts at: " + search.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")));
                    alert.showAndWait();
                    return;
                } else if (LoginController.getLang().contentEquals("français")) {
                    upcommingAppointment = true;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Le rendez-vous " + search.getAppointment_ID() + " commence à " + search.getStart().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                    alert.showAndWait();
                    return;
                }
            }

        }
        if (upcommingAppointment != true) {
            if (LoginController.getLang().contentEquals("English")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("No upcoming appointments");
                alert.showAndWait();
                return;
            } else if (LoginController.getLang().contentEquals("français")) {
                upcommingAppointment = true;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Aucun rendez-vous à venir");
                alert.showAndWait();
                return;
            }

        }
    }
}
