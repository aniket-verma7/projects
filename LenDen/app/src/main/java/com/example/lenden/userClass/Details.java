package com.example.lenden.userClass;




public class Details
{
    private int id;
    private String name;
    private  String amount,type;
    private String mob;
    private String localDate;


    public Details(int id, String name, String amount, String type, String mob, String localDate) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.mob = mob;
        this.localDate = localDate;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public Details() {}

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
