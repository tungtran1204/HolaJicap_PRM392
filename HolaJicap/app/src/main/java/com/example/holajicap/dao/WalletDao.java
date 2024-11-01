package com.example.holajicap.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.holajicap.model.Wallet;

import java.util.List;

@Dao
public interface WalletDao {
    @Insert
    void insert(Wallet wallet);

    @Query("SELECT * FROM Wallet")
    List<Wallet> getAllWallets();

    @Query("SELECT * FROM Wallet WHERE walletId = :walletId")
    Wallet getWalletById(int walletId);

    @Query("DELETE FROM Wallet WHERE walletId = :walletId")
    void deleteWalletById(int walletId);

    @Query("SELECT * FROM Wallet WHERE userId = :userId")
    List<Wallet> getWalletsByUserId(int userId);

    @Query("UPDATE Wallet SET balance = balance + :amountDelta WHERE walletId = :walletId")
    void updateWalletAmount(int walletId, double amountDelta);
}
