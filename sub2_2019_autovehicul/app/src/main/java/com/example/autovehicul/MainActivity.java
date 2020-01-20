package com.example.autovehicul;

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
    ArrayList<Autovehicul> autovehicule = new ArrayList<>();
    private final int COD_ADAUGARE = 333;
    private final int COD_LISTA = 555;
    AutovehiculDatabase database;
    private final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = Room.databaseBuilder(getApplicationContext(),AutovehiculDatabase.class,"autovehiculeDB").allowMainThreadQueries().build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_autovehicul,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_adauga:
                Intent intentAdaugare = new Intent(getApplicationContext(),AdaugaAutovehicul.class);
                startActivityForResult(intentAdaugare, COD_ADAUGARE);
                break;
            case R.id.menu_despre:
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.autor),Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_JSON:
                GetAutovehiculeFromJSON getAutovehiculeFromJSON = new GetAutovehiculeFromJSON(){
                    @Override
                    protected void onPostExecute(List<Autovehicul> autovehiculs) {
                        super.onPostExecute(autovehiculs);
                        for(Autovehicul autovehicul : autovehiculs){
                            autovehicule.add(autovehicul);
                        }
                    }
                };
                getAutovehiculeFromJSON.execute("http://pdm.ase.ro/examen/autovehicule.json.txt");
                break;
            case R.id.menu_lista:
                Intent intentLista = new Intent(getApplicationContext(),ListaAutomobile.class);
                intentLista.putParcelableArrayListExtra("autovehicule",autovehicule);
                startActivityForResult(intentLista,COD_LISTA);
                break;
            case R.id.menu_raport:
                Intent intentChart = new Intent(getApplicationContext(),ChartActivity.class);
                intentChart.putParcelableArrayListExtra("autovehiculeChart",autovehicule);
                startActivity(intentChart);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COD_ADAUGARE) {
            if(resultCode == RESULT_OK){
                Autovehicul autovehiculNou = data.getParcelableExtra("autovehiculAdaugat");
                autovehicule.add(autovehiculNou);
            }
        }

        if(requestCode == COD_LISTA) {
            if(resultCode == RESULT_OK){
                ArrayList<Autovehicul> autovehiculeModificate = data.getParcelableArrayListExtra("autovehiculeModificate");
                autovehicule = autovehiculeModificate;
            }
        }
    }

    public void adaugaAutovehiculeInBD(View view) {
        for(Autovehicul autovehicul: autovehicule) {
            database.getAutovehiculDAO().insertAutovehicul(autovehicul);
        }
        List<Autovehicul> autovehiculeDinBD = database.getAutovehiculDAO().selectToateAutovehiculeleDinBD();
        for(Autovehicul autovehicul : autovehiculeDinBD) {
            Log.d(TAG, autovehicul.toString());
        }

    }
}
