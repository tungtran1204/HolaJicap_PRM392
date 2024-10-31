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

    public Wallet() {
    }

    public Wallet(int walletId, String walletName, int balance) {
        this.walletId = walletId;
        this.walletName = walletName;
        this.balance = balance;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", walletName='" + walletName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
