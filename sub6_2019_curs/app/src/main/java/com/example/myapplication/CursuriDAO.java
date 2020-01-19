package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CursuriDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCurs(Curs curs);

    @Query("SELECT * FROM cursuri;")
    List<Curs> selectAllCursuri();
}
