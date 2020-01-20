package com.example.autovehicul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdaugaAutovehicul extends AppCompatActivity {
    Intent intent;
    EditText ETNrAuto;
    EditText ETIdLoc;
    EditText ETDataInregistrarii;
    CheckBox CBPlata;
    Autovehicul autovehicul;
    private final String FORMAT_DATA = "dd/MM/yyyy hh:mm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_autovehicul);
        intent = getIntent();
        initComponente();
        if(intent.hasExtra("deEditat")){
            autovehicul = intent.getParcelableExtra("deEditat");
            setContinutComponente();
        }
    }

    private void initComponente() {
        ETNrAuto = findViewById(R.id.ETNrAuto);
        ETIdLoc = findViewById(R.id.ETIdLoc);
        ETDataInregistrarii = findViewById(R.id.ETDataInregistrarii);
        CBPlata = findViewById(R.id.CBAPlatit);
    }

    private void setContinutComponente() {
        ETNrAuto.setText(autovehicul.getNumarAuto());
        ETIdLoc.setText(String.valueOf(autovehicul.getIdLocParcare()));
        ETDataInregistrarii.setText(new SimpleDateFormat(FORMAT_DATA,Locale.ITALY).format(autovehicul.getDataInregistrarii()));
        CBPlata.setChecked(autovehicul.getAPlatit());
    }

    public void salveazaAutovehicul(View view) {
        if(valideazaDate()) {
            int idLoc = Integer.parseInt(ETIdLoc.getText().toString());
            String nrAuto = ETNrAuto.getText().toString();
            String data = ETDataInregistrarii.getText().toString();
            Date dataInregistrarii = null;
            try {
                dataInregistrarii = new SimpleDateFormat(FORMAT_DATA,Locale.ITALY).parse(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            boolean aPlatit = CBPlata.isChecked();
            autovehicul = new Autovehicul(nrAuto,dataInregistrarii,idLoc,aPlatit);
            intent.putExtra("autovehiculAdaugat",autovehicul);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    private boolean valideazaDate() {
        if(ETIdLoc.getText().toString() == null || ETIdLoc.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),getString(R.string.id_invalid),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ETNrAuto.getText().toString() == null || ETNrAuto.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.nr_invalid),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ETDataInregistrarii.getText().toString() == null || ETDataInregistrarii.getText().toString().trim().isEmpty() || ! valideazaDataInregistrare(ETDataInregistrarii.getText().toString())) {
            Toast.makeText(getApplicationContext(), getString(R.string.data_invalida),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean valideazaDataInregistrare(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATA, Locale.ITALY);
        try {
            sdf.parse(data);
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
