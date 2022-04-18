package Model;

public class Contact {
    private Integer contactID;
    private String contactName;
    private String email;

    public Contact(Integer contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    public Integer getContactID(){
        return contactID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override public String toString(){
        return(contactName+" "+email);
    }
}
