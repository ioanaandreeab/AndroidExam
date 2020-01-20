package com.example.apackage;

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

public class SendPackage extends AppCompatActivity {
    DataPackage myPackage;
    Intent intent;
    EditText ETPackageId;
    EditText ETLatitude;
    EditText ETLongitude;
    Spinner spinnerType;
    EditText ETTimestamp;
    ArrayAdapter<String> spinnerAdapter;
    private final String TIMESTAMP_PATTERN = "dd/MM/yyyy hh:mm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_package);
        intent = getIntent();
        initComponents();
        if(intent.hasExtra("toBeEdited")){
            myPackage = intent.getParcelableExtra("toBeEdited");
            setComponentsContent();
        }
    }

    private void setComponentsContent() {
        ETPackageId.setEnabled(false);
        ETPackageId.setText(String.valueOf(myPackage.getPackageId()));
        ETLatitude.setText(String.valueOf(myPackage.getLatitude()));
        ETLongitude.setText(String.valueOf(myPackage.getLongitude()));
        ETTimestamp.setText(new SimpleDateFormat(TIMESTAMP_PATTERN,Locale.ITALY).format(myPackage.getTimestamp()));
        spinnerType.setSelection(spinnerAdapter.getPosition(myPackage.getPackageType()));
    }

    private void initComponents() {
        ETPackageId = findViewById(R.id.ETPackageId);
        ETLatitude = findViewById(R.id.ETLatitude);
        ETLongitude = findViewById(R.id.ETLongitude);
        ETTimestamp = findViewById(R.id.ETTimestamp);
        ETTimestamp = findViewById(R.id.ETTimestamp);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.packageType));
        spinnerType.setAdapter(spinnerAdapter);
    }

    public void savePackage(View view) {
        if(validateData()){
            int id = Integer.parseInt(ETPackageId.getText().toString());
            double latitude = Double.parseDouble(ETLatitude.getText().toString());
            double longitude = Double.parseDouble(ETLongitude.getText().toString());
            Date date = null;
            try {
                date = new SimpleDateFormat(TIMESTAMP_PATTERN,Locale.ITALY).parse(ETTimestamp.getText().toString());
            } catch(ParseException e){
                e.printStackTrace();
            }
            String type = String.valueOf(spinnerType.getSelectedItem());
            myPackage = new DataPackage(id,type,latitude,longitude,date);
            intent.putExtra("savedPackage",myPackage);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    private boolean validateData() {
        if(ETTimestamp.getText().toString() == null || ETTimestamp.getText().toString().trim().isEmpty() || !validateTimestamp(ETTimestamp.getText().toString())){
            Toast.makeText(getApplicationContext(),getString(R.string.invalid_time),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETLongitude.getText().toString() == null || ETLongitude.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),getString(R.string.invalid_longitude),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETLatitude.getText().toString() == null || ETLatitude.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getString(R.string.invalid_latitude),Toast.LENGTH_LONG).show();
            return false;
        }
        if(ETPackageId.getText().toString() == null || ETPackageId.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getString(R.string.invalid_id),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validateTimestamp(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_PATTERN, Locale.ITALY);
        try {
            sdf.parse(date);
        } catch (ParseException e){
            return false;
        }
        return true;
    }


    public void cancelOperation(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
