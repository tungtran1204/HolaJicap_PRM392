package com.example.holajicap.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.holajicap.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM User WHERE email = :email LIMIT 1")
    User checkEmailExists(String email);

    @Insert
    void signUp(User... users);

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    User signIn(String email, String password);

    @Query("SELECT * FROM User WHERE username = :username")
    User checkUsernameExists(String username);

    @Delete
    void delete(User user);
}