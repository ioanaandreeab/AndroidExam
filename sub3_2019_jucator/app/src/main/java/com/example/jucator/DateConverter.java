package com.example.jucator;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    //din long in date
    public static Date fromTimeStamp(Long timestamp){
        if(timestamp!=null){
            return new Date(timestamp);
        }
        else return null;
    }

    //din date in long
    @TypeConverter
    public static Long fromDate(Date date){
        if(date!=null){
            return date.getTime();
        }
        else return null;
    }

}
