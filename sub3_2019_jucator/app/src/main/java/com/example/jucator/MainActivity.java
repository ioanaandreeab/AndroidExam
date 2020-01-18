package com.example.jucator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ArrayList<Jucator> jucatori = new ArrayList<>();
    final int COD_ADAUGARE_JUCATOR = 333;
    final int COD_EDITARE_JUCATOR = 444;
    final String TAG = MainActivity.class.getName();
    ListView LVJucatori;
    int pozitieSelectata;
    JucatoriDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init LV
        LVJucatori = findViewById(R.id.LVJucatori);
        adaugaJucatoriLaLV();

        LVJucatori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pozitieSelectata=position;
                Intent intent = new Intent(getApplicationContext(), AdaugaJucator.class);
                intent.putExtra("deEditat",jucatori.get(position));
                startActivityForResult(intent, COD_EDITARE_JUCATOR);
            }
        });

        LVJucatori.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pozitieSelectata = position;
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle(getResources().getString(R.string.confirm_text))
                        .setPositiveButton(getResources().getString(R.string.da), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                jucatori.remove(jucatori.get(pozitieSelectata));
                                LVJucatori.invalidate();
                                adaugaJucatoriLaLV();
                            }
                        }).setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.renuntat),Toast.LENGTH_LONG).show();
                            }
                        }).create();
                alertDialog.show();
                return true;
            }
        });

        database = Room.databaseBuilder(getApplicationContext(),JucatoriDatabase.class,"jucatoriDB").allowMainThreadQueries().build();
    }

    private void adaugaJucatoriLaLV(){
        ArrayAdapter<Jucator> adapterLV = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,jucatori);
        LVJucatori.setAdapter(adapterLV);
    }

    //creare meniu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuAdaugaJucator:
                Intent intent = new Intent(this, AdaugaJucator.class);
                startActivityForResult(intent,COD_ADAUGARE_JUCATOR);
                break;
            case R.id.menuGrafic:
                Intent intentGrafic = new Intent(getApplicationContext(),GraficActivity.class);
                intentGrafic.putParcelableArrayListExtra("jucatori",jucatori);
                startActivity(intentGrafic);
                break;
            case R.id.menuSincronizareRetea:
                sincronizareRetea();
                break;
            default:
                break;
        }
        return true;
    }

    private void sincronizareRetea(){
        GetJucatori getJucatori = new GetJucatori(){
            @Override
            protected void onPostExecute(List<Jucator> jucators) {
                for(Jucator jucator:jucators){
                    jucatori.add(jucator);
                }
                LVJucatori.invalidate();
                adaugaJucatoriLaLV();
            }
        };
        getJucatori.execute("http://pdm.ase.ro/examen/jucatori.json.txt");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==COD_ADAUGARE_JUCATOR){
            if(resultCode==RESULT_OK){
                Jucator jucatorNou = data.getParcelableExtra("jucatorAdaugat");
                jucatori.add(jucatorNou);
                LVJucatori.invalidate();
                adaugaJucatoriLaLV();
            }
        }
        if(requestCode == COD_EDITARE_JUCATOR){
            if(resultCode==RESULT_OK) {
               Jucator jucatorEditat = data.getParcelableExtra("jucatorAdaugat");
               jucatori.get(pozitieSelectata).setNumar(jucatorEditat.getNumar());
               jucatori.get(pozitieSelectata).setDataNasterii(jucatorEditat.getDataNasterii());
               jucatori.get(pozitieSelectata).setNume(jucatorEditat.getNume());
               jucatori.get(pozitieSelectata).setPozitie(jucatorEditat.getPozitie());
               LVJucatori.invalidate();
               adaugaJucatoriLaLV();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        for(int i=0;i<jucatori.size();i++){
            outState.putParcelable("key"+i,jucatori.get(i));
        }
        outState.putInt("listSize",jucatori.size());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int dim = savedInstanceState.getInt("listSize");
        for(int i=0;i<dim;i++){
            jucatori.add((Jucator)savedInstanceState.getParcelable("key"+i));
        }
    }

    public void adaugaJucatoriInBD(View view) {
        for(Jucator jucator:jucatori){
            database.getJucatoriDAO().insertJucatorInDB(jucator);
        }
        List<Jucator> jucatoriDinDB = database.getJucatoriDAO().selectAllJucatoriFromDB();
        for(Jucator jucator : jucatoriDinDB){
            Log.d(TAG,jucator.toString());
        }
    }
}

class GetJucatori extends AsyncTask<String,Void,List<Jucator>>{
    @Override
    protected List<Jucator> doInBackground(String... strings) {
        List<Jucator> jucatori = new ArrayList<>();
        try{
            URL url = new URL(strings[0]);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            InputStream is = http.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String linie = null;
            StringBuilder builder = new StringBuilder();
            while((linie=reader.readLine())!=null){
                builder.append(linie);
            }

            String listaJucatoriString=builder.toString();
            JSONObject object = new JSONObject(listaJucatoriString);
            JSONObject echipa = object.getJSONObject("echipa");
            JSONArray jucatoriArray = echipa.getJSONArray("jucatori");
            for(int i=0;i<jucatoriArray.length();i++){
                JSONObject jucator = jucatoriArray.getJSONObject(i);
                String nume = jucator.getString("nume");
                int numar = jucator.getInt("numar");
                Date dataNasterii=null;
                try{
                dataNasterii = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).parse(jucator.getString("dataNasterii"));
                } catch (ParseException e){
                    e.printStackTrace();
                }
                String pozitie=jucator.getString("pozitie");
                Jucator jucatorFromJSON = new Jucator(numar,nume,dataNasterii,pozitie);
                jucatori.add(jucatorFromJSON);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jucatori;
    }
}