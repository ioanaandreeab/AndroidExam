package com.example.examen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Examen> examene = new ArrayList<>();
    ListView LVExamene;
    private final int COD_ADAUGARE_EXAMEN = 333;
    private final int COD_EDITARE_EXAMEN = 444;
    int pozitie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LVExamene = findViewById(R.id.LVExamene);

        LVExamene.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pozitie = position;
                Intent intent = new Intent(getApplicationContext(),AdaugaExamenActivity.class);
                intent.putExtra("deEditat",examene.get(position));
                startActivityForResult(intent,COD_EDITARE_EXAMEN);
            }
        });

        LVExamene.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pozitie = position;
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle(getResources().getString(R.string.confirmare))
                        .setPositiveButton(getResources().getString(R.string.da), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                examene.remove(pozitie);
                                adaugareExameneInLV();
                            }
                        }).setNegativeButton(getResources().getString(R.string.nu), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.renuntat),Toast.LENGTH_LONG).show();
                            }
                        }).create();
                alertDialog.show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_examen,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuAdaugaExamen:
                Intent intent = new Intent(getApplicationContext(),AdaugaExamenActivity.class);
                startActivityForResult(intent,COD_ADAUGARE_EXAMEN);
                break;
            case R.id.menuSincronizareRetea:
                break;
            case R.id.menuGrafic:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void adaugareExameneInLV(){
        ArrayAdapter<Examen> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,examene);
        LVExamene.invalidate();
        LVExamene.setAdapter(arrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COD_ADAUGARE_EXAMEN){
            if(resultCode == RESULT_OK){
                Examen rezultat = data.getParcelableExtra("examenAdaugat");
                examene.add(rezultat);
                adaugareExameneInLV();
            }
        }
        if(requestCode == COD_EDITARE_EXAMEN){
            if(resultCode == RESULT_OK) {
                Examen examenEditat = data.getParcelableExtra("examenAdaugat");
                examene.get(pozitie).setDenumireMaterie(examenEditat.getDenumireMaterie());
                examene.get(pozitie).setId(examenEditat.getId());
                examene.get(pozitie).setNumarStudenti(examenEditat.getNumarStudenti());
                examene.get(pozitie).setSala(examenEditat.getSala());
                examene.get(pozitie).setSupraveghetor(examenEditat.getSala());
                adaugareExameneInLV();
            }
        }
    }
}
