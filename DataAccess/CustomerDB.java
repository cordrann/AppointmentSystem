package DataAccess;

import Database.JDBC;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDB {
    public static void insertCustomer(String name, String address, String postal, String phone, String state) {

        int divisionID;

        try {
            String divIDQuery = "SELECT Division_ID FROM First_Level_Divisions WHERE Division = \"" + state + "\"";
            PreparedStatement dIDQ = JDBC.getConnection().prepareStatement(divIDQuery);

            ResultSet results = dIDQ.executeQuery();
            divisionID = results.getInt("Division_ID");

            String insert = "INSERT INTO Customers VALUES(NULL, ?,? ?,?, NULL, NULL, NULL, NULL, ?)";

            PreparedStatement ips = JDBC.getConnection().prepareStatement(insert);

            ips.setString(1,name);
            ips.setString(2,address);
            ips.setString(3, postal);
            ips.setString(4, phone);
            ips.setInt(9, divisionID);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

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

}
