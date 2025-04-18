package dev.mobile.bai1;

public class Contact {
    private String username;
    private String contactNumber;

    public Contact(String username, String contactNumber) {
        this.username = username;
        this.contactNumber = contactNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}