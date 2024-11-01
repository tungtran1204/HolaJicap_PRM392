package com.example.holajicap.model;

import androidx.room.ColumnInfo;
import androidx.room.DeleteTable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Wallet",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "uid",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Wallet {
    @PrimaryKey(autoGenerate = true)
    private int walletId;
    @ColumnInfo(name = "userId")
    private int userId;
    @ColumnInfo(name = "walletName")
    private String walletName;
    @ColumnInfo(name = "balance")
    private double balance;
    @ColumnInfo(name = "currency")
    private String currency;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "createdDate")
    private Date createdDate;

    public Wallet() {
    }

    public Wallet(int walletId, int userId, String walletName, double balance, String currency, String description, Date createdDate) {
        this.walletId = walletId;
        this.userId = userId;
        this.walletName = walletName;
        this.balance = balance;
        this.currency = currency;
        this.description = description;
        this.createdDate = createdDate;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", userId=" + userId +
                ", walletName='" + walletName + '\'' +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
