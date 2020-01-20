package com.example.apackage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PackageDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPackageInBD(DataPackage dataPackage);

    @Query("SELECT * FROM packages;")
    List<DataPackage> selectAllPackagesFromDB();
}
