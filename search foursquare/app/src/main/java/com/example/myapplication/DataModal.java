package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class DataModal {
    @SerializedName("meta")
    String meta;

    public String getHeaderLocation() {
        return meta;
    }

    public void setHeaderLocation(String meta) {
        this.meta = meta;
    }
}