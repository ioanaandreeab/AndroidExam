package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Curs> cursuri = new ArrayList<>();
    ListView LVCursuri;
    int pozitieSelectata;
    private final int COD_ADAUGARE=333;
    private final int COD_EDITARE=444;
    private final String TAG = MainActivity.class.getName();

    CursuriDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LVCursuri = findViewById(R.id.LVCursuri);
        LVCursuri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pozitieSelectata = position;
                Intent intent = new Intent(getApplicationContext(),AdaugaCurs.class);
                intent.putExtra("deEditat",cursuri.get(position));
                startActivityForResult(intent,COD_EDITARE);
            }
        });

        LVCursuri.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pozitieSelectata = position;
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle(getResources().getString(R.string.sigur))
                        .setPositiveButton(getResources().getString(R.string.da), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cursuri.remove(cursuri.get(pozitieSelectata));
                                adaugaCursuriInLV();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.nu), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.renuntat),Toast.LENGTH_LONG).show();
                            }
                        }).create();
                alertDialog.show();
                return true;
            }
        });

        database= Room.databaseBuilder(this,CursuriDatabase.class,"cursuriDB").allowMainThreadQueries().build();
    }

    private void adaugaCursuriInLV(){
        ArrayAdapter<Curs> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,cursuri);
        LVCursuri.invalidate();
        LVCursuri.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_curs,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuAdaugaCurs:
                Intent it = new Intent(this,AdaugaCurs.class);
                startActivityForResult(it,COD_ADAUGARE);
                break;
            case R.id.menuSincronizareRetea:
                GetCursuriFromJSON getCursuriFromJSON = new GetCursuriFromJSON() {
                    @Override
                    protected void onPostExecute(List<Curs> curs) {
                        for(Curs cursJSON : curs){
                            cursuri.add(cursJSON);
                        }
                        adaugaCursuriInLV();
                    }
                };
                getCursuriFromJSON.execute("http://pdm.ase.ro/examen/cursuri.json.txt");
                break;
            case R.id.menuGrafic:
                Intent intentGrafic = new Intent(getApplicationContext(),ChartActivity.class);
                ArrayList<Curs> cursuriGrafic = new ArrayList<>();
                for(Curs curs : cursuri){
                    cursuriGrafic.add(curs);
                }
                intentGrafic.putParcelableArrayListExtra("cursuri",cursuriGrafic);
                startActivity(intentGrafic);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COD_ADAUGARE){
            if(resultCode==RESULT_OK){
                Curs curs = data.getParcelableExtra("cursAdaugat");
                cursuri.add(curs);
                adaugaCursuriInLV();
            }
        }
        if(requestCode == COD_EDITARE){
            if(resultCode == RESULT_OK){
                Curs curs = data.getParcelableExtra("cursAdaugat");
                cursuri.get(pozitieSelectata).setDenumire(curs.getDenumire());
                cursuri.get(pozitieSelectata).setIdCurs(curs.getIdCurs());
                cursuri.get(pozitieSelectata).setNumarParticipanti(curs.getNumarParticipanti());
                cursuri.get(pozitieSelectata).setSala(curs.getSala());
                cursuri.get(pozitieSelectata).setProfesorTitular(curs.getProfesorTitular());
                adaugaCursuriInLV();
            }
        }
    }

    public void salveazaInBD(View view) {
        for(Curs curs:cursuri){
            database.getCursuriDAO().insertCurs(curs);
        }
        List<Curs> cursuriDinBD = new ArrayList<>();
        cursuriDinBD = database.getCursuriDAO().selectAllCursuri();

        for(Curs curs : cursuriDinBD){
            Log.d(TAG,curs.toString());
        }
    }
}

class GetCursuriFromJSON extends AsyncTask<String,Void,List<Curs>>{
    @Override
    protected List<Curs> doInBackground(String... strings) {
        List<Curs> cursuri = new ArrayList<>();
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            InputStream is = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            StringBuilder builder = new StringBuilder();
            while((line=reader.readLine())!=null){
                builder.append(line);
            }

            String listaCursuri = builder.toString();
            JSONObject objectTotalCursuri = new JSONObject(listaCursuri);
            JSONArray listaTotalCursuri = objectTotalCursuri.getJSONArray("cursuri");
            for(int i=0;i<listaTotalCursuri.length();i++){
                JSONObject curs = listaTotalCursuri.getJSONObject(i);
                int idCurs = curs.getInt("idCurs");
                String denumire = curs.getString("denumire");
                int nrParticipanti = curs.getInt("numarParticipanti");
                String sala = curs.getString("sala");
                String profesorTitular = curs.getString("profesorTitular");
                Curs cursJSON = new Curs(idCurs,denumire,nrParticipanti,sala,profesorTitular);
                cursuri.add(cursJSON);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cursuri;
    }
}