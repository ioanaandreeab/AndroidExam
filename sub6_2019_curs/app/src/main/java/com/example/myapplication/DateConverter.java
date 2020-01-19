package com.example.myapplication;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Long fromDateToLong(Date date){
        if(date!=null){
            return date.getTime();
        }
        else return null;
    }

    @TypeConverter
    public static Date fromLongToDate(Long date){
        if(date!=null){
            return new Date(date);
        }
        else return null;
    }
}
