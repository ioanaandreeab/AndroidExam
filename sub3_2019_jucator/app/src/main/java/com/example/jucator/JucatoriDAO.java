package com.example.jucator;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface JucatoriDAO {
    @Insert
    public long insertJucatorInDB(Jucator jucator);

    @Query("SELECT * FROM jucatori")
    public List<Jucator> selectAllJucatoriFromDB();
}
