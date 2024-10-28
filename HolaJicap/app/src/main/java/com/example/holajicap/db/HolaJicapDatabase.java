package com.example.holajicap.db;
import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.holajicap.App;
import com.example.holajicap.dao.UserDao;
import com.example.holajicap.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class HolaJicapDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static HolaJicapDatabase instance;

    public static HolaJicapDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            HolaJicapDatabase.class, "app_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
