package com.example.myapplication;

import android.location.Location;

import java.util.List;

public class TSO {
    private String latitude;
    private String longitude;
    private String fullAddress;
    private String placeUa;
    private List<String> tw;

    public TSO(String latitude, String longitude, String fullAddress, String placeUa, List<String> tw) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.fullAddress = fullAddress;
        this.placeUa = placeUa;
        this.tw = tw;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getCityRU() {
        return placeUa;
    }

    public void setCityRU(String cityRU) {
        this.placeUa = cityRU;
    }

    public List<String> getTw() {
        return tw;
    }

    public void setTw(List<String> tw) {
        this.tw = tw;
    }
}
