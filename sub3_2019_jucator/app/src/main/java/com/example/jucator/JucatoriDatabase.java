package com.example.jucator;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = Jucator.class,version = 1,exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class JucatoriDatabase extends RoomDatabase {
    public abstract JucatoriDAO getJucatoriDAO();
}
