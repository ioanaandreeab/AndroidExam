package com.example.autovehicul;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AutovehiculAdapter extends ArrayAdapter<Autovehicul> {
    int resourceId;
    public AutovehiculAdapter(@NonNull Context context, int resource, @NonNull List<Autovehicul> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Autovehicul autovehicul = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(resourceId, null);

        //iau controalele
        TextView TVNrAuto = v.findViewById(R.id.TVNrAuto);
        TextView TVLocParcare = v.findViewById(R.id.TVNrLoc);
        TextView TVAPlatit = v.findViewById(R.id.TVAPlatit);
        //populez
        TVNrAuto.setText(autovehicul.getNumarAuto());
        TVLocParcare.setText(String.valueOf(autovehicul.getIdLocParcare()));
        TVAPlatit.setText(String.valueOf(autovehicul.getAPlatit()));

        return v;
    }
}
