package com.example.concedii;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SharedPref {
    private SharedPreferences mySharedPref;

    public SharedPref(Context context) {
        mySharedPref = context.getSharedPreferences("sharedPref",Context.MODE_PRIVATE);
    }

    //salveaza data la care utilizatorul acceseaza app
    public  void setUserDate(String date) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("data curenta",date);
        editor.commit();
    }

    //incarca data curenta
    public String getUserDate(){
        String date = mySharedPref.getString("data curenta",null);
        return date;
    }
}
