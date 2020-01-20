package com.example.autovehicul;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    //from Long to Date
    @TypeConverter
    public static Date fromLongToDate(Long date){
        if(date!=null){
            return new Date(date);
        }
        else return null;
    }
    //from Date to Long
    @TypeConverter
    public static Long fromDateToLong(Date date){
        if(date != null){
            return date.getTime();
        }
        else return null;
    }
}
