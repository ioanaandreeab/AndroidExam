package com.example.autovehicul;

import android.os.AsyncTask;

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

public class GetAutovehiculeFromJSON extends AsyncTask<String, Void, List<Autovehicul>> {
    private final String FORMAT_DATA = "dd/MM/yyyy hh:mm";
    @Override
    protected List<Autovehicul> doInBackground(String... strings) {
        List<Autovehicul> autovehicule = new ArrayList<>();

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

            String rezultat = builder.toString();
            JSONObject objectRezultat = new JSONObject(rezultat);
            JSONObject objectAutovehicul = objectRezultat.getJSONObject("autovehicule");
            JSONArray autovehiculeArray = objectAutovehicul.getJSONArray("autovehicul");
            for(int i=0;i<autovehiculeArray.length();i++){
                JSONObject autovehiculObj = autovehiculeArray.getJSONObject(i);
                String nrAuto = autovehiculObj.getString("numarAuto");
                String data = autovehiculObj.getString("dataInregistrarii");
                int idLoc = autovehiculObj.getInt("idParcare");
                Date dataInregistrarii = null;
                try {
                    dataInregistrarii = new SimpleDateFormat(FORMAT_DATA, Locale.ITALY).parse(data);
                } catch (ParseException e){
                    e.printStackTrace();
                }
                String loc = autovehiculObj.getString("locPlatit");
                boolean locPlatit;
                if(loc.equals("3")){
                    locPlatit = Boolean.parseBoolean("false");
                } else {
                    locPlatit = Boolean.parseBoolean(loc);
                }
                Autovehicul autovehicul = new Autovehicul(nrAuto,dataInregistrarii,idLoc,locPlatit);
                autovehicule.add(autovehicul);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return autovehicule;
    }
}
