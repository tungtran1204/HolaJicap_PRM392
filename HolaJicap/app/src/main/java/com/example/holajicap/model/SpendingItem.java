package com.example.holajicap.model;

public class SpendingItem {
    private final int iconResId;
    private final String name;
    private final int percentage;

    public SpendingItem(int iconResId, String name, int percentage) {
        this.iconResId = iconResId;
        this.name = name;
        this.percentage = percentage;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getName() {
        return name;
    }

    public int getPercentage() {
        return percentage;
    }
}
