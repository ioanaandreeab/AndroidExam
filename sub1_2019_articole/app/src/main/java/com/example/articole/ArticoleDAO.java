package com.example.articole;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArticoleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertArticol(Articol articol);

    @Query("SELECT * FROM articole;")
    List<Articol> selectToateArticolele();
}
