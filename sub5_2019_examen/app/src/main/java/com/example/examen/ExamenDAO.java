package com.example.examen;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExamenDAO {
    @Insert
    long insertExameninBD(Examen examen);

    @Query("SELECT * FROM examene;")
    List<Examen> selectAllExamene();
}
