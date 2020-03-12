package com.example.ocr.Beam;

public class MoneywaterBean {
    private String user;
    private String money;

    public MoneywaterBean(String user, String money) {
        this.user = user;
        this.money = money;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
