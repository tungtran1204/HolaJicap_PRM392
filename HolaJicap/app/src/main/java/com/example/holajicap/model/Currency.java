package com.example.holajicap.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Currency {
    @PrimaryKey(autoGenerate = true)
    public int currency_id;

    @ColumnInfo(name = "currency_name")
    private String currency_name;
    @ColumnInfo(name = "currency_code")
    private String currency_code;
    @ColumnInfo(name = "currency_imageResId")
    private String currency_imageResId;
    @ColumnInfo(name = "currency_isSelected")
    private boolean currency_isSelected;

    public Currency(int currency_id, String currency_name, String currency_code, String currency_imageResId, boolean currency_isSelected) {
        this.currency_id = currency_id;
        this.currency_name = currency_name;
        this.currency_code = currency_code;
        this.currency_imageResId = currency_imageResId;
        this.currency_isSelected = currency_isSelected;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCurrency_imageResId() {
        return currency_imageResId;
    }

    public void setCurrency_imageResId(String currency_imageResId) {
        this.currency_imageResId = currency_imageResId;
    }

    public boolean isCurrency_isSelected() {
        return currency_isSelected;
    }

    public void setCurrency_isSelected(boolean currency_isSelected) {
        this.currency_isSelected = currency_isSelected;
    }
}
