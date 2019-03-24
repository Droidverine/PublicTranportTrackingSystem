package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Models;

public class Tickets {
    String date,time,price,destination,quantity,source,email,uid;

    public Tickets() {
    }

    public Tickets(String date, String time, String price, String destination, String quantity, String source, String email, String uid) {
        this.date = date;
        this.time = time;
        this.price = price;
        this.destination = destination;
        this.quantity = quantity;
        this.source = source;
        this.email = email;
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
