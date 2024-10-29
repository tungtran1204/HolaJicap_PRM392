package com.example.holajicap.db;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.holajicap.dao.CategoryDao;
import com.example.holajicap.dao.TransactionDao;
import com.example.holajicap.dao.UserDao;
import com.example.holajicap.dao.WalletDao;
import com.example.holajicap.model.Category;
import com.example.holajicap.model.Transaction;
import com.example.holajicap.model.User;
import com.example.holajicap.model.Wallet;

@Database(entities = {User.class, Wallet.class, Category.class, Transaction.class}, version = 1)
public abstract class HolaJicapDatabase extends RoomDatabase {
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
                    .build();

            //insert data here
        }
        return instance;
    }
}
