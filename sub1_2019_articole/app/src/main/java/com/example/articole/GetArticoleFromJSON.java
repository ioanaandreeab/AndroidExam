package com.example.articole;

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
import java.util.ArrayList;
import java.util.List;

public class GetArticoleFromJSON extends AsyncTask<String,Void, List<Articol>> {
    @Override
    protected List<Articol> doInBackground(String... strings) {
        List<Articol> articole = new ArrayList<>();

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
            JSONObject objectArticole = objectRezultat.getJSONObject("articole");
            JSONArray arrayArticol = objectArticole.getJSONArray("articol");

            for(int i=0;i<arrayArticol.length();i++) {
                JSONObject objectArticol = arrayArticol.getJSONObject(i);
                String titlu = objectArticol.getString("title");
                int primaPagina = objectArticol.getInt("firstPage");
                int ultimaPagina = objectArticol.getInt("lastPage");
                int nrAutori = objectArticol.getInt("numberAuthors");
                Articol articol = new Articol(titlu,primaPagina,ultimaPagina,nrAutori);
                articole.add(articol);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return articole;
    }
}
