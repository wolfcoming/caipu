package com.example.caipuandroid.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CaipuDao {
    @Insert
    public void insertCategory(CategoryEntity categoryEntity);

    @Query("select * from category_entity")
    public List<CategoryEntity> getCategory();


    @Query("delete from category_entity")
    void clearCategoryData();
}
