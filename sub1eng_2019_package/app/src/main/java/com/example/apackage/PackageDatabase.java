package com.example.apackage;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters({PackageConverter.class})
@Database(entities = DataPackage.class,version = 1,exportSchema = false)
public abstract class PackageDatabase extends RoomDatabase {
    public abstract PackageDAO getPackageDAO();
}
