package com.example.articole;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {
    ListView LVArticole;
    ArrayList<Articol> articole = new ArrayList<>();
    Intent intent;
    int pozitie;
    private final int COD_EDITARE_ARTICOL = 555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        intent = getIntent();
        articole = intent.getParcelableArrayListExtra("articole");
        LVArticole = findViewById(R.id.LVArticole);
        adaugaArticoleInLV();

        LVArticole.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pozitie = position;
                Intent intent = new Intent(getApplicationContext(),AdaugaArticolActivity.class);
                intent.putExtra("deEditat",articole.get(position));
                startActivityForResult(intent,COD_EDITARE_ARTICOL);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COD_EDITARE_ARTICOL){
            if(resultCode == RESULT_OK){
                Articol articolRezutat = data.getParcelableExtra("articolAdaugat");
                articole.get(pozitie).setNumarAutori(articolRezutat.getNumarAutori());
                articole.get(pozitie).setTitlu(articolRezutat.getTitlu());
                articole.get(pozitie).setPrimaPagina(articolRezutat.getPrimaPagina());
                articole.get(pozitie).setUltimaPagina(articolRezutat.getUltimaPagina());
                adaugaArticoleInLV();
            }
        }
    }

    private void adaugaArticoleInLV(){
        ArrayAdapter<Articol> LVAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,articole);
        LVArticole.invalidate();
        LVArticole.setAdapter(LVAdapter);
    }

    @Override
    public void onBackPressed() {
        intent.putParcelableArrayListExtra("articoleRezultat",articole);
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }
}
