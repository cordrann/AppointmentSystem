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
import java.time.ZonedDateTime;

public class AppointmentDB {

    public static void insertAppointment(String newTitle, String description, String location, int contactID,
                                         String type, Timestamp start, Timestamp end, Integer customerID, int userID) {

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
}
