package com.example.rezervare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        ArrayList<Rezervare> rezervari = intent.getParcelableArrayListExtra("rezervariChart");
        BarChart barChart = new BarChart(this,rezervari);
        setContentView(barChart);
    }
}

class BarChart extends View {
    ArrayList<Rezervare> rezervari;
    int nrRezervariS = 0;
    int nrRezervariD = 0;
    public BarChart(Context context, ArrayList<Rezervare> rezervari){
        super(context);
        this.rezervari = rezervari;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(Rezervare rezervare : rezervari){
            if(rezervare.getTipCamera().equals("S")) nrRezervariS+=1;

            if(rezervare.getTipCamera().equals("D")) nrRezervariD+=1;
        }

        int valoareMaxima = 0;
        if(nrRezervariD > nrRezervariS) {
            valoareMaxima = nrRezervariD;
        } else valoareMaxima = nrRezervariS;

        Paint pensula = new Paint();
        pensula.setColor(Color.RED);
        canvas.drawRect(10,canvas.getHeight()-nrRezervariD*500/valoareMaxima,canvas.getWidth()/2-10,canvas.getHeight()-50,pensula);
        pensula.setColor(Color.BLUE);
        canvas.drawRect(10+canvas.getWidth()/2,canvas.getHeight()-nrRezervariS*500/valoareMaxima,canvas.getWidth()-10,canvas.getHeight()-50,pensula);
        pensula.setColor(Color.BLACK);
        pensula.setTextSize(30);
        canvas.drawText("D",10,canvas.getHeight()-20,pensula);
        canvas.drawText("S",10+canvas.getWidth()/2,canvas.getHeight()-20,pensula);
    }
}
