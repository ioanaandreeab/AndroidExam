package com.example.jucator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdaugaJucator extends AppCompatActivity {
    EditText ETNumar;
    EditText ETNume;
    EditText ETDataNasterii;
    Spinner spinnerPozitie;
    SpinnerAdapter spinnerAdapter;
    Intent intent;
    Jucator jucator;
    private final String FORMAT_DATA = "dd/MM/yyyy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_jucator);
        initComponente();
        intent = getIntent();
        if(intent.hasExtra("deEditat")){

        }
    }

    private void initComponente(){
        ETNumar =  findViewById(R.id.ETNumar);
        ETNume = findViewById(R.id.ETNume);
        ETDataNasterii = findViewById(R.id.ETDataNasterii);
        spinnerPozitie = findViewById(R.id.spinnerPozitie);
        spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.pozitii));
        spinnerPozitie.setAdapter(spinnerAdapter);
    }

    public void salveazaJucator(View view) {
        if(valideazaDate()){
            int numar = Integer.parseInt(ETNumar.getText().toString());
            String nume = ETNume.getText().toString();
            String pozitie = spinnerPozitie.getSelectedItem().toString();
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATA, Locale.ITALY);
            Date dataNasterii= null;
            try {
                dataNasterii = sdf.parse(ETDataNasterii.getText().toString());
            } catch (ParseException e){
                e.printStackTrace();
            }
            jucator = new Jucator(numar, nume, dataNasterii,pozitie);
            intent.putExtra("jucatorAdaugat",jucator);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    private boolean valideazaDate(){
        if(ETNumar.getText().toString()==null || ETNumar.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.numar_invalid), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ETNume.getText().toString()==null || ETNume.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.nume_invalid), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ETDataNasterii.getText().toString()==null || ETDataNasterii.getText().toString().trim().isEmpty() ||!(valideazaDataNasterii(ETDataNasterii.getText().toString()))){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.numar_invalid), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean valideazaDataNasterii(String perioada){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATA, Locale.ITALY);
        try {
            sdf.parse(perioada);
        } catch (ParseException e){
            return false;
        }
        return true;
    }

    public void renuntaOperatiune(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
