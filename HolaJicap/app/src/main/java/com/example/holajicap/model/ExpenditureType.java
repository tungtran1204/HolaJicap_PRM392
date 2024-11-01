package com.example.holajicap.model;

public class ExpenditureType {
    private int ava; // avatar
    private String title;

    public ExpenditureType() {
    }

    public ExpenditureType(int ava, String title) {
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
