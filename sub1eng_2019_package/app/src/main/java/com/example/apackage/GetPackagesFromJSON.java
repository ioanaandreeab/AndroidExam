package com.example.apackage;

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

public class GetPackagesFromJSON extends AsyncTask <String,Void, List<DataPackage>> {
    private final String TIMESTAMP_FORMAT = "dd/MM/yyyy hh:mm";

    @Override
    protected List<DataPackage> doInBackground(String... strings) {
        List<DataPackage> packages = new ArrayList<>();


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
            String result = builder.toString();
            JSONObject objectResult = new JSONObject(result);
            JSONArray packagesArray = objectResult.getJSONArray("packages");
            for(int i=0;i<packagesArray.length();i++){
                JSONObject packageObject = packagesArray.getJSONObject(i);
                int id = packageObject.getInt("packageId");
                String packageType = packageObject.getString("packageType");
                double latitude = packageObject.getDouble("latitude");
                double longitude = packageObject.getDouble("longitude");
                String date = packageObject.getString("timestamp");
                SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT, Locale.ITALY);
                Date timestamp = null;
                try {
                    timestamp = sdf.parse(date);
                } catch (ParseException e){
                    e.printStackTrace();
                }
                DataPackage newPackage = new DataPackage(id,packageType,latitude,longitude,timestamp);
                packages.add(newPackage);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return packages;
    }
}
