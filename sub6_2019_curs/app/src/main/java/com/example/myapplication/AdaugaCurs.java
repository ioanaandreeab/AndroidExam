package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdaugaCurs extends AppCompatActivity {
    Curs curs;
    Intent intent;
    EditText ETId;
    EditText ETDenumire;
    EditText ETNrParticipanti;
    EditText ETSala;
    EditText ETProfTitular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_curs);
        intent = getIntent();
        initComponente();
        if(intent.hasExtra("deEditat")){
            curs = intent.getParcelableExtra("deEditat");
            setContentComponente();
        }
    }

    private void setContentComponente(){
        ETId.setText(String.valueOf(curs.getIdCurs()));
        ETProfTitular.setText(curs.getProfesorTitular());
        ETSala.setText(String.valueOf(curs.getSala()));
        ETNrParticipanti.setText(String.valueOf(curs.getNumarParticipanti()));
        ETDenumire.setText(curs.getDenumire());
    }

    private void initComponente(){
        ETId = findViewById(R.id.ETId);
        ETDenumire = findViewById(R.id.ETDenumire);
        ETNrParticipanti = findViewById(R.id.ETNrParticipanti);
        ETSala = findViewById(R.id.ETSala);
        ETProfTitular = findViewById(R.id.ETProfTitular);
    }

    public void salveazaCurs(View view) {
        if(valideazaDate()){
            int idCurs = Integer.parseInt(ETId.getText().toString());
            String denumire = ETDenumire.getText().toString();
            int nrParticipanti = Integer.parseInt(ETNrParticipanti.getText().toString());
            String sala = ETSala.getText().toString();
            String profTitular = ETProfTitular.getText().toString();
            curs = new Curs(idCurs,denumire,nrParticipanti,sala,profTitular);
            intent.putExtra("cursAdaugat",curs);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    private boolean valideazaDate() {
        if(ETId.getText().toString() == null|| ETId.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.id_invalid),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETDenumire.getText().toString() == null || ETDenumire.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.denumire_invalida),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETNrParticipanti.getText().toString()==null || ETNrParticipanti.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.part_invalid),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETSala.getText().toString()==null || ETSala.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.sala_invalida),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETProfTitular.getText().toString()==null || ETProfTitular.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.prof_invalid),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void renuntaOperatiune(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
