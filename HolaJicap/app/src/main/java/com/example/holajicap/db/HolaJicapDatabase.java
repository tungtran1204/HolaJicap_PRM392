package com.example.holajicap.db;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.holajicap.dao.CategoryDao;
import com.example.holajicap.dao.CurrencyDao;
import com.example.holajicap.dao.TransactionDao;
import com.example.holajicap.dao.UserDao;
import com.example.holajicap.dao.WalletDao;
import com.example.holajicap.model.Category;
import com.example.holajicap.model.Currency;
import com.example.holajicap.model.Transaction;
import com.example.holajicap.model.User;
import com.example.holajicap.model.Wallet;

@Database(entities = {Currency.class,User.class, Wallet.class, Category.class, Transaction.class}, version = 7)
@TypeConverters({Converter.class})
public abstract class HolaJicapDatabase extends RoomDatabase {
    public abstract CurrencyDao currencyDao();
    public abstract UserDao userDao();
    public abstract CategoryDao categoryDao();
    public abstract WalletDao walletDao();
    public abstract TransactionDao transactionDao();

    private static HolaJicapDatabase instance;

    public static HolaJicapDatabase getInstance(Context context){


        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            HolaJicapDatabase.class, "app_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

            //insert data here
        }
        return instance;
    }
}
