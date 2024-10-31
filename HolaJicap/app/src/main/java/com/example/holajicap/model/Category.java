package com.example.holajicap.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int cateId;
    @ColumnInfo(name = "cateIcon")
    private int cateIcon;
    @ColumnInfo(name = "cateName")
    private String cateName;
    @ColumnInfo(name = "cateType")
    private String cateType;

    public Category() {
    }

    public Category(int cateId, int cateIcon, String cateName, String cateType) {
        this.cateId = cateId;
        this.cateIcon = cateIcon;
        this.cateName = cateName;
        this.cateType = cateType;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public int getCateIcon() {
        return cateIcon;
    }

    public void setCateIcon(int cateIcon) {
        this.cateIcon = cateIcon;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateType() {
        return cateType;
    }

    public void setCateType(String cateType) {
        this.cateType = cateType;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cateId=" + cateId +
                ", cateIcon=" + cateIcon +
                ", cateName='" + cateName + '\'' +
                ", cateType='" + cateType + '\'' +
                '}';
    }
}
