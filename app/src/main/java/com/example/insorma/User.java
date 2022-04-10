package com.example.insorma;

import java.util.ArrayList;

public class User {

    private int UserID;
    private String UserEmailAddress, UserUsername, UserPhoneNumber, UserPassword;

    public User() {
    }

    public User(int UserID, String UserEmailAddress, String UserUsername, String UserPhoneNumber, String UserPassword) {
        this.UserID = UserID;
        this.UserEmailAddress = UserEmailAddress;
        this.UserUsername = UserUsername;
        this.UserPhoneNumber = UserPhoneNumber;
        this.UserPassword = UserPassword;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getUserID() {
        return this.UserID;
    }

    public void setUserEmailAddress(String UserEmailAddress) {
        this.UserEmailAddress = UserEmailAddress;
    }

    public String getUserEmailAddress() {
        return this.UserEmailAddress;
    }

    public void setUserUsername(String UserUsername) {
        this.UserUsername = UserUsername;
    }

    public String getUserUsername() {
        return this.UserUsername;
    }

    public void setUserPhoneNumber(String UserPhoneNumber) {
        this.UserPhoneNumber = UserPhoneNumber;
    }

    public String getUserPhoneNumber() {
        return this.UserPhoneNumber;
    }

    public void setUserPassword(String UserPassword) {
        this.UserPassword = UserPassword;
    }

    public String getUserPassword() {
        return this.UserPassword;
    }

}
