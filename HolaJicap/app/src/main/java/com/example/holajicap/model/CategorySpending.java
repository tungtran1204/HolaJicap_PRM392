package com.example.holajicap.model;

public class CategorySpending {
    private String cateIcon;
    private String cateName;
    private double totalAmount;

    public CategorySpending(String cateIcon, String cateName, double totalAmount) {
        this.cateIcon = cateIcon;
        this.cateName = cateName;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getCateIcon() {
        return cateIcon;
    }

    public void setCateIcon(String cateIcon) {
        this.cateIcon = cateIcon;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
