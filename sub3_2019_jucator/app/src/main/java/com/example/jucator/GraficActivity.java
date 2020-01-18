package com.example.jucator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class GraficActivity extends AppCompatActivity {
    ArrayList<Jucator> jucatori= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        jucatori = intent.getParcelableArrayListExtra("jucatori");
        int portari =0;
        int fundasi =0;
        int mijlocasi =0;
        int atacanti =0;
        for(Jucator jucator :jucatori){
            if(jucator.getPozitie().equals("portar")){
                portari+=1;
            }
            if(jucator.getPozitie().equals("fundas")){
                fundasi+=1;
            }
            if(jucator.getPozitie().equals("mijlocas")){
                mijlocasi+=1;
            }
            if(jucator.getPozitie().equals("atacant")){
                atacanti+=1;
            }
        }
        ArrayList<Integer> valoriDeDesenat = new ArrayList<>();
        valoriDeDesenat.add(portari);
        valoriDeDesenat.add(fundasi);
        valoriDeDesenat.add(mijlocasi);
        valoriDeDesenat.add(atacanti);
        BarChart barChart = new BarChart(this,valoriDeDesenat);
        setContentView(barChart);
    }
}

class BarChart extends View {
    ArrayList<Integer> valoriDeDesenat;
    public BarChart(Context context, ArrayList<Integer> valoriDeDesenat){
        super(context);
        this.valoriDeDesenat = valoriDeDesenat;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint pensula = new Paint();
        int valoareDePornire=0;
        int valoareMaxima = Collections.max(valoriDeDesenat);
        for(int i=0;i<valoriDeDesenat.size();i++){
            pensula.setColor(Color.rgb(25*i%255,6*i%255,80));
            canvas.drawRect(i*canvas.getWidth()/valoriDeDesenat.size()+10,canvas.getHeight()-valoriDeDesenat.get(i)*500/valoareMaxima,valoareDePornire+canvas.getWidth()/valoriDeDesenat.size()-10,canvas.getHeight(),pensula);
            valoareDePornire+=canvas.getWidth()/valoriDeDesenat.size();
        }
    }
}
