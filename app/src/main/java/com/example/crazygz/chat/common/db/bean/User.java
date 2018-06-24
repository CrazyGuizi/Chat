package com.example.crazygz.chat.common.db.bean;

public class User {

    int id;
    String name;
    String username;
    String password;
    String about;

    public User() {}

    public User(int id, String name, String username, String password, String about) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.about = about;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAbout() {
        return about;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", about='" + about + '\'' +
                '}';
    }
}
