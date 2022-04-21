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

public class FirstLevelDivisionsDB {

    /**
     * get a list of states filtered by country
     * @param country the country to filter by
     * @return the list of filtered states
     */

    public static ObservableList<String> getFilteredDivisions(String country){
        ObservableList<String> divisions = FXCollections.observableArrayList();

        try{
            String queryCountry = ("SELECT Country_ID FROM countries WHERE Country = ? ");
            PreparedStatement cQ = JDBC.getConnection().prepareStatement(queryCountry);
            cQ.setString(1, country);
            ResultSet resultsCountry = cQ.executeQuery();
            resultsCountry.next();
            int cID = resultsCountry.getInt("Country_ID");

            String queryDivision = ("SELECT Division FROM First_Level_Divisions WHERE Country_ID = ?");
            PreparedStatement dQ = JDBC.getConnection().prepareStatement(queryDivision);
            dQ.setInt(1, cID);
            ResultSet resultsDivision = dQ.executeQuery();

            while(resultsDivision.next()){
                String division = resultsDivision.getString("Division");
                divisions.add(division);

            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return divisions;
    }

}
