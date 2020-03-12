package com.example.ocr.Beam;

public class UserTable {
    private String user;
    private String name;
    private String passed;

    public UserTable(String user, String name, String passed) {
        this.user = user;
        this.name = name;
        this.passed = passed;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }
}
