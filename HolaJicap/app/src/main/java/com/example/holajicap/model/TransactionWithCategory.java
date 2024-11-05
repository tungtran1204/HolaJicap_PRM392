package com.example.holajicap.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TransactionWithCategory {
    @Embedded
    private Transaction transaction;

    @Relation(
            parentColumn = "cateId",
            entityColumn = "cateId"
    )
    private Category category;

    // Constructor, Getter và Setter cho transaction và category

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
