package com.example.realtimechat.datalayer.model;


public class User {
    public String name;
    public String email;
    public String uid;
    public String photoUrl;
    public String status;

    public User(){}
    public User(String userName, String userEmail, String uid, String photoUrl) {
        this.name = userName;
        this.email = userEmail;
        this.uid = uid;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }
    public String getStatus(){return status; }
    public String getUid() {
        return uid;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
