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
