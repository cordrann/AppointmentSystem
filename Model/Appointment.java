package Model;

import Database.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;



public class Appointment {
    /**
     * ID of this appointment
     */
    private Integer appointmentID;
    /**
     * Title of this appointment
     */
    private String title;
    /**
     * Description of this appointment
     */
    private String description;
    /**
     * Location of this appointment
     */
    private String location;
    /**
     * Type of this appointment
     */
    private String type;
    /**
     * Start date/time of this appointment
     */
    private Timestamp startTime;
    /**
     * End date/time of this appointment
     */
    private Timestamp endTime;
    /**
     * Customer id for this appointment
     */
    private Integer customerID;
    /**
     * User id for this appointment
     */
    private Integer userID;
    /**
     *Contact id for this appointment
     */
    private Integer contactID;

    /**
     * Makes a new appointment with the given input
     * @param appointmentID id of appointment
     * @param title title of appointment
     * @param description description of appointment
     * @param location location of appointment
     * @param type type of appointment
     * @param startTime start date and time of appointment
     * @param endTime end date and time of appointment
     * @param customerID id of customer who has appointment
     * @param userID id of user who owns appointment
     * @param contactID id of contact for appointment
     */
    public Appointment(Integer appointmentID, String title, String description, String location, String type,
                       Timestamp startTime, Timestamp endTime, Integer customerID,
                       Integer userID, Integer contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * get and appointment id from an appointment
     * @return the id of the appointment
     */
    public Integer getAppointmentID() {
        return appointmentID;
    }

    /**
     * get the title of the appointment
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * get the description of the appointment
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * get the location of the appointment
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * get the type of appointment
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * set the type of appointment
     * @param type the type to set to
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * get the start date/time of the appointment
     * @return the start date/time
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * get the end date/time of the appointment
     * @return the end date/time
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * get the customer id for the appointment
     * @return id of customer who has appointment
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     * get the user id for the appointment
     * @return id of user who owns appointment
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * get the contact id for the appointment
     * @return the contact id for the appointment
     */
    public Integer getContactID() {
        return contactID;
    }


}
