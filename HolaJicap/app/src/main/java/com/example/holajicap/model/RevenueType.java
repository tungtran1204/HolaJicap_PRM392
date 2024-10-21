package com.example.holajicap.model;

public class RevenueType {
    private int ava; // avatar
    private String title;

    public RevenueType() {
    }

    public RevenueType(int ava, String title) {
        this.ava = ava;
        this.title = title;
    }

    public int getAva() {
        return ava;
    }

    public void setAva(int ava) {
        this.ava = ava;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
