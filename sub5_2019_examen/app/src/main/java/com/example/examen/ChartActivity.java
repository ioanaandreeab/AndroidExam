package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ArrayList<Examen> examene = intent.getParcelableArrayListExtra("examene");
        BarChart barChart = new BarChart(this,examene);
        setContentView(barChart);
    }
}

class BarChart extends View {
    ArrayList<Examen> examene;
    public BarChart(Context context, ArrayList<Examen> examene) {
        super(context);
        this.examene = examene;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<Integer> valori = new ArrayList<>();
        for(Examen examen: examene) {
            valori.add(examen.getNumarStudenti());
        }

        int valoareMaxima = Collections.max(valori);
        int valoareDeInceput = 0;

        Paint pensula = new Paint();
        for(int i=0;i<examene.size();i++) {
            pensula.setColor(Color.rgb(25*i%255,78*i%255,120));
            canvas.drawRect(i*canvas.getWidth()/examene.size()+10,canvas.getHeight()-examene.get(i).getNumarStudenti()*500/valoareMaxima,valoareDeInceput+canvas.getWidth()/examene.size()-10,canvas.getHeight()-50,pensula);
            pensula.setColor(Color.BLACK);
            pensula.setTextSize(30);
            canvas.drawText(examene.get(i).getDenumireMaterie(),i*canvas.getWidth()/examene.size(),canvas.getHeight()-20,pensula);
            valoareDeInceput += canvas.getWidth()/examene.size();
        }
    }
}