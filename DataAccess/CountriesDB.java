package DataAccess;

import Database.JDBC;

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
}
