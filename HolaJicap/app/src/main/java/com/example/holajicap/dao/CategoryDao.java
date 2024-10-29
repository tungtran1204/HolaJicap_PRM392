package com.example.holajicap.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.holajicap.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Category")
    List<Category> getAll();
    @Query("SELECT * FROM Category WHERE cateType = :cateType")
    List<Category> getCategoriesByType(String cateType);
    @Query("SELECT * FROM Category WHERE cateId = :cateId")
    Category getCategoryById(int cateId);
    @Query("SELECT * FROM Category WHERE cateName = :cateName")
    Category getCategoryByName(String cateName);
    @Query("SELECT * FROM Category WHERE cateType = :cateType AND cateName = :cateName")
    Category getCategoryByTypeAndName(String cateType, String cateName);
}
