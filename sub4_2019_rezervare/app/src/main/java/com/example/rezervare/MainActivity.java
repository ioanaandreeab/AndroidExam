package com.example.rezervare;

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
import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    List<Rezervare> rezervari = new ArrayList<>();
    private final int COD_ADAUGA_REZERVARE = 333;
    private final int COD_EDITEAZA_REZERVARE = 444;
    ListView LVRezervari;
    int pozitie;
    RezervariDatabase database;
    final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = Room.databaseBuilder(this,RezervariDatabase.class,"rezervariDB").allowMainThreadQueries().build();
        LVRezervari = findViewById(R.id.LVRezervari);
        LVRezervari.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pozitie = position;
                Rezervare deEditat = rezervari.get(position);
                Intent intent = new Intent(getApplicationContext(),AdaugaRezervareActivity.class);
                intent.putExtra("deEditat",deEditat);
                startActivityForResult(intent,COD_EDITEAZA_REZERVARE);
            }
        });

        LVRezervari.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                pozitie = position;
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle("Suneti sigur ca doriti stergerea elementului?")
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                rezervari.remove(pozitie);
                                adaugaRezervariInLV();
                            }
                        })
                        .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"Ati renuntat la stergere",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                alertDialog.show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rezervare_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuAdauga:
                Intent intent = new Intent(getApplicationContext(),AdaugaRezervareActivity.class);
                startActivityForResult(intent, COD_ADAUGA_REZERVARE);
                break;
            case R.id.menuDespre:
                AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.info_title))
                        .setMessage("Utilizator - "+ getResources().getString(R.string.utilizator)+ " Data - " + Calendar.getInstance().getTime())
                        .setIcon(R.drawable.ic_android_black_24dp)
                        .create();
                alertDialog.show();
                break;
            case R.id.menuGrafic:
                Intent intentChart = new Intent(getApplicationContext(),ChartActivity.class);
                ArrayList<Rezervare> rezervariChart = new ArrayList<>();
                for (Rezervare rezervare : rezervari){
                    rezervariChart.add(rezervare);
                }
                intentChart.putParcelableArrayListExtra("rezervariChart",rezervariChart);
                startActivity(intentChart);
                break;
            case R.id.menuPreluareRezervari:
                GetRezervariFromJSON getRezervari = new GetRezervariFromJSON(){
                    @Override
                    protected void onPostExecute(List<Rezervare> rezervares) {
                        super.onPostExecute(rezervares);
                        for(Rezervare rezervare : rezervares){
                            rezervari.add(rezervare);
                            adaugaRezervariInLV();
                        }
                    }
                };
                getRezervari.execute("http://pdm.ase.ro/examen/rezervari.json.txt");
                break;
            case R.id.menuSalvareBD:
                for(Rezervare rezervare : rezervari){
                    database.getRezervariDAO().inserareRezervare(rezervare);
                }
                List<Rezervare> rezervariFromDB = database.getRezervariDAO().selectAllRezervari();
                for(Rezervare rezervare: rezervariFromDB){
                    Log.d(TAG,rezervare.toString());
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void adaugaRezervariInLV(){
        ArrayAdapter<Rezervare> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,rezervari);
        LVRezervari.invalidate();
        LVRezervari.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COD_ADAUGA_REZERVARE){
            if(resultCode==RESULT_OK) {
                Rezervare rezervare = data.getParcelableExtra("rezervareAdaugata");
                rezervari.add(rezervare);
                adaugaRezervariInLV();
            }
        }
        if(requestCode == COD_EDITEAZA_REZERVARE) {
            if(resultCode == RESULT_OK){
                Rezervare rezervareEditata = data.getParcelableExtra("rezervareAdaugata");
                rezervari.get(pozitie).setDataCazare(rezervareEditata.getDataCazare());
                rezervari.get(pozitie).setDurataSejur(rezervareEditata.getDurataSejur());
                rezervari.get(pozitie).setIdRezervare(rezervareEditata.getIdRezervare());
                rezervari.get(pozitie).setNumeClient(rezervareEditata.getNumeClient());
                rezervari.get(pozitie).setSumaPlata(rezervareEditata.getSumaPlata());
                rezervari.get(pozitie).setTipCamera(rezervareEditata.getTipCamera());
                adaugaRezervariInLV();
            }
        }
    }
}

class GetRezervariFromJSON extends AsyncTask<String,Void,List<Rezervare>> {
    @Override
    protected List<Rezervare> doInBackground(String... strings) {
        List<Rezervare> rezervari = new ArrayList<>();
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            InputStream is = http.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            while((line=br.readLine())!=null){
                stringBuilder.append(line);
            }

            //get full object from stringbuilder -> string
            String objectString = stringBuilder.toString();
            JSONObject object = new JSONObject(objectString);
            JSONObject rezervariObj = object.getJSONObject("rezervari");
            JSONArray rezervariArray = rezervariObj.getJSONArray("rezervare");
            for(int i=0;i<rezervariArray.length();i++){
                JSONObject rezervareObj = rezervariArray.getJSONObject(i);
                int idRezervare = rezervareObj.getInt("idRezervare");
                String numeClient = rezervareObj.getString("numeClient");
                String tipCamera = rezervareObj.getString("tipCamera");
                int durataSejur = rezervareObj.getInt("durataSejur");
                int sumaPlata = rezervareObj.getInt("sumaPlata");
                String data = rezervareObj.getString("dataCazare");
                Date dataCazare = null;
                try {
                    dataCazare = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.ITALY).parse(data);
                } catch (ParseException e){
                    e.printStackTrace();
                }
                Rezervare rezervare = new Rezervare(idRezervare,numeClient,tipCamera,durataSejur,sumaPlata,dataCazare);
                rezervari.add(rezervare);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rezervari;
    }
}
