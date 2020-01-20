package com.example.autovehicul;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AutovehiculDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAutovehicul(Autovehicul autovehicul);

    @Query("SELECT * FROM autovehicule;")
    List<Autovehicul> selectToateAutovehiculeleDinBD();
}
