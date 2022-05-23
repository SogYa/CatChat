package com.example.realtimechat.data.model;

public class Message {
    private String uid;
    private String messageText;
    private String time;
    private String name;

    public Message() {
    }

    public Message(String text, String time, String name, String uid) {
        this.messageText = text;
        this.time = time;
        this.name = name;
        this.uid = uid;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }
}
