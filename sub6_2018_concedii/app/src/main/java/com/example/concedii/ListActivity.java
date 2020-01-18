package com.example.concedii;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    List<HomeExchange> colectie;
    Intent intent;
    ListView LVOferte;
    int pozitieSelectata;
    final int COD_EDITARE = 444;
    Database oferteDB;
    private final String TAG_USED = ListActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        intent = getIntent();
        colectie = intent.getParcelableArrayListExtra("colectie");
        LVOferte = findViewById(R.id.LVOferte);
        addInLV();

        oferteDB= Room.databaseBuilder(getApplicationContext(),Database.class,"oferte").allowMainThreadQueries().build();
        List<HomeExchange> listFromDB = oferteDB.getHomeExchangeDAO().selectAllOferte();
        for(HomeExchange oferta :listFromDB){
            Log.d(TAG_USED,oferta.toString());
        }
        LVOferte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pozitieSelectata = position;
                Intent intent = new Intent(getApplicationContext(), OperatiuneOferta.class);
                intent.putExtra("cod","editare");
                intent.putExtra("deEditat",colectie.get(position));
                startActivityForResult(intent,COD_EDITARE);
            }
        });
    }

    private void addInLV(){
        ListAdapter LVAdapter = new ListAdapter(this,R.layout.exchange_layout,colectie);
        LVOferte.setAdapter(LVAdapter);
        LVAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==COD_EDITARE){
            if(resultCode==RESULT_OK){
                LVOferte.invalidate();
                HomeExchange ofertaEditata = data.getParcelableExtra("ofertaAdaugata");
                colectie.get(pozitieSelectata).setAdresa(ofertaEditata.getAdresa());
                colectie.get(pozitieSelectata).setNumarCamere(ofertaEditata.getNumarCamere());
                colectie.get(pozitieSelectata).setPerioada(ofertaEditata.getPerioada());
                colectie.get(pozitieSelectata).setSuprafata(ofertaEditata.getSuprafata());
                colectie.get(pozitieSelectata).setTipLocuinta(ofertaEditata.getTipLocuinta());
                addInLV();
            }
        }
    }
}
