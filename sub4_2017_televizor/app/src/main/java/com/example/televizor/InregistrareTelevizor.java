package com.example.televizor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InregistrareTelevizor extends AppCompatActivity {
    Intent intent;
    EditText ETNrInventar;
    EditText ETProducator;
    EditText ETProprietar;
    EditText ETDataIntrarii;
    SeekBar seekbarDiagonala;
    Televizor televizor;
    private final String DATE_PATTERN = "dd/MM/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inregistrare_televizor);
        intent = getIntent();
        initComponente();
        if(intent.hasExtra("deEditat")){
            televizor = intent.getParcelableExtra("deEditat");
            setContinutComponente();
        }
    }

    private void setContinutComponente() {
        ETNrInventar.setText(String.valueOf(televizor.getNr_inventar()));
        ETProducator.setText(televizor.getProducator());
        ETProprietar.setText(televizor.getProprietar());
        seekbarDiagonala.setProgress((int)televizor.getDiagonala());
        ETDataIntrarii.setText(new SimpleDateFormat(DATE_PATTERN,Locale.ITALY).format(televizor.getData_intrarii()));
    }

    private void initComponente(){
        ETNrInventar = findViewById(R.id.ETNrInventar);
        ETProducator = findViewById(R.id.ETProducator);
        ETProprietar = findViewById(R.id.ETProprietar);
        ETDataIntrarii = findViewById(R.id.ETDataIntrarii);
        seekbarDiagonala = findViewById(R.id.seekbarDiagonala);
        seekbarDiagonala.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(getApplicationContext(),progress +" ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void salveazaTelevizor(View view) {
        if(valideazaDate()){
            int nrInventar = Integer.parseInt(ETNrInventar.getText().toString());
            String producator = ETProducator.getText().toString();
            String proprietar = ETProprietar.getText().toString();
            float diagonala = seekbarDiagonala.getProgress();
            Date data = null;
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.ITALY);
            try {
                data = sdf.parse(ETDataIntrarii.getText().toString());
            } catch (ParseException e){
                e.printStackTrace();
            }
            televizor = new Televizor(nrInventar,producator,diagonala,proprietar,data);
            intent.putExtra("televizorAdaugat",televizor);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    private boolean valideazaDate(){
        if(ETProprietar.getText().toString() == null || ETProprietar.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),getString(R.string.proprietar_invalid),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETProducator.getText().toString() == null || ETProducator.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getString(R.string.producator_invalid),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETDataIntrarii.getText().toString() == null || ETDataIntrarii.getText().toString().trim().isEmpty() || !valideazaData(ETDataIntrarii.getText().toString())){
            Toast.makeText(getApplicationContext(), getString(R.string.data_invalida),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETNrInventar.getText().toString() == null || ETNrInventar.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),getString(R.string.nr_invalid),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean valideazaData(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.ITALY);
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
