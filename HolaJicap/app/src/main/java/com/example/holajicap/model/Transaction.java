package com.example.holajicap.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Transaction",
        foreignKeys = {
                @ForeignKey(
                        entity = Category.class,
                        parentColumns = "cateId",
                        childColumns = "cateId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Wallet.class,
                        parentColumns = "walletId",
                        childColumns = "walletId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int transId;
    @ColumnInfo(name = "walletId")
    private int walletId;
    @ColumnInfo(name = "amount")
    private double amount;
    @ColumnInfo(name = "note")
    private String note;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "cateId")
    private int cateId;

    public Transaction() {
    }

    public Transaction(int transId, int walletId, double amount, String note, Date date, int cateId) {
        this.transId = transId;
        this.walletId = walletId;
        this.amount = amount;
        this.note = note;
        this.date = date;
        this.cateId = cateId;
    }

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transId=" + transId +
                ", walletId=" + walletId +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", date=" + date +
                ", cateId=" + cateId +
                '}';
    }
}
