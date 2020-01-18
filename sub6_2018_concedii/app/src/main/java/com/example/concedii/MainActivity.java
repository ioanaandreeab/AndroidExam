package com.example.concedii;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    SharedPref mySharedPref;
    private int COD_ADAUGARE_OFERTA = 333;
    ArrayList<HomeExchange> colectie = new ArrayList<>();
    Database oferteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mySharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //preluare data curenta
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);
        long date = new Date().getTime();
        String data = dateFormat.format(date);
        mySharedPref.setUserDate(data);
        oferteDatabase = Room.databaseBuilder(this,Database.class,"oferte").allowMainThreadQueries().build();
        List<HomeExchange> listaTemporara = oferteDatabase.getHomeExchangeDAO().selectAllOferte();
        for(HomeExchange oferta: listaTemporara){
            colectie.add(oferta);
        }
    }

    // afisare date despre utilizator
    public void infoUtilizator(View view) {
        String numeUtilizator = getResources().getString(R.string.user);
        String dataDeschidereApp = mySharedPref.getUserDate();
        Toast.makeText(getApplicationContext(),numeUtilizator + " - " + dataDeschidereApp,Toast.LENGTH_LONG).show();
    }


    public void adaugaOfertaLaunch(View view) {
        Intent intent = new Intent(this,OperatiuneOferta.class);
        intent.putExtra("cod","adaugare");
        startActivityForResult(intent, COD_ADAUGARE_OFERTA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == COD_ADAUGARE_OFERTA){
            if(resultCode == RESULT_OK){
            HomeExchange ofertaNoua = data.getParcelableExtra("ofertaAdaugata");
            Toast.makeText(getApplicationContext(),ofertaNoua.toString(),Toast.LENGTH_LONG).show();
            colectie.add(ofertaNoua);
            oferteDatabase.getHomeExchangeDAO().insert(ofertaNoua);
            }
        }
    }

    public void deschideLista(View view) {
        Intent intent = new Intent(this,ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("colectie",colectie);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void deschideCentralizator(View view) {
        Intent intent = new Intent(this, CentralizatorActivity.class);
        startActivity(intent);
    }
}
