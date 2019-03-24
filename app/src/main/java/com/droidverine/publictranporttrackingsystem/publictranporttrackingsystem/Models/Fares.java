package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Models;

public class Fares {
    String source,destination,price;

    public Fares(String source, String destination, String price) {
        this.source = source;
        this.destination = destination;
        this.price = price;
    }

    public Fares() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
