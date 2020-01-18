package com.example.concedii;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface HomeExchangeDAO {
    @Insert
    long insert(HomeExchange oferta);

    @Query("SELECT * from oferte")
    List<HomeExchange> selectAllOferte();

    @Query("SELECT COUNT(id) from oferte where numarCamere = 1")
    int select1Camera();

    @Query("SELECT COUNT(id) from oferte where numarCamere = 2")
    int select2Camere();

    @Query("SELECT COUNT(id) from oferte where numarCamere = 3")
    int select3Camere();

    @Query("SELECT COUNT(id) from oferte where numarCamere = 4")
    int select4Camere();

    @Query("SELECT COUNT(id) from oferte where numarCamere = 5")
    int select5Camere();
}
