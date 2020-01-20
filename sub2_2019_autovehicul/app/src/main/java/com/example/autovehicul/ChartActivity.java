package com.example.autovehicul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        ArrayList<Autovehicul> autovehicule = intent.getParcelableArrayListExtra("autovehiculeChart");
        BarChart barChart = new BarChart(getApplicationContext(),autovehicule);
        setContentView(barChart);
    }
}

class BarChart extends View {
    ArrayList<Autovehicul> autovehicule;
    public BarChart(Context context, ArrayList<Autovehicul> autovehicule) {
        super(context);
        this.autovehicule = autovehicule;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int nrAuPlatit = 0;
        int nrNuAuPlatit = 0;

        for(Autovehicul autovehicul : autovehicule){
            if(autovehicul.getAPlatit()){
                nrAuPlatit+=1;
            }
            else nrNuAuPlatit +=1;
        }
        int valoareMaxima = nrAuPlatit > nrNuAuPlatit ? nrAuPlatit : nrNuAuPlatit;

        Paint pensula = new Paint();
        pensula.setColor(Color.GREEN);
        canvas.drawRect(10,canvas.getHeight()-nrAuPlatit*500/valoareMaxima,canvas.getWidth()/2-10,canvas.getHeight()-50,pensula);
        pensula.setColor(Color.RED);
        canvas.drawRect(canvas.getWidth()/2+10,canvas.getHeight()-nrNuAuPlatit*500/valoareMaxima,canvas.getWidth()-10,canvas.getHeight()-50,pensula);
    }
}
