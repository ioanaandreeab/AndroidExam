package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdaugaExamenActivity extends AppCompatActivity {
    Examen examen;
    Intent intent;
    EditText ETId;
    EditText ETProfSupraveghetor;
    EditText ETDenumireMaterie;
    EditText ETSala;
    EditText ETNrStudenti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_examen);
        intent = getIntent();
        initComponente();
        if(intent.hasExtra("deEditat")){
            examen = intent.getParcelableExtra("deEditat");
            setContinutComponente();
        }
    }

    private void setContinutComponente(){
        ETId.setText(String.valueOf(examen.getId()));
        ETNrStudenti.setText(String.valueOf(examen.getNumarStudenti()));
        ETSala.setText(examen.getSala());
        ETDenumireMaterie.setText(examen.getDenumireMaterie());
        ETProfSupraveghetor.setText(examen.getSupraveghetor());
    }

    private void initComponente() {
        ETId = findViewById(R.id.ETId);
        ETProfSupraveghetor = findViewById(R.id.ETSupraveghetor);
        ETDenumireMaterie = findViewById(R.id.ETDenumireMaterie);
        ETSala = findViewById(R.id.ETSala);
        ETNrStudenti = findViewById(R.id.ETNrStudenti);
    }

    public void salveazaExamen(View view) {
        if(valideazaDate()){
            int id = Integer.parseInt(ETId.getText().toString());
            String profSupraveghetor = ETProfSupraveghetor.getText().toString();
            String denumireMaterie = ETDenumireMaterie.getText().toString();
            String sala = ETSala.getText().toString();
            int nrStudenti = Integer.parseInt(ETNrStudenti.getText().toString());
            examen = new Examen(id,denumireMaterie,nrStudenti,sala,profSupraveghetor);
            intent.putExtra("examenAdaugat",examen);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    private boolean valideazaDate(){
        if(ETId.getText().toString() == null || ETId.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.id_invalid),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETProfSupraveghetor.getText().toString() == null || ETProfSupraveghetor.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.prof_invalid),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETDenumireMaterie.getText().toString() == null || ETDenumireMaterie.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.denumire_invalida),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETSala.getText().toString() == null || ETSala.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.sala_invalida),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETNrStudenti.getText().toString() == null || ETNrStudenti.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.nr_invalid),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void renuntaOperatiune(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
