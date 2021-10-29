package com.example.myapplication;

public class Workers {
    private String name;
    private String name_day;
    private String about;

    public Workers() {
    }

    public Workers(String name, String name_day, String about) {
        this.name = name;
        this.name_day = name_day;
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_day() {
        return name_day;
    }

    public void setName_day(String name_day) {
        this.name_day = name_day;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}