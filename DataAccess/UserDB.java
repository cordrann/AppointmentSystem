/**
 * @author Andrew Stowe
 */
package DataAccess;

import Database.JDBC;
import Model.Contact;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    /**
     * get a list of all users from the database
     * @return the list of all users
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();

        try{
            String userQuery = "Select * FROM Users";
            PreparedStatement uQ = JDBC.getConnection().prepareStatement(userQuery);
            ResultSet results = uQ.executeQuery();
            while (results.next()){
                Integer uid = results.getInt("User_ID");
                String name = results.getString("User_Name");
                String password = results.getString("Password");

                User thisUser = new User (uid, name, password);
                users.add(thisUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * get a specific user from the database
     * @param userID the id of the user
     * @return the user
     */

    public static User getThisUser(Integer userID) {
        try{
            String userQuery = "Select * FROM Users WHERE User_ID = ?";
            PreparedStatement uQ = JDBC.getConnection().prepareStatement(userQuery);
            uQ.setInt(1, userID);

            ResultSet results = uQ.executeQuery();
            results.next();
            Integer uid = results.getInt("User_ID");
            String name = results.getString("User_Name");
            String password = results.getString("Password");

            User thisUser = new User (uid, name, password);

            return thisUser;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
