package com.example.vibe.Models;

public class MessagesModel {
    String uId,message,messageId;
    int feeling = -1;
    Long timeStamp;

//    public MessagesModel(String uId, String message, Long timeStamp) {
//        this.uId = uId;
//        this.message = message;
//        this.timeStamp = timeStamp;
//    }

    public MessagesModel(String uId, String message, String messageId, int feeling, Long timeStamp) {
        this.uId = uId;
        this.message = message;
        this.messageId = messageId;
        this.feeling = feeling;
        this.timeStamp = timeStamp;
    }

    public MessagesModel(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }
    public MessagesModel(){

    }


    ////
    ////////
    public int getFeeling() {
        return feeling;
    }
////////////
    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
