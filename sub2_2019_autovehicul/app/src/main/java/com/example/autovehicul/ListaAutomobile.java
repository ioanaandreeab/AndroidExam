package com.example.autovehicul;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaAutomobile extends AppCompatActivity {
    ArrayList<Autovehicul> autovehicule = new ArrayList<>();
    Intent intent;
    ListView LVAutovehicule;
    private final int COD_EDITARE = 444;
    int pozitie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_automobile);
        intent = getIntent();
        autovehicule = intent.getParcelableArrayListExtra("autovehicule");
        LVAutovehicule = findViewById(R.id.LVAutovehicule);
        LVAutovehicule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pozitie = position;
                Intent intentEditare = new Intent(getApplicationContext(),AdaugaAutovehicul.class);
                intentEditare.putExtra("deEditat",autovehicule.get(position));
                startActivityForResult(intentEditare,COD_EDITARE);
            }
        });
        adaugaAutovehiculeInLV();
    }

    private void adaugaAutovehiculeInLV() {
        //ArrayAdapter<Autovehicul> adapterLV = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,autovehicule);
        AutovehiculAdapter adapterLV = new AutovehiculAdapter(getApplicationContext(),R.layout.autovehicule_adapter,autovehicule);
        LVAutovehicule.invalidate();
        LVAutovehicule.setAdapter(adapterLV);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COD_EDITARE) {
            if (resultCode == RESULT_OK){
                Autovehicul autovehiculEditat = data.getParcelableExtra("autovehiculAdaugat");
                autovehicule.get(pozitie).setaPlatit(autovehiculEditat.getAPlatit());
                autovehicule.get(pozitie).setDataInregistrarii(autovehiculEditat.getDataInregistrarii());
                autovehicule.get(pozitie).setIdLocParcare(autovehiculEditat.getIdLocParcare());
                autovehicule.get(pozitie).setNumarAuto(autovehiculEditat.getNumarAuto());
                adaugaAutovehiculeInLV();
            }
        }
    }

    @Override
    public void onBackPressed() {
        intent.putExtra("autovehiculeModificate",autovehicule);
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }
}
