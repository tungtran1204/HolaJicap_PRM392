package com.example.holajicap.db;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.holajicap.dao.UserDao;
import com.example.holajicap.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class HolaJicapDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
