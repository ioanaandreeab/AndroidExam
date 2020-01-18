package com.example.concedii;

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
import java.util.Date;
import java.util.Locale;

public class OperatiuneOferta extends AppCompatActivity {
    String codPrimit;
    HomeExchange oferta;
    Intent intent;
    EditText ETAdresa;
    EditText ETNrCamere;
    EditText ETSuprafata;
    EditText ETPerioada;
    Spinner spinnerTipuri;
    ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operatiune_oferta);
        intent = getIntent();
        codPrimit = intent.getStringExtra("cod");
        Toast.makeText(getApplicationContext(),codPrimit,Toast.LENGTH_LONG).show();
        initComponente();
        if(codPrimit.equals("editare")){
            oferta = intent.getParcelableExtra("deEditat");
            Toast.makeText(getApplicationContext(),oferta.toString(),Toast.LENGTH_LONG).show();
            setContentComponente();
        }
    }

    private void setContentComponente(){
        ETAdresa.setText(oferta.getAdresa());
        ETNrCamere.setText(String.valueOf(oferta.getNumarCamere()));
        ETPerioada.setText(new SimpleDateFormat("dd/MM/yyyy",Locale.ITALY).format(oferta.getPerioada()));
        ETSuprafata.setText(String.valueOf(oferta.getSuprafata()));
        int tipPosition = spinnerAdapter.getPosition(oferta.getTipLocuinta());
        spinnerTipuri.setSelection(tipPosition);
    }

    private void initComponente(){
        spinnerTipuri = findViewById(R.id.spinnerTipLocuinta);
        spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.tipuri));
        spinnerTipuri.setAdapter(spinnerAdapter);

        ETAdresa = findViewById(R.id.ETAdresa);
        ETNrCamere = findViewById(R.id.ETNrCamere);
        ETSuprafata = findViewById(R.id.ETSuprafata);
        ETPerioada = findViewById(R.id.ETperioada);
    }

    public void salveazaOferta(View view) {
        if(valid()){
            String adresa = ETAdresa.getText().toString();
            int nrCamere = Integer.parseInt(ETNrCamere.getText().toString());
            float suprafata = Float.parseFloat(ETSuprafata.getText().toString());
            String tip = spinnerTipuri.getSelectedItem().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.ITALY);
            Date perioada = null;
            try{
                perioada = sdf.parse(ETPerioada.getText().toString());
            }catch (ParseException e){
                e.printStackTrace();
            }
            oferta = new HomeExchange(adresa,nrCamere,suprafata,perioada,tip);
            intent.putExtra("ofertaAdaugata",oferta);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private boolean valid() {
        if(ETAdresa.getText().toString()==null || ETAdresa.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.adresa_invalida),Toast.LENGTH_LONG).show();
            return false;
        }

        if(ETNrCamere.getText().toString()==null || ETNrCamere.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.adresa_invalida),Toast.LENGTH_LONG).show();
            return false;
        }

        if(ETSuprafata.getText().toString()==null || ETSuprafata.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.suprafata_invalida),Toast.LENGTH_LONG).show();
            return false;
        }

        if(ETPerioada.getText().toString()==null || ETPerioada.getText().toString().trim().isEmpty() || !validateDate(ETPerioada.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),getString(R.string.perioada_invalida),Toast.LENGTH_LONG).show();
            return false;

        }
        return true;
    }

    private boolean validateDate(String perioada){
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        try {
            sdf.parse(perioada);
        } catch (ParseException e){
            return false;
        }
        return  true;
    }

    public void renunta(View view) {
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
