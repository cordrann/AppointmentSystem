package Model;

import java.sql.Timestamp;
import java.time.chrono.ChronoZonedDateTime;

public class Customer {
    /**
     * ID of this customer
     */
    private Integer customerID;
    /**
     * Name of this customer
     */
    private String customerName;
    /**
     * Address of this customer
     */
    private String address;
    /**
     * Postal code of this customer
     */
    private String postalCode;
    /**
     * phone number of this customer
     */
    private String phone;
    /**
     * state/province of this customer
     */
    private String division;
    /**
     * country of this customer
     */
    private String country;

    /**
     * creates a new customer object based on information input
     * @param customerID ID of the customer
     * @param customerName name of the customer
     * @param address street address of the customer
     * @param postalCode postal code of the customer
     * @param phone phone number of customer
     * @param division state/province of the customer
     * @param country country of the customer
     */
    public Customer(Integer customerID, String customerName, String address, String postalCode, String phone, String division, String country) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    /**
     * get the customer's state/province
     * @return the customer's state/province
     */
    public String getDivision() {
        return division;
    }

    /**
     * get the customer's country
     * @return the customer's country
     */
    public String getCountry() {
        return country;
    }

    /**
     * get the customer's ID
     * @return the customer's ID
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     * get the customer's name
     * @return name of customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * get the customer's street address
     * @return street address of customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * get the customer's phone number
     * @return phone number of customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * change the toString method of the customer object to display different information
     * @return the updated string
     */
    @Override public String toString(){
        return(customerName);
    }
}
