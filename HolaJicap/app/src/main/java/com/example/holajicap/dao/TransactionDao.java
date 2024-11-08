package com.example.holajicap.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.holajicap.model.CategorySpending;
import com.example.holajicap.model.Transaction;
import com.example.holajicap.model.TransactionWithCategory;

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

    @Query("DELETE FROM 'Transaction' WHERE transId = :transId")
    void deleteById(int transId);

    @Query("SELECT c.cateIcon AS cateIcon, c.cateName AS cateName, SUM(t.amount) AS totalAmount "
            + "FROM `Transaction` t "
            + "JOIN Category c ON t.cateId = c.cateId "
            + "WHERE t.userId = :userId AND c.cateType = 'Expenditure' "
            + "GROUP BY c.cateId, c.cateIcon, c.cateName "
            + "ORDER BY totalAmount DESC "
            + "LIMIT 5")
    List<CategorySpending> getTotalAmountPerCategory(int userId);

    // Tổng doanh thu tháng này
    @Query("SELECT SUM(t.amount) FROM 'Transaction' t " +
            "JOIN Category c ON t.cateId = c.cateId " +
            "WHERE c.cateType = 'Revenue' " +
            "AND strftime('%Y-%m', date(substr(t.date, 1, 4) || '-' || substr(t.date, 6, 2) || '-' || substr(t.date, 9, 2))) = strftime('%Y-%m', 'now') " +
            "AND t.userId = :userId")
    LiveData<Double> getTotalRevenueCurrentMonth(int userId);

    // Tổng chi tiêu tháng này
    @Query("SELECT SUM(t.amount) FROM 'Transaction' t " +
            "JOIN Category c ON t.cateId = c.cateId " +
            "WHERE c.cateType = 'Expenditure' " +
            "AND strftime('%Y-%m', date(substr(t.date, 1, 4) || '-' || substr(t.date, 6, 2) || '-' || substr(t.date, 9, 2))) = strftime('%Y-%m', 'now') " +
            "AND t.userId = :userId")
    LiveData<Double> getTotalExpenditureCurrentMonth(int userId);

    // Tổng doanh thu tháng trước
    @Query("SELECT SUM(t.amount) FROM 'Transaction' t " +
            "JOIN Category c ON t.cateId = c.cateId " +
            "WHERE c.cateType = 'Revenue' " +
            "AND strftime('%Y-%m', date(substr(t.date, 1, 4) || '-' || substr(t.date, 6, 2) || '-' || substr(t.date, 9, 2))) = strftime('%Y-%m', 'now', '-1 month') " +
            "AND t.userId = :userId")
    LiveData<Double> getTotalRevenueLastMonth(int userId);

    // Tổng chi tiêu tháng trước
    @Query("SELECT SUM(t.amount) FROM 'Transaction' t " +
            "JOIN Category c ON t.cateId = c.cateId " +
            "WHERE c.cateType = 'Expenditure' " +
            "AND strftime('%Y-%m', date(substr(t.date, 1, 4) || '-' || substr(t.date, 6, 2) || '-' || substr(t.date, 9, 2))) = strftime('%Y-%m', 'now', '-1 month') " +
            "AND t.userId = :userId")
    LiveData<Double> getTotalExpenditureLastMonth(int userId);
}
