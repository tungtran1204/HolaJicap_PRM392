package com.example.holajicap.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.holajicap.SpendingData;
import com.example.holajicap.model.Category;
import com.example.holajicap.model.Transaction;
import com.example.holajicap.model.TransactionWithCategory;

import java.util.Date;
import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM 'Transaction'")
    List<Transaction> getAll();

    @Query("SELECT * FROM 'Transaction' WHERE walletId = :walletId")
    List<Transaction> getTransactionsByWalletId(int walletId);

    @Query("SELECT * FROM 'Transaction' WHERE cateId = :cateId")
    List<Transaction> getTransactionsByCateId(int cateId);

    @Query("SELECT * FROM 'Transaction' WHERE transId = :transId")
    Transaction getTransactionById(int transId);

    @Query("SELECT * FROM 'Transaction' WHERE userId = :userId")
    List<Transaction> getTransactionsByUserId(int userId);

    @Insert
    void insert(Transaction transaction);

    @Query("DELETE FROM 'Transaction' WHERE transId = :transId")
    void delete(int transId);

    @Query("UPDATE 'Transaction' SET amount = :amount, note = :note, date = :date, cateId = :cateId WHERE transId = :transId")
    void update(int transId, double amount, String note, String date, int cateId);

    @Query("SELECT * FROM `Transaction` WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    List<Transaction> getTransactionsInRange(String startDate, String endDate);

    @Query("SELECT * FROM `Transaction` WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    List<Transaction> getTransactionsInRangeByUserId(int userId, String startDate, String endDate);

    @androidx.room.Transaction
    @Query("SELECT * FROM `Transaction` WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    List<TransactionWithCategory> getTransactionsWithCategories(int userId, String startDate, String endDate);
    @Query("SELECT c.cateIcon, c.cateName, SUM(t.amount) AS totalAmount, "
            + "ROUND((SUM(t.amount) * 100.0 / (SELECT SUM(amount) FROM `Transaction` WHERE userId = :userId)), 2) AS percentage "
            + "FROM `Transaction` t "
            + "JOIN Category c ON t.cateId = c.cateId "
            + "WHERE t.userId = :userId "
            + "GROUP BY c.cateId "
            + "ORDER BY totalAmount DESC "
            + "LIMIT 3")
    List<SpendingData> getTopCategoriesWithPercentage(int userId);

}
