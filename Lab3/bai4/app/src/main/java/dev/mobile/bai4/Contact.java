package dev.mobile.bai4;

import java.io.Serializable;

public class Contact implements Serializable {
    private String userName;
    private String job;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String homepage;

    public Contact(String userName, String job, String name, String phone, String mail, String address,
                   String homepage) {
        this.userName = userName;
        this.job = job;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.homepage = homepage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getJob() {
        return this.job;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getMail() {
        return this.mail;
    }

    public String getAddress() {
        return this.address;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

}

