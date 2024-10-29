package com.example.holajicap.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.holajicap.model.Transaction;

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
    @Insert
    void insert(Transaction transaction);
    @Query("DELETE FROM 'Transaction' WHERE transId = :transId")
    void delete(int transId);
    @Query("UPDATE 'Transaction' SET amount = :amount, note = :note, date = :date, cateId = :cateId WHERE transId = :transId")
    void update(int transId, int amount, String note, String date, int cateId);


}