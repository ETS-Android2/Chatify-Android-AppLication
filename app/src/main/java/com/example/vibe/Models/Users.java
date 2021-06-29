package com.example.vibe.Models;

public class Users {
    String profilePic, userName, mail, password, userId, lastMessage, status, phoneNumber;

//    public Users(String profilePic, String userName, String mail, String password, String userId, String lastMessage, String status) {
//        this.profilePic = profilePic;
//        this.userName = userName;
//        this.mail = mail;
//        this.password = password;
//        this.userId = userId;
//        this.lastMessage = lastMessage;
//        this.status = status;
//    }

    // added on 13th june --- phone number
    public Users(String profilePic, String userName, String mail, String password, String userId, String lastMessage, String status, String phoneNumber) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.status = status;
        this.phoneNumber = phoneNumber;
    }

    public Users(){

    }

    // Sign up Constructor
    public Users(String userName, String mail, String password) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }

    // added on 13th june --- phone number
    public Users(String userName, String mail, String password, String phoneNumber) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // added on 13th june --- phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}
