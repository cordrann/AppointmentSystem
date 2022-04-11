package DataAccess;

import Database.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDB {

    public static boolean loginCredentials(String userName, String password) {


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
}
