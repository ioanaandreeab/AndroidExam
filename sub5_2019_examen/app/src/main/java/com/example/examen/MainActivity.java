package com.example.examen;

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
    List<Examen> examene = new ArrayList<>();
    ListView LVExamene;
    private final int COD_ADAUGARE_EXAMEN = 333;
    private final int COD_EDITARE_EXAMEN = 444;
    int pozitie;
    ExamenDatabase database;
    private final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = Room.databaseBuilder(this,ExamenDatabase.class,"exameneDB").allowMainThreadQueries().build();

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
                GetExameneFromJSON getExameneFromJSON = new GetExameneFromJSON(){
                    @Override
                    protected void onPostExecute(List<Examen> examen) {
                        super.onPostExecute(examen);
                        for(Examen e :examen){
                            examene.add(e);
                        }
                        adaugareExameneInLV();
                    }
                };
                getExameneFromJSON.execute("http://pdm.ase.ro/examen/examen.json.txt");
                break;
            case R.id.menuGrafic:
                Intent intentGrafic = new Intent(getApplicationContext(),ChartActivity.class);
                ArrayList<Examen> examenePentruGrafic = new ArrayList<>();
                for(Examen examen : examene) {
                    examenePentruGrafic.add(examen);
                }
                intentGrafic.putParcelableArrayListExtra("examene",examenePentruGrafic);
                startActivity(intentGrafic);
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

    public void adaugaExameneInBD(View view) {
        for(Examen examen : examene){
            database.getExamenDAO().insertExameninBD(examen);
        }
        List<Examen> exameneDinBD = database.getExamenDAO().selectAllExamene();
        for(Examen examen: exameneDinBD){
            Log.d(TAG,examen.toString());
        }

    }
}

class GetExameneFromJSON extends AsyncTask<String, Void,List<Examen>>{
    @Override
    protected List<Examen> doInBackground(String... strings) {
        List<Examen> examene = new ArrayList<>();

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            InputStream is = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            StringBuilder stringBuilder = new StringBuilder();

            while((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }

            String rezultat = stringBuilder.toString();
            JSONObject objectTotal = new JSONObject(rezultat);
            JSONArray exameneArray = objectTotal.getJSONArray("examene");
            for(int i=0;i<exameneArray.length();i++){
                JSONObject examenObject = exameneArray.getJSONObject(i);
                int id = examenObject.getInt("id");
                String denumireMaterie = examenObject.getString("denumireMaterie");
                int numarStudenti = examenObject.getInt("numarStudenti");
                String sala = examenObject.getString("sala");
                String supraveghetor = examenObject.getString("supraveghetor");
                Examen examen = new Examen(id, denumireMaterie,numarStudenti,sala,supraveghetor);
                examene.add(examen);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return examene;
    }
}
