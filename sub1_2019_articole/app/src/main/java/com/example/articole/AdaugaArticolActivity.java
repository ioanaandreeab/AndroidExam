package com.example.articole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdaugaArticolActivity extends AppCompatActivity {
    Intent intent;
    EditText ETTitlu;
    EditText ETPrimaPagina;
    EditText ETUltimaPagina;
    EditText ETNrAutori;
    Articol articol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_articol);
        intent = getIntent();
        initComponente();
        if(intent.hasExtra("deEditat")){
            articol = intent.getParcelableExtra("deEditat");
            setContinutComponente();
        }
    }

    private void setContinutComponente() {
        ETTitlu.setText(articol.getTitlu());
        ETNrAutori.setText(String.valueOf(articol.getNumarAutori()));
        ETPrimaPagina.setText(String.valueOf(articol.getPrimaPagina()));
        ETUltimaPagina.setText(String.valueOf(articol.getUltimaPagina()));
    }

    private void initComponente() {
        ETTitlu = findViewById(R.id.ETTitlu);
        ETPrimaPagina = findViewById(R.id.ETPrimaPagina);
        ETUltimaPagina = findViewById(R.id.ETUltimaPagina);
        ETNrAutori = findViewById(R.id.ETNrAutori);
    }

    public void salveazaArticol(View view) {
        if(valideazaDate()){
            String titlu = ETTitlu.getText().toString();
            int primaPagina = Integer.parseInt(ETPrimaPagina.getText().toString());
            int ultimaPagina = Integer.parseInt(ETUltimaPagina.getText().toString());
            int nrAutori = Integer.parseInt(ETNrAutori.getText().toString());
            articol = new Articol(titlu,primaPagina,ultimaPagina,nrAutori);
            intent.putExtra("articolAdaugat",articol);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    private boolean valideazaDate(){
        if(ETTitlu.getText().toString() ==null ||ETTitlu.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.titlu_invalid),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETPrimaPagina.getText().toString() == null || ETPrimaPagina.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.pp_invalida),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETUltimaPagina.getText().toString() == null || ETUltimaPagina.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.up_invalida),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETNrAutori.getText().toString() == null || ETNrAutori.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.nr_invalid),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void renuntaOperatiune(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
