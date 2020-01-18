package com.example.jucator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Jucator> jucatori = new ArrayList<>();
    final int COD_ADAUGARE_JUCATOR = 333;
    ListView LVJucatori;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init LV
        LVJucatori = findViewById(R.id.LVJucatori);
        adaugaJucatoriLaLV();

        LVJucatori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });

    }


    private void adaugaJucatoriLaLV(){
        ArrayAdapter<Jucator> adapterLV = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,jucatori);
        LVJucatori.setAdapter(adapterLV);
    }

    //creare meniu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuAdaugaJucator:
                Intent intent = new Intent(this, AdaugaJucator.class);
                startActivityForResult(intent,COD_ADAUGARE_JUCATOR);
                break;
            case R.id.menuGrafic:
                Toast.makeText(this,"grafic",Toast.LENGTH_LONG).show();
                break;
            case R.id.menuSincronizareRetea:
                Toast.makeText(this,"sincronizare retea",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==COD_ADAUGARE_JUCATOR){
            if(resultCode==RESULT_OK){
                Jucator jucatorNou = data.getParcelableExtra("jucatorAdaugat");
                jucatori.add(jucatorNou);
                Toast.makeText(this, jucatorNou.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        for(int i=0;i<jucatori.size();i++){
            outState.putParcelable("key"+i,jucatori.get(i));
        }
        outState.putInt("listSize",jucatori.size());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int dim = savedInstanceState.getInt("listSize");
        for(int i=0;i<dim;i++){
            jucatori.add((Jucator)savedInstanceState.getParcelable("key"+i));
        }
    }
}
