package com.example.holajicap.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.holajicap.model.Currency;

import java.util.List;

@Dao
public interface CurrencyDao {
    @Query(value = "SELECT * FROM Currency")
    List<Currency> getAllCurrencies();


    @Query("SELECT * FROM Currency WHERE currency_id = :id")
    Currency getCurrencyById(int id);





}
