package com.example.televizor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int COD_ADAUGARE_TELEVIZOR = 333;
    private final int COD_TRANSMITERE_LISTA = 444;
    ArrayList<Televizor> televizoare = new ArrayList<>();
    TelevizoareDatabase database;
    private final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = Room.databaseBuilder(getApplicationContext(),TelevizoareDatabase.class,"televizoareDB").allowMainThreadQueries().build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_televizor,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_despre:
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.autor),Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_inregistrare:
                Intent intent = new Intent(getApplicationContext(),InregistrareTelevizor.class);
                startActivityForResult(intent,COD_ADAUGARE_TELEVIZOR);
                break;
            case R.id.menu_JSON:
                break;
            case R.id.menu_lista:
                Intent intentLista = new Intent(getApplicationContext(),ListaTelevizoare.class);
                intentLista.putParcelableArrayListExtra("televizoare",televizoare);
                startActivityForResult(intentLista,COD_TRANSMITERE_LISTA);
                break;
            case R.id.menu_raport:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COD_ADAUGARE_TELEVIZOR){
            if(resultCode == RESULT_OK){
                Televizor televizor = data.getParcelableExtra("televizorAdaugat");
                televizoare.add(televizor);
            }
        }
        if(requestCode == COD_TRANSMITERE_LISTA){
            if(resultCode == RESULT_OK){
                ArrayList<Televizor> televizoareModificate = data.getParcelableArrayListExtra("televizoareModificate");
                televizoare = televizoareModificate;
            }
        }
    }

    public void adaugaTelevizoareInBD(View view) {
        for(Televizor televizor : televizoare) {
            database.getTelevizoareDAO().insertTelevizorInDB(televizor);
        }
        List<Televizor> televizoareDinBD = database.getTelevizoareDAO().selectAllTelevizoareFromBD();
        for(Televizor televizor : televizoareDinBD){
            Log.d(TAG,televizor.toString());
        }
    }
}
