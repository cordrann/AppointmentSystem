/**
 * @author Andrew Stowe
 */
package DataAccess;

import Database.JDBC;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDB {
    /**
     * inserts a customer into the database
     * @param name name of the customer
     * @param address customers street address
     * @param postal customers postal code
     * @param phone customers phone number
     * @param state customers state or province
     */
    public static void insertCustomer(String name, String address, String postal, String phone, String state) {

        int divisionID;

        try {
            String divIDQuery = "SELECT Division_ID FROM First_Level_Divisions WHERE Division = \"" + state + "\"";
            System.out.println(divIDQuery);
            PreparedStatement dIDQ = JDBC.getConnection().prepareStatement(divIDQuery);

            ResultSet results = dIDQ.executeQuery();
            results.next();
            divisionID = results.getInt("Division_ID");

            String insert = "INSERT INTO Customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                    "VALUES(NULL, ?, ?, ?, ?, ?)";
            System.out.println(insert);
            PreparedStatement ips = JDBC.getConnection().prepareStatement(insert);

            ips.setString(1,name);
            ips.setString(2,address);
            ips.setString(3, postal);
            ips.setString(4, phone);
            ips.setInt(5, divisionID);

            ips.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * get all the customers from the database
     * @return a list of all customers
     */
    public static ObservableList<Customer> getAllCustomers(){

        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try{
            String customerQuery = "SELECT customers.*, First_Level_Divisions.Division, Countries.Country from customers" +
                    " LEFT JOIN  First_Level_Divisions ON customers.Division_ID = First_Level_Divisions.Division_ID " +
                    "LEFT JOIN Countries ON First_Level_Divisions.Country_ID = Countries.Country_ID";
            PreparedStatement cQ = JDBC.getConnection().prepareStatement(customerQuery);
            ResultSet results = cQ.executeQuery();
            while (results.next()){
                int cid = results.getInt("Customer_ID");
                String name = results.getString("Customer_Name");
                String address = results.getString("Address");
                String pcode = results.getString("Postal_Code");
                String phone = results.getString("Phone");
                String division = results.getString("Division");
                String country = results.getString("Country");
                Customer thisCustomer = new Customer (cid, name, address, pcode, phone, division, country);
                customers.add(thisCustomer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * update a customer in the database
     * @param cid id of customer being updated
     * @param name name of customer
     * @param address customer street address
     * @param postal customer postal code
     * @param phone customer phone number
     * @param state customer state/province
     */

    public static void updateCustomer(Integer cid, String name, String address, String postal, String phone, String state) {

        int divisionID;

        try {
            String divIDQuery = "SELECT Division_ID FROM First_Level_Divisions WHERE Division = \"" + state + "\"";
            PreparedStatement dIDQ = JDBC.getConnection().prepareStatement(divIDQuery);

            ResultSet results = dIDQ.executeQuery();
            results.next();
            divisionID = results.getInt("Division_ID");

            String update = "UPDATE Customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? " +
                    "WHERE Customer_ID = ?";

            PreparedStatement ups = JDBC.getConnection().prepareStatement(update);

            ups.setString(1,name);
            ups.setString(2,address);
            ups.setString(3, postal);
            ups.setString(4, phone);
            ups.setInt(5, divisionID);
            ups.setInt(6,cid);

            ups.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * deletes a customer from the database
     * @param cid the id of the customer to be deleted
     */

    public static void deleteCustomer(Integer cid) {
        try{
            String deleteA = "DELETE FROM Appointments WHERE Customer_ID = ?";
            PreparedStatement daps = JDBC.getConnection().prepareStatement(deleteA);
            daps.setInt(1,cid);
            daps.execute();

            String deleteC = "DELETE FROM Customers WHERE Customer_ID = ?";
            PreparedStatement dcps = JDBC.getConnection().prepareStatement(deleteC);
            dcps.setInt(1, cid);
            dcps.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * get a specific customer
     * @param customerID the id of the customer you are looking for
     * @return the customer
     */

    public static Customer getThisCustomer(Integer customerID) {
        try{
            String customerQuery = "SELECT customers.*, First_Level_Divisions.Division, Countries.Country from customers" +
                    " LEFT JOIN  First_Level_Divisions ON customers.Division_ID = First_Level_Divisions.Division_ID " +
                    "LEFT JOIN Countries ON First_Level_Divisions.Country_ID = Countries.Country_ID WHERE Customer_ID = ?";
            PreparedStatement cQ = JDBC.getConnection().prepareStatement(customerQuery);
            cQ.setInt(1, customerID);

            ResultSet results = cQ.executeQuery();
            results.next();
            Integer cid = results.getInt("Customer_ID");
            String name = results.getString("Customer_Name");
            String address = results.getString("Address");
            String pcode = results.getString("Postal_Code");
            String phone = results.getString("Phone");
            String division = results.getString("Division");
            String country = results.getString("Country");
            Customer thisCustomer = new Customer (cid, name, address, pcode, phone, division, country);
            return thisCustomer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
