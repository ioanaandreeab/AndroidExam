package com.example.myapplication;

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
    ArrayList<Curs> cursuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        cursuri = intent.getParcelableArrayListExtra("cursuri");
        BarChart barChart = new BarChart(this,cursuri);
        setContentView(barChart);
    }
}

class BarChart extends View {
    ArrayList<Curs> cursuri;
    public BarChart(Context context, ArrayList<Curs> cursuri){
        super(context);
        this.cursuri = cursuri;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //get maxValue
        List<Integer> valori = new ArrayList<>();
        for(Curs curs :cursuri){
            valori.add(curs.getNumarParticipanti());
        }
        int valoareMaxima = Collections.max(valori);
        int valoareDeInceput = 0;
        Paint pensula = new Paint();
        for(int i=0;i<cursuri.size();i++){
            pensula.setColor(Color.rgb(25*i%255,46*i%255,100));
            canvas.drawRect(i*canvas.getWidth()/cursuri.size() + 10,canvas.getHeight()-cursuri.get(i).getNumarParticipanti()*500/valoareMaxima,valoareDeInceput+canvas.getWidth()/cursuri.size()-10,canvas.getHeight()-50,pensula);
            pensula.setColor(Color.BLACK);
            pensula.setTextSize(40);
            canvas.drawText(cursuri.get(i).getDenumire(),i*canvas.getWidth()/cursuri.size() + 10,canvas.getHeight()-30,pensula);
            valoareDeInceput += canvas.getWidth()/cursuri.size();
        }
    }
}