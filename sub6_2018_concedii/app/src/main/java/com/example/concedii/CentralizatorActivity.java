package com.example.concedii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CentralizatorActivity extends AppCompatActivity {
    List<Integer> valoriDeDesenat = new ArrayList<>();
    int single;
    int doubleRoom;
    int triple;
    int fourRoom;
    int fiveRoom;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = Room.databaseBuilder(this,Database.class,"oferte").allowMainThreadQueries().build();
        single = db.getHomeExchangeDAO().select1Camera();
        if (single!=0){
            valoriDeDesenat.add(single);
        }
        doubleRoom = db.getHomeExchangeDAO().select2Camere();
        if (doubleRoom!=0){
            valoriDeDesenat.add(doubleRoom);
        }
        triple = db.getHomeExchangeDAO().select3Camere();
        if (triple!=0){
            valoriDeDesenat.add(triple);
        }
        fourRoom = db.getHomeExchangeDAO().select4Camere();
        if (fourRoom!=0){
            valoriDeDesenat.add(fourRoom);
        }
        fiveRoom = db.getHomeExchangeDAO().select5Camere();
        if (fiveRoom!=0){
            valoriDeDesenat.add(fiveRoom);
        }
        BarChart barChart = new BarChart(this,valoriDeDesenat);
        setContentView(barChart);
    }
}

class BarChart extends View {
    List<Integer> colectie;
    public BarChart(Context context, List<Integer> colectie) {
        super(context);
        this.colectie = colectie;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint pensula = new Paint();
        int valoareMaxima = Collections.max(colectie);
        float valoareDePornire =0;
        for(int i=0;i<colectie.size();i++){
            pensula.setColor(Color.rgb(37*i%255,48*i%255,20));
            canvas.drawRect(i*canvas.getWidth()/colectie.size() + 10,canvas.getHeight()-(float)(colectie.get(i)*500/valoareMaxima),valoareDePornire+canvas.getWidth()/colectie.size()-10,canvas.getHeight(),pensula);
            valoareDePornire+=canvas.getWidth()/colectie.size();
            pensula.setColor(Color.BLACK);
            pensula.setTextSize(50);
            canvas.drawText(colectie.get(i)+"camere",i*canvas.getWidth()/colectie.size() + 10,canvas.getHeight()-(float)(colectie.get(i)*500/valoareMaxima)-20,pensula);
        }
    }
}
