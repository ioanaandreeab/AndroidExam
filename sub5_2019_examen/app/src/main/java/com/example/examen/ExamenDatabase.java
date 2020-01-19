package com.example.examen;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Examen.class,version = 1,exportSchema = false)
public abstract class ExamenDatabase extends RoomDatabase {
    public abstract ExamenDAO getExamenDAO();
}
