/**
 * @author Andrew Stowe
 */
package DataAccess;

import Database.JDBC;
import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ContactDB {
    /**
     * gets all the contacts from the database
     * @return a list of all the contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        try{
            String contactQuery = "Select * FROM Contacts";
            PreparedStatement cQ = JDBC.getConnection().prepareStatement(contactQuery);
            ResultSet results = cQ.executeQuery();
            while (results.next()){
                Integer cid = results.getInt("Contact_ID");
                String name = results.getString("Contact_Name");
                String email = results.getString("Email");

                Contact thisContact = new Contact (cid, name, email);
                contacts.add(thisContact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    /**
     * gets a specific contact from the database
     * @param contactID id of the contact to get
     * @return the contact that is retrieved
     */
    public static Contact getThisContact(Integer contactID) {
        try{
            String contactQuery = "Select * FROM Contacts WHERE Contact_ID = ? ";
            PreparedStatement cQ = JDBC.getConnection().prepareStatement(contactQuery);
            cQ.setInt(1,contactID );
            ResultSet results = cQ.executeQuery();
            results.next();
            Integer cid = results.getInt("Contact_ID");
            String name = results.getString("Contact_Name");
            String email = results.getString("Email");

            Contact thisContact = new Contact (cid, name, email);

            return thisContact;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }
}
