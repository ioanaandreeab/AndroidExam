package com.example.televizor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaTelevizoare extends AppCompatActivity {
    private final int COD_EDITARE_TELEVIZOR = 555;
    ListView LVTelevizoare;
    ArrayList<Televizor> televizoare = new ArrayList<>();
    Intent intent;
    int pozitie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_televizoare);
        intent = getIntent();
        televizoare = intent.getParcelableArrayListExtra("televizoare");
        LVTelevizoare = findViewById(R.id.LVTelevizoare);
        adaugaTelevizoareInLV();

        LVTelevizoare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pozitie = position;
                Intent intentEditare = new Intent(getApplicationContext(),InregistrareTelevizor.class);
                intentEditare.putExtra("deEditat",televizoare.get(position));
                startActivityForResult(intentEditare, COD_EDITARE_TELEVIZOR);
            }
        });
    }

    private void adaugaTelevizoareInLV() {
        ArrayAdapter<Televizor> adaptorLV = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,televizoare);
        LVTelevizoare.invalidate();
        LVTelevizoare.setAdapter(adaptorLV);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COD_EDITARE_TELEVIZOR){
            if(resultCode == RESULT_OK){
                Televizor televizorEditat = data.getParcelableExtra("televizorAdaugat");
                televizoare.get(pozitie).setData_intrarii(televizorEditat.getData_intrarii());
                televizoare.get(pozitie).setDiagonala(televizorEditat.getDiagonala());
                televizoare.get(pozitie).setNr_inventar(televizorEditat.getNr_inventar());
                televizoare.get(pozitie).setProducator(televizorEditat.getProducator());
                televizoare.get(pozitie).setProprietar(televizorEditat.getProprietar());
                adaugaTelevizoareInLV();
            }
        }
    }

    @Override
    public void onBackPressed() {
        intent.putParcelableArrayListExtra("televizoareModificate",televizoare);
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }
}
