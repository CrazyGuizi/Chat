package com.example.crazygz.chat.common.db.bean;

public class Message {

    String name;
    String message;
    int type;

    public Message() {}

    public Message(int type,String name, String message) {
        this.name = name;
        this.message = message;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}
