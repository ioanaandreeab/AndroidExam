package com.example.apackage;

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

public class PackageAdapter extends ArrayAdapter<DataPackage> {
    private final String DATE_FORMAT = "dd/MM/yyyy hh:mm";
    private int resourceId;
    public PackageAdapter(@NonNull Context context, int resource, @NonNull List<DataPackage> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataPackage myPackage = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(resourceId,null);

        //am obtinut view-ul si preiau tv-urile
        TextView TVType = v.findViewById(R.id.TVType);
        TextView TVLongitude = v.findViewById(R.id.TVLongitude);
        TextView TVLatitude = v.findViewById(R.id.TVLatitude);
        TextView TVTimestamp = v.findViewById(R.id.TVTimestamp);

        //le populez
        TVType.setText(myPackage.getPackageType());
        TVLatitude.setText(String.valueOf(myPackage.getLatitude()));
        TVLongitude.setText(String.valueOf(myPackage.getLongitude()));
        TVTimestamp.setText(new SimpleDateFormat(DATE_FORMAT, Locale.ITALY).format(myPackage.getTimestamp()));

        return v;

    }
}
