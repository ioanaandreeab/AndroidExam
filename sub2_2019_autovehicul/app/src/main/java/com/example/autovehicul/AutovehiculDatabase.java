package com.example.autovehicul;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters({DateConverter.class})
@Database(entities = Autovehicul.class, version = 1, exportSchema = false)
public abstract class AutovehiculDatabase extends RoomDatabase {
    public abstract AutovehiculDAO getAutovehiculDAO();
}
