/**
 * @author Andrew Stowe
 */
package DataAccess;

import Database.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDB {
    /**
     * Checks the database for the username and password in the same row
     * @param userName the username entered by the user
     * @param password the password entered by the user
     * @return true if login is accepted, false if it is not
     */
    public static boolean loginCredentialsValid(String userName, String password) {


        try {
            String query = ("SELECT User_Name, Password FROM users WHERE User_Name =\"" + userName + "\" AND Password = \"" + password + "\"");

            PreparedStatement login = JDBC.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet results = login.executeQuery();
            results.last();
            int i = results.getRow();
            if (i!=0){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * gets the user id from the database
     * @param uName the username of the user
     * @param pword the password of the user
     * @return the user id
     */
    public static Integer getUserID(String uName, String pword) {
        try {
            String query = ("SELECT User_ID FROM users WHERE User_Name =\"" + uName + "\" AND Password = \"" + pword + "\"");

            PreparedStatement login = JDBC.getConnection().prepareStatement(query);
            ResultSet results = login.executeQuery();
            results.next();

            return results.getInt("User_ID");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
