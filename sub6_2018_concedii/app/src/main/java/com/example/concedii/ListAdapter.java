package com.example.concedii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends ArrayAdapter<HomeExchange> {
    private int resursaID;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<HomeExchange> objects) {
        super(context, resource, objects);
        resursaID =resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HomeExchange homeExchange = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(resursaID,null);
        //am obtinut view-ul si il populez
        TextView adresa = v.findViewById(R.id.TVAdresa);
        TextView perioada = v.findViewById(R.id.TVPerioada);
        TextView tip = v.findViewById(R.id.TVTip);

        //punem campurile
        adresa.setText(homeExchange.getAdresa());

        perioada.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(homeExchange.getPerioada()));
        tip.setText(homeExchange.getTipLocuinta());
        return v;
    }
}
