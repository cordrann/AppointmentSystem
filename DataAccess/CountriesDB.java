/**
 * @author Andrew Stowe
 */
package DataAccess;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDB {
    /**
     * gets all the countries in the database
     * @return a list of all the countries
     */
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
