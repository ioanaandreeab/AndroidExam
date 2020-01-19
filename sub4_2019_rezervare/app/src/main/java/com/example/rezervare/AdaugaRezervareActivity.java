package com.example.rezervare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdaugaRezervareActivity extends AppCompatActivity {
    EditText ETIdRezervare;
    EditText ETNumeClient;
    Spinner spinnerTipCamera;
    EditText ETDurataSejur;
    EditText ETSumaPlata;
    EditText ETDataCazare;
    ArrayAdapter<String> spinnerAdapter;
    Intent intent;
    private final String PATTERN = "dd/MM/yyyy hh:mm";
    Rezervare rezervare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_rezervare);
        initComponente();
        intent = getIntent();
        if(intent.hasExtra("deEditat")){
            rezervare = intent.getParcelableExtra("deEditat");
            setContentComponente();
        }
    }

    private void setContentComponente() {
        ETSumaPlata.setText(String.valueOf(rezervare.getSumaPlata()));
        ETDurataSejur.setText(String.valueOf(rezervare.getDurataSejur()));
        ETIdRezervare.setText(String.valueOf(rezervare.getIdRezervare()));
        ETNumeClient.setText(rezervare.getNumeClient());
        spinnerTipCamera.setSelection(spinnerAdapter.getPosition(rezervare.getTipCamera()));
        ETDataCazare.setText(new SimpleDateFormat(PATTERN,Locale.ITALY).format(rezervare.getDataCazare()));
    }

    private void initComponente() {
        ETIdRezervare = findViewById(R.id.ETIdRezervare);
        ETNumeClient = findViewById(R.id.ETNumeCurs);
        spinnerTipCamera = findViewById(R.id.spinnerTipCamera);
        spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.tipCamera));
        spinnerTipCamera.setAdapter(spinnerAdapter);
        ETDurataSejur = findViewById(R.id.ETDurataSejur);
        ETDataCazare = findViewById(R.id.ETDataCazare);
        ETSumaPlata = findViewById(R.id.ETSumaPlata);
    }

    public void salveazaRezervare(View view) {
        if(valideazaDatele()){
            int idRezervare = Integer.parseInt(ETIdRezervare.getText().toString());
            String numeClient = ETNumeClient.getText().toString();
            String tipCamera = String.valueOf(spinnerTipCamera.getSelectedItem());
            int durataSejur = Integer.parseInt(ETDurataSejur.getText().toString());
            int sumaPlata = Integer.parseInt(ETSumaPlata.getText().toString());
            Date dataCazare = null;
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN,Locale.ITALY);
            try {
                dataCazare = sdf.parse(ETDataCazare.getText().toString());
            } catch (ParseException e){
                e.printStackTrace();
            }
            rezervare = new Rezervare(idRezervare,numeClient,tipCamera,durataSejur,sumaPlata,dataCazare);
            intent.putExtra("rezervareAdaugata",rezervare);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    private boolean valideazaDatele(){
        if(ETIdRezervare.getText().toString()==null || ETIdRezervare.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Id invalid",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ETNumeClient.getText().toString()==null || ETNumeClient.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Nume client invalid",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ETDurataSejur.getText().toString()==null || ETDurataSejur.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Durata sejur invalida",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ETSumaPlata.getText().toString()==null || ETSumaPlata.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Suma plata invalida",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ETDataCazare.getText().toString()==null || ETDataCazare.getText().toString().trim().isEmpty() || !validareData(ETDataCazare.getText().toString())) {
            Toast.makeText(getApplicationContext(),"Data cazare invalida",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validareData(String data){
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN, Locale.ITALY);
        try {
            sdf.parse(data);
        }catch (ParseException e){
            return false;
        }
        return true;
    }

    public void renuntaOperatiune(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
