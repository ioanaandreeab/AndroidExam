package com.example.televizor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TelevizoareDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTelevizorInDB(Televizor televizor);

    @Query("SELECT * FROM televizoare;")
    List<Televizor> selectAllTelevizoareFromBD();
}
