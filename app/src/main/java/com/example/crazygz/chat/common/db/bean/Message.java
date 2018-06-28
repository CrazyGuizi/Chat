package com.example.crazygz.chat.common.db.bean;

public class Message {

    int type;
    String name;
    String username;
    String message;

    public Message() {}

    public Message(int type, String name, String username, String message) {
        this.type = type;
        this.name = name;
        this.username = username;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}
