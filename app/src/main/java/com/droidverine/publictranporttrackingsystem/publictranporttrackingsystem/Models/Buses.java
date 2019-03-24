package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Models;

public class Buses {
    String busnumber,email,lat,lon,username,bustitle;

    public Buses() {
    }

    public Buses(String busnumber, String email, String lat, String lon, String username, String bustitle) {
        this.busnumber = busnumber;
        this.email = email;
        this.lat = lat;
        this.lon = lon;
        this.username = username;
        this.bustitle = bustitle;
    }

    public String getBusnumber() {
        return busnumber;
    }

    public void setBusnumber(String busnumber) {
        this.busnumber = busnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBustitle() {
        return bustitle;
    }

    public void setBustitle(String bustitle) {
        this.bustitle = bustitle;
    }
}
