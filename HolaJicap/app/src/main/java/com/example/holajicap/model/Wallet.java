package com.example.holajicap.model;

import androidx.room.ColumnInfo;
import androidx.room.DeleteTable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Wallet {
    @PrimaryKey(autoGenerate = true)
    private int walletId;
    @ColumnInfo(name = "walletName")
    private String walletName;
    @ColumnInfo(name = "balance")
    private int balance;
    @ColumnInfo(name = "currency")
    private String currency;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "createdDate")
    private String createdDate;

    public Wallet() {
    }

    public Wallet(int walletId, String walletName, int balance, String currency, String description, String createdDate) {
        this.walletId = walletId;
        this.walletName = walletName;
        this.balance = balance;
        this.currency = currency;
        this.description = description;
        this.createdDate = createdDate;
    }
}
