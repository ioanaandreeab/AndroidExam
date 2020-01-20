package com.example.televizor;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters({DateConverter.class})
@Database(entities = Televizor.class,version = 1,exportSchema = false)
public abstract class TelevizoareDatabase extends RoomDatabase {
    public abstract TelevizoareDAO getTelevizoareDAO();
}
