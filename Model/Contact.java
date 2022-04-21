package Model;

public class Contact {
    /**
     * contact id for this contact
     */
    private Integer contactID;
    /**
     * name of this contact
     */
    private String contactName;
    /**
     * email for this contact
     */
    private String email;

    /**
     * Makes a new contact object
     * @param contactID id of contact
     * @param contactName name of contact
     * @param email email of contact
     */
    public Contact(Integer contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * get the id of a contact
     * @return the contacts id
     */
    public Integer getContactID(){
        return contactID;
    }

    /**
     * change the toString method for contact to display better information
     * @return the modified string
     */
    @Override public String toString(){
        return(contactName+" "+email);
    }
}
