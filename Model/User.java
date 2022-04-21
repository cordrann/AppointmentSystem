package Model;

public class User {
    private Integer userID;
    private String userName;
    private String password;

    /**
     * create a new user object based on input
     * @param userID id of the user
     * @param userName name of the user
     * @param password password of the user
     */
    public User(Integer userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * get the user's id
     * @return id of the user
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * change the toString method for user to display just the username
     * @return the modified string
     */
    @Override public String toString(){
        return(userName);
    }
}
