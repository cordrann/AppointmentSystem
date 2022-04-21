/**
 * @author Andrew Stowe
 */
package DataAccess;

import Database.JDBC;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class AppointmentDB {

    /**
     * Inserts an appointment into the database
     * @param newTitle Appointment Title
     * @param description Appointment Description
     * @param location Appointment Location
     * @param contactID Appointment contact ID
     * @param type Appointment type
     * @param start Appointment start date/time
     * @param end Appointment end date/time
     * @param customerID Appointment customer ID
     * @param userID Appointment User ID
     */
    public static void insertAppointment(String newTitle, String description, String location, Integer contactID,
                                         String type, Timestamp start, Timestamp end, Integer customerID, Integer userID) {

        try {
            String insert = "INSERT INTO Appointments (Appointment_ID, Title, Description, Location, Type," +
                    " Start, End, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            System.out.println(insert);
            PreparedStatement ips = JDBC.getConnection().prepareStatement(insert);

            ips.setString(1,newTitle);
            ips.setString(2,description);
            ips.setString(3, location);
            ips.setString(4, type);
            ips.setTimestamp(5, start);
            ips.setTimestamp(6, end);
            ips.setInt(7, customerID);
            ips.setInt(8, userID);
            ips.setInt(9, contactID);

            ips.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * gets all the appointments from the database
     * @return returns a list of all appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try{
            String appointmentQuery = "Select * FROM Appointments";
            PreparedStatement aQ = JDBC.getConnection().prepareStatement(appointmentQuery);
            ResultSet results = aQ.executeQuery();
            while (results.next()){
                Integer aid = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                Timestamp start = results.getTimestamp("Start");
                Timestamp end = results.getTimestamp("End");
                Integer cuid = results.getInt("Customer_ID");
                Integer uid = results.getInt("User_ID");
                Integer coid = results.getInt("Contact_ID");
                Appointment thisAppointment = new Appointment (aid, title, description, location, type,
                        start, end, cuid, uid, coid);
                appointments.add(thisAppointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    /**
     * get all appointments for a specific customer
     * @param customerID the id of the customer whose appointments you are looking for
     * @return this list of appointments for the customer
     */
    public static ObservableList<Appointment> getCustomerAppointments(Integer customerID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try{
            String appointmentQuery = "Select * FROM Appointments WHERE Customer_ID = ?";
            PreparedStatement aQ = JDBC.getConnection().prepareStatement(appointmentQuery);
            aQ.setInt(1,customerID);
            ResultSet results = aQ.executeQuery();
            while (results.next()){
                Integer aid = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                Timestamp start = results.getTimestamp("Start");
                Timestamp end = results.getTimestamp("End");
                Integer cuid = results.getInt("Customer_ID");
                Integer uid = results.getInt("User_ID");
                Integer coid = results.getInt("Contact_ID");
                Appointment thisAppointment = new Appointment (aid, title, description, location, type,
                        start, end, cuid, uid, coid);
                appointments.add(thisAppointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;

    }

    /**
     * deletes an appointment from the database
     * @param aid the id of the appointment to delete
     */
    public static void deleteAppointment(Integer aid) {
        try{
            String deleteA = "DELETE FROM Appointments WHERE Appointment_ID = ?";
            PreparedStatement daps = JDBC.getConnection().prepareStatement(deleteA);
            daps.setInt(1,aid);
            daps.execute();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * get a list of all a customers appointments besides the one which is being updated
     * @param customerID The id of the customer who has the appointments
     * @param appointmentID The id of the appointment being updated
     * @return List of all the customers other appointments
     */
    public static ObservableList<Appointment> getCustomerOtherAppointments(Integer customerID, Integer appointmentID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try{
            String appointmentQuery = "Select * FROM Appointments WHERE (Customer_ID = ? AND Appointment_ID != ?)";
            PreparedStatement aQ = JDBC.getConnection().prepareStatement(appointmentQuery);
            aQ.setInt(1, customerID);
            aQ.setInt(2, appointmentID);
            ResultSet results = aQ.executeQuery();
            while (results.next()){
                Integer aid = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                Timestamp start = results.getTimestamp("Start");
                Timestamp end = results.getTimestamp("End");
                Integer cuid = results.getInt("Customer_ID");
                Integer uid = results.getInt("User_ID");
                Integer coid = results.getInt("Contact_ID");
                Appointment thisAppointment = new Appointment (aid, title, description, location, type,
                        start, end, cuid, uid, coid);
                appointments.add(thisAppointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    /**
     * Updates an appointment in the DB based on input
     * @param aid id of appointment being updated
     * @param newTitle title of appointment
     * @param description description of appointment
     * @param location location of appointment
     * @param contactID contact id of appointment
     * @param type type of appointment
     * @param startStamp start date/time of appointment
     * @param endStamp end date/time of appointment
     * @param customerID ID of the customer who has the appointment
     * @param userID ID of the user who has the appointment
     */
    public static void updateAppointment (Integer aid, String newTitle, String description, String location,
                                         Integer contactID, String type, Timestamp startStamp, Timestamp endStamp,
                                         Integer customerID, Integer userID) {
        try {

            String update = ("UPDATE Appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, " +
                    "Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?");

            PreparedStatement ups = JDBC.getConnection().prepareStatement(update);

            ups.setString(1, newTitle);
            ups.setString(2, description);
            ups.setString(3, location);
            ups.setInt(4, contactID);
            ups.setString(5, type);
            ups.setTimestamp(6, startStamp);
            ups.setTimestamp(7, endStamp);
            ups.setInt(8, customerID);
            ups.setInt(9, userID);
            ups.setInt(10, aid);


            ups.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    /**
     * checks the database for appointments within 15 minutes for a specific user
     * @param thisUserID the user who owns the appointments
     * @return list of all appointments within 15 minutes
     */

    public static ObservableList<Appointment> userAppsWithin15(Integer thisUserID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try{
            String appointmentQuery = "Select * FROM Appointments WHERE Start > ? AND Start < ? AND User_ID = ?";
            PreparedStatement aQ = JDBC.getConnection().prepareStatement(appointmentQuery);
            aQ.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            aQ.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));
            aQ.setInt(3, thisUserID);
            ResultSet results = aQ.executeQuery();
            while (results.next()){
                Integer aid = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                Timestamp start = results.getTimestamp("Start");
                Timestamp end = results.getTimestamp("End");
                Integer cuid = results.getInt("Customer_ID");
                Integer uid = results.getInt("User_ID");
                Integer coid = results.getInt("Contact_ID");
                Appointment thisAppointment = new Appointment (aid, title, description, location, type,
                        start, end, cuid, uid, coid);
                appointments.add(thisAppointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }


    /**
     * gets a list of appointments within 7 days or 30 days of a date based on week or month selection
     * @param value start date for the week or month time frame
     * @param selected true if week is selected, setting the range to 7 days
     * @return list of all appointments in the given window
     */
    public static ObservableList<Appointment> getTimeAppointments(LocalDate value, boolean selected) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Integer days;

        if(selected){days = 7;}
        else{days = 30;}

        Timestamp valueTs = Timestamp.valueOf(value.atTime(0,0));

        Timestamp range = Timestamp.valueOf(value.atTime(0,0).plusDays(days));

        try{
            String appointmentQuery = "Select * FROM Appointments WHERE Start > ? AND Start < ? ORDER BY Start";
            PreparedStatement aQ = JDBC.getConnection().prepareStatement(appointmentQuery);
            aQ.setTimestamp(1, valueTs);
            aQ.setTimestamp(2, range);
            ResultSet results = aQ.executeQuery();
            while (results.next()){
                Integer aid = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                Timestamp start = results.getTimestamp("Start");
                Timestamp end = results.getTimestamp("End");
                Integer cuid = results.getInt("Customer_ID");
                Integer uid = results.getInt("User_ID");
                Integer coid = results.getInt("Contact_ID");
                Appointment thisAppointment = new Appointment (aid, title, description, location, type,
                        start, end, cuid, uid, coid);
                appointments.add(thisAppointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;


    }

    /**
     * Counts the number of appointments with a given type and month
     * @param type the type of appointment
     * @param month the number index of the month selected
     * @return the number of appointments meeting the criteria
     */
    public static Integer countAppointments(String type, Integer month) {
        Integer appointments = 0;

        try{
            String appointmentQuery = "Select Count(*) FROM Appointments WHERE Type = ? AND Month(Start) = ? ";
            PreparedStatement aQ = JDBC.getConnection().prepareStatement(appointmentQuery);
            aQ.setString(1, type);
            aQ.setInt(2, month);
            ResultSet results = aQ.executeQuery();
            results.next();

            appointments = results.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;

    }
}
