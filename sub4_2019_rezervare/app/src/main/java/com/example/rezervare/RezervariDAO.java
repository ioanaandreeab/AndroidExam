package com.example.rezervare;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RezervariDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long inserareRezervare(Rezervare rezervare);
    @Query("SELECT * from rezervari;")
    List<Rezervare> selectAllRezervari();
}
