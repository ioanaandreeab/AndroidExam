package com.example.articole;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = Articol.class,version = 1,exportSchema = false)
public abstract class ArticoleDatabase extends RoomDatabase {
    public abstract ArticoleDAO getArticoleDAO();
}
