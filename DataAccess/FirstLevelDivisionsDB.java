package DataAccess;

import Database.JDBC;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionsDB {
    public static String getDivision(int divID){
        String division = new String();

        try{
            String divQuery = ("SELECT Division FROM First_Level_Divisions WHERE Division_ID = " + divID);

            PreparedStatement dQ = JDBC.getConnection().prepareStatement(divQuery);
            ResultSet results = dQ.executeQuery();
            division = results.getString("Division");
            } catch (SQLException e) {
            e.printStackTrace();
        }


        return division;
    }

    public static int getCountryID(int divID){
        int countryID = 0;

        try{
            String divQuery = ("SELECT Country_ID FROM First_Level_Divisions WHERE Division_ID = " + divID);

            PreparedStatement dQ = JDBC.getConnection().prepareStatement(divQuery);
            ResultSet results = dQ.executeQuery();
            countryID = results.getInt("Country_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return countryID;
    }

}
