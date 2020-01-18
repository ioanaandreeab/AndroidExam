package com.example.concedii;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@androidx.room.Database(entities = HomeExchange.class,version = 1,exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class Database extends RoomDatabase {
    public abstract HomeExchangeDAO getHomeExchangeDAO();
}
