package DataAccess;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDB {
    public static String getCountry(int countryID){
        String country = new String();

        try{
            String countryQuery = ("SELECT Country FROM countries WHERE Country_ID = " + countryID);

            PreparedStatement cQ = JDBC.getConnection().prepareStatement(countryQuery);
            ResultSet results = cQ.executeQuery();
            country = results.getString("Country");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return country;
    }

    public static ObservableList<String> getAllCountries(){
        ObservableList<String> countries = FXCollections.observableArrayList();

        try {
            String query = ("SELECT Country From countries");

            PreparedStatement q = JDBC.getConnection().prepareStatement(query);
            ResultSet results = q.executeQuery();

            while(results.next()){
                String country = results.getString("Country");
                countries.add(country);
            }


        } catch(SQLException e){
            e.printStackTrace();
        }

        return countries;
    }
}
