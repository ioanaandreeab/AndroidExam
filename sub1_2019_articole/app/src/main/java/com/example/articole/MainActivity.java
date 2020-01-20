package com.example.articole;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ListActivity;
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
    ArrayList<Articol> articole = new ArrayList<>();
    private final int COD_ADAUGARE_ARTICOL = 333;
    private final int COD_LISTA = 444;
    private final String TAG = MainActivity.class.getName();
    ArticoleDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = Room.databaseBuilder(getApplicationContext(),ArticoleDatabase.class,"articoleDB").allowMainThreadQueries().build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_articole,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_adauga_articol:
                Intent intent = new Intent(getApplicationContext(),AdaugaArticolActivity.class);
                startActivityForResult(intent, COD_ADAUGARE_ARTICOL);
                break;
            case R.id.menu_lista_articole:
                Intent intentLista = new Intent(getApplicationContext(), ListaActivity.class);
                intentLista.putParcelableArrayListExtra("articole",articole);
                startActivityForResult(intentLista,COD_LISTA);
                break;
            case R.id.menu_json:
                GetArticoleFromJSON getArticoleFromJSON = new GetArticoleFromJSON(){
                    @Override
                    protected void onPostExecute(List<Articol> articols) {
                        super.onPostExecute(articols);
                        for(Articol articol : articols) {
                            articole.add(articol);
                        }
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_json),Toast.LENGTH_LONG).show();
                    }
                };
                getArticoleFromJSON.execute("http://pdm.ase.ro/examen/articole.json.txt");
                break;
            case R.id.menu_raport:
                break;
            case R.id.menu_despre:
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.utilizator),Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COD_ADAUGARE_ARTICOL){
            if(resultCode == RESULT_OK) {
                Articol articol = data.getParcelableExtra("articolAdaugat");
                articole.add(articol);
                Toast.makeText(getApplicationContext(),articol.toString(),Toast.LENGTH_LONG).show();
            }
        }
        if(requestCode == COD_LISTA){
            if(resultCode == RESULT_OK) {
                ArrayList<Articol>  articolePrimite = data.getParcelableArrayListExtra("articoleRezultat");
                articole = articolePrimite;
            }
        }
    }

    public void salveazaArticoleInBD(View view) {
        for(Articol articol : articole) {
            database.getArticoleDAO().insertArticol(articol);
        }
        List<Articol> articoleDinBD = database.getArticoleDAO().selectToateArticolele();
        for(Articol articol : articoleDinBD) {
            Log.d(TAG, articol.toString());
        }
    }
}
