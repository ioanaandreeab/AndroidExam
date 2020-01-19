package com.example.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = Curs.class,version = 1,exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class CursuriDatabase extends RoomDatabase {
    public abstract CursuriDAO getCursuriDAO();
}
